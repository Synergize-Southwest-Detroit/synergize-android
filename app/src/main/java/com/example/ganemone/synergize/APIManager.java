package com.example.ganemone.synergize;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

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

    public void loadEvents(final Closure cb) {

        JsonHttpResponseHandler handler = getGETHandler(Event.class, new APIClosure() {
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

        client.get(BASE_URL + "events", null, handler);
    }

    public void loadHowtos(final Closure cb) {

        JsonHttpResponseHandler handler = getGETHandler(HowTo.class, new APIClosure() {
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

        client.get(BASE_URL + "howtos", null, handler);
    }

    public void loadResources(final Closure cb) {

        JsonHttpResponseHandler handler = getGETHandler(Resource.class, new APIClosure() {
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

        client.get(BASE_URL + "resources", null, handler);
    }

    public JsonHttpResponseHandler getGETHandler(final Class<? extends APIObject> obj, final APIClosure cb) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray objects = null;
                ArrayList ret = new ArrayList(10);
                try {
                    objects = response.getJSONArray("objects");
                } catch (JSONException e) {
                    e.printStackTrace();
                    cb.onFailure("Error parsing response");
                    return;
                }
                for (int i = 0; i < objects.length(); i++) {
                    try {
                        JSONObject jsonObj = objects.getJSONObject(i);
                        Constructor<? extends APIObject> constructor = obj.getConstructor(JSONObject.class);
                        ret.add(constructor.newInstance(jsonObj));
                    } catch (JSONException e) {
                        cb.onFailure("Error creating event");
                        e.printStackTrace();
                        return;
                    } catch (InvocationTargetException e) {
                        cb.onFailure("Invocation Target Exception");
                        e.printStackTrace();
                        return;
                    } catch (NoSuchMethodException e) {
                        cb.onFailure("No Such Method Exception");
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
                cb.onFailure(responseString);
            }
        };
    }






}
