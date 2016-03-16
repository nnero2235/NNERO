package com.nnero.nnero.http.okhttp;

/**
 * Created by nnero on 16/3/15.
 * http 返回的response
 */
public class Response {
    private boolean isSuccess;
    private String json;

    public Response(boolean isSuccess,String json){
        this.isSuccess = isSuccess;
        this.json = json;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
