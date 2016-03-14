package com.nnero.nnero.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NNERO on 16/1/20.
 *
 * SongOne数据库
 */
public class SongOneDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "song_one";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TAB_RECENT_LISTEN = "CREATE TABLE IF NOT EXISTS " + DBWords.RecentListen.TABLE_NAME + "(" +
            DBWords.RecentListen.ID + " integer primary key autoincrement," +
            DBWords.RecentListen.AUDIO_ID + " text," +
            DBWords.RecentListen.NAME + " text," +
            DBWords.RecentListen.ALBUM + " text," +
            DBWords.RecentListen.ARTIST + " text," +
            DBWords.RecentListen.IMAGE + " text," +
            DBWords.RecentListen.DURATION + " integer," +
            DBWords.RecentListen.SIZE + " integer," +
            DBWords.RecentListen.COME_FROM + " integer," +
            DBWords.RecentListen.SOURCE + " text," +
            DBWords.RecentListen.TIME + " integer)";


    public SongOneDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TAB_RECENT_LISTEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO:第一版没有升级 以后可能有
    }
}
