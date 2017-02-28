package com.example.mvptest;

/**
 * Created by ishitasinha on 28/01/17.
 */

public interface ResponseListener<T> {
    void onResponse(T data);

    void onFailure(Throwable throwable);
}
