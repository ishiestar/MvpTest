package com.example.mvptest;

/**
 * Interface to catch data from the API
 *
 * Created by ishitasinha on 28/01/17.
 */

public interface ResponseListener<T> {

    //generic because the data might be of any type and we don't want to cast every time
    void onResponse(T data);

    void onFailure(Throwable throwable);
}
