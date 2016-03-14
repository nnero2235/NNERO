package com.nnero.nnero.db;

import android.content.ContentValues;

import java.util.List;

/**
 * Created by NNERO on 16/1/21.
 *
 * 封装增删改查 到 wrapper
 */
public interface TableWrapper<T> {

    String NO_LIMIT = "";
    String LIMIT_100 = "100";

    void insert(ContentValues values);

    void delete(String where, String[] args);

    void update(ContentValues values, String where, String[] args);

    List<T> query(String select, String[] selectArgs, String limit);

    List<T> queryAll(String limit);

    boolean hasSame(T t); //表中 是否有相同的
}

