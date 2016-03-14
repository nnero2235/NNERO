package com.nnero.nnero.callback;

/**
 * Created by nnero on 16/3/11.
 */
public interface CallBack<T> {

    void preDoInBackground();
    void process(T t);
}
