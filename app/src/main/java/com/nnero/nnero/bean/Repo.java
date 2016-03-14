package com.nnero.nnero.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by NNERO on 16/1/29.
 */
public class Repo implements Serializable{

    private long id;
    private String name;
    private String full_name;
    private boolean isPrivate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Repo(JSONObject obj){
        this.id = obj.optInt("id");
        this.name = obj.optString("name");
        this.full_name = obj.optString("full_name");
        this.isPrivate = obj.optBoolean("private");
    }
}
