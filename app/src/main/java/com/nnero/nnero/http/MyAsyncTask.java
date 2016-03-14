package com.nnero.nnero.http;

import android.os.AsyncTask;
import android.os.Build;

import com.nnero.nnero.callback.CallBack;

/**
 * Created by nnero on 16/3/11.
 */
public abstract class MyAsyncTask<T> extends AsyncTask<Void,Void,T> {

    private CallBack<T> mCallBack;

    public MyAsyncTask(CallBack<T> callBack){
        this.mCallBack = callBack;
    }

    public MyAsyncTask doExecute(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            executeOnExecutor(SERIAL_EXECUTOR);
        } else {
            execute();
        }
        return this;
    }

    @Override
    protected void onPreExecute() {
        if(mCallBack != null){
            mCallBack.preDoInBackground();
        }
    }

    @Override
    protected void onPostExecute(T o) {
        if(mCallBack != null){
            mCallBack.process(o);
        }
    }
}
