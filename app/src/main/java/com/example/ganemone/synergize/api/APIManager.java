package com.example.ganemone.synergize.api;

import android.util.Log;

import com.example.ganemone.synergize.Closure;
import com.example.ganemone.synergize.event.Event;
import com.example.ganemone.synergize.howto.HowTo;
import com.example.ganemone.synergize.resource.Resource;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by ganemone on 4/1/15.
 */


public class APIManager {

    private static String TAG = "APIManager";
    private static APIManager ourInstance = new APIManager();

    public static String BASE_URL = "http://ganemone.pythonanywhere.com/api/";

    public ArrayList<Event> events = new ArrayList<Event>();
    public ArrayList<HowTo> howtos = new ArrayList<HowTo>();
    public ArrayList<Resource> resources = new ArrayList<Resource>();
    private AsyncHttpClient client = new AsyncHttpClient();

    public int howtoPage = 1;
    public int eventPage = 1;
    public int resourcePage = 1;
    public int numHowToPages = 1;
    public int numEventPages = 1;
    public int numResourcePages = 1;


    public enum Type {
        EVENT, HOWTO, RESOURCE
    }

    public static APIManager getInstance() {
        return ourInstance;
    }

    public Event getEventByID(int id) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).id == id) {
                return events.get(i);
            }
        }
        return null;
    }

    public HowTo getHowToByID(int id) {
        for (int i = 0; i < howtos.size(); i++) {
            if (howtos.get(i).id == id) {
                return howtos.get(i);
            }
        }
        return null;
    }

    public Resource getResourceByID(int id) {
        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).id == id) {
                return resources.get(i);
            }
        }
        return null;
    }

    public void reloadEvents(final Closure cb) {
        this.events.clear();
        this.eventPage = 1;
        this.loadEvents(cb);
    }

    public void loadEvents(final Closure cb) {

        JsonHttpResponseHandler handler = getGETHandler(Type.EVENT, new APIClosure() {
            @Override
            public void onSuccess(ArrayList objects) {
                eventPage++;
                for (int i = 0; i < objects.size(); i++) {
                    events.add((Event)objects.get(i));
                }
                cb.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                cb.onFailure(message);
            }
        });
        RequestParams params = new RequestParams("page", eventPage);
        client.get(BASE_URL + "events", params, handler);
    }

    public void reloadHowtos(Closure cb) {
        howtoPage = 1;
        howtos.clear();
        loadHowtos(cb);
    }

    public void loadHowtos(final Closure cb) {

        JsonHttpResponseHandler handler = getGETHandler(Type.HOWTO, new APIClosure() {
            @Override
            public void onSuccess(ArrayList objects) {
                howtoPage++;
                for (int i = 0; i < objects.size(); i++) {
                    howtos.add((HowTo)objects.get(i));
                }
                cb.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                cb.onFailure(message);
            }
        });

        RequestParams params = new RequestParams("page", howtoPage);
        client.get(BASE_URL + "howtos", params, handler);
    }


    public void reloadResources(Closure closure) {
        resourcePage = 1;
        resources.clear();
        loadResources(closure);
    }

    public void loadResources(final Closure cb) {

        JsonHttpResponseHandler handler = getGETHandler(Type.RESOURCE, new APIClosure() {
            @Override
            public void onSuccess(ArrayList objects) {
                resourcePage++;
                for (int i = 0; i < objects.size(); i++) {
                    resources.add((Resource) objects.get(i));
                }
                cb.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                cb.onFailure(message);
            }
        });
        RequestParams params = new RequestParams("page", resourcePage);
        client.get(BASE_URL + "resources", params, handler);
    }

    public Constructor<? extends APIObject> getConstructorFromType(Type type) {
        try {
            switch (type) {
                case EVENT:
                    return Event.class.getConstructor(JSONObject.class);
                case RESOURCE:
                    return Resource.class.getConstructor(JSONObject.class);
                case HOWTO:
                    return HowTo.class.getConstructor(JSONObject.class);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTotalPages(Type type, JSONObject response) {
        try {
            int totalPages = response.getInt("total_pages");
            switch (type) {
                case EVENT: numEventPages = totalPages; break;
                case RESOURCE: numResourcePages = totalPages; break;
                case HOWTO: numHowToPages = totalPages; break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JsonHttpResponseHandler getGETHandler(final Type type, final APIClosure cb) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                setTotalPages(type, response);
                JSONArray objects = null;
                ArrayList ret = new ArrayList(10);
                try {
                    objects = response.getJSONArray("objects");
                } catch (JSONException e) {
                    e.printStackTrace();
                    cb.onFailure("Error parsing response");
                    return;
                }
                Constructor<? extends APIObject> constructor = null;
                constructor = getConstructorFromType(type);
                for (int i = 0; i < objects.length(); i++) {
                    try {
                        JSONObject jsonObj = objects.getJSONObject(i);
                        ret.add(constructor.newInstance(jsonObj));
                    } catch (JSONException e) {
                        cb.onFailure("Error creating event");
                        e.printStackTrace();
                        return;
                    } catch (InvocationTargetException e) {
                        cb.onFailure("Invocation Target Exception");
                        e.printStackTrace();
                        return;
                    } catch (InstantiationException e) {
                        cb.onFailure("Instantiation Exception");
                        e.printStackTrace();
                        return;
                    } catch (IllegalAccessException e) {
                        cb.onFailure("Illegal Access Exception");
                        e.printStackTrace();
                        return;
                    }
                }
                cb.onSuccess(ret);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("FAILED: ", "Status Code: " + statusCode);
                cb.onFailure(responseString);
            }
        };
    }

    public boolean hasNextEventPage() {
        return eventPage <= numEventPages;
    }

    public boolean hasNextHowToPage() {
        return howtoPage <= numHowToPages;
    }

    public boolean hasNextResourcePage() {
        return resourcePage <= numResourcePages;
    }
}
