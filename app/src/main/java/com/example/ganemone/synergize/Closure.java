package com.example.ganemone.synergize;

import java.util.ArrayList;

/**
 * Created by ganemone on 4/1/15.
 */
public interface Closure {
    public void onSuccess();
    public void onFailure(String message);
}

