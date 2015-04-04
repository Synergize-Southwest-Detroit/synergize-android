package com.example.ganemone.synergize.api;

import java.util.ArrayList;

public interface APIClosure {
    public void onSuccess(ArrayList objects);
    public void onFailure(String message);
}
