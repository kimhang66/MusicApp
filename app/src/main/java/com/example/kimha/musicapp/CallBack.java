package com.example.kimha.musicapp;

/**
 * Created by kimha on 5/20/2018.
 */

public interface CallBack<T> {
    public void success(T object);
    public void error(T object);
}
