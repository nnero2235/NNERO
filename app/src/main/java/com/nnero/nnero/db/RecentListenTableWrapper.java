package com.nnero.nnero.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nnero.nnero.bean.Audio;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by NNERO on 16/1/21.
 */
public class RecentListenTableWrapper implements TableWrapper<Audio> {

    private SongOneDB db;

    private final String[] COLUMNS = {DBWords.RecentListen.ID,
            DBWords.RecentListen.AUDIO_ID,
            DBWords.RecentListen.NAME,
            DBWords.RecentListen.ALBUM,
            DBWords.RecentListen.ARTIST,
            DBWords.RecentListen.IMAGE,
            DBWords.RecentListen.DURATION,
            DBWords.RecentListen.SIZE,
            DBWords.RecentListen.COME_FROM,
            DBWords.RecentListen.SOURCE,
            DBWords.RecentListen.TIME};

    public RecentListenTableWrapper(SongOneDB db){
        this.db = db;
    }

    @Override
    public void insert(ContentValues values) {
        deleteIfOver100();
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.insert(DBWords.RecentListen.TABLE_NAME, null, values);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    @Override
    public void delete(String where, String[] args) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.delete(DBWords.RecentListen.TABLE_NAME, where, args);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    @Override
    public void update(ContentValues values, String where, String[] args) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.update(DBWords.RecentListen.TABLE_NAME, values, where, args);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    @Override
    public List<Audio> query(String select, String[] selectArgs, String limit) {
        Cursor cursor = db.getReadableDatabase().query(
                DBWords.RecentListen.TABLE_NAME,
                COLUMNS,
                select,
                selectArgs,
                null,
                null,
                DBWords.RecentListen.TIME +" DESC",
                limit);
        if(cursor != null) {
            List<Audio> audios = parseCursor(cursor);
            cursor.close();
            return audios;
        } else {
            return null;
        }
    }

    @Override
    public List<Audio> queryAll(String limit) {
        Cursor cursor = db.getReadableDatabase().query(
                DBWords.RecentListen.TABLE_NAME,
                COLUMNS,
                null, null, null, null,
                DBWords.RecentListen.TIME +" DESC",
                limit);
        if(cursor != null) {
            List<Audio> audios = parseCursor(cursor);
            cursor.close();
            return audios;
        } else {
            return null;
        }
    }

    @Override
    public boolean hasSame(Audio audio) {
        Cursor cursor = db.getReadableDatabase().query(
                DBWords.RecentListen.TABLE_NAME,
                COLUMNS,
                DBWords.RecentListen.AUDIO_ID + "= ?" ,
                new String[]{audio.getAudioId()},
                null,
                null,
                DBWords.RecentListen.TIME +" DESC",
                TableWrapper.NO_LIMIT);
        if(cursor.getCount() == 0){
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    private List<Audio> parseCursor(Cursor cursor){
        if(cursor.getCount() > 0){
            List<Audio> audios = new ArrayList<>();
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                Audio audio = new Audio();
                audio.setAudioId(cursor.getString(cursor.getColumnIndex(DBWords.RecentListen.AUDIO_ID)));
                audio.setName(cursor.getString(cursor.getColumnIndex(DBWords.RecentListen.NAME)));
                audio.setAlbumName(cursor.getString(cursor.getColumnIndex(DBWords.RecentListen.ALBUM)));
                audio.setAudioImage(cursor.getString(cursor.getColumnIndex(DBWords.RecentListen.IMAGE)));
                audio.setArtist(cursor.getString(cursor.getColumnIndex(DBWords.RecentListen.ARTIST)));
                audio.setDuration(cursor.getLong(cursor.getColumnIndex(DBWords.RecentListen.DURATION)));
                audio.setSize(cursor.getInt(cursor.getColumnIndex(DBWords.RecentListen.SIZE)));
                audio.setSource(cursor.getString(cursor.getColumnIndex(DBWords.RecentListen.SOURCE)));
                audio.setComeFrom(cursor.getInt(cursor.getColumnIndex(DBWords.RecentListen.COME_FROM)));
                audios.add(audio);
            }
            return audios;
        } else {
            return null;
        }
    }
    //超过100条要删除
    private void deleteIfOver100(){
        Cursor cursorCount = db.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM " + DBWords.RecentListen.TABLE_NAME, null);
        cursorCount.moveToFirst();
        int count = cursorCount.getInt(0);
        cursorCount.close();
        if(count > 100){
            Cursor cursor = db.getReadableDatabase().rawQuery("SELECT MIN("+DBWords.RecentListen.TIME+")"+" FROM " + DBWords.RecentListen.TABLE_NAME,null);
            delete(DBWords.RecentListen.TIME+"= ?",new String[]{cursor.getInt(0)+""});
            cursor.close();
        }
    }

}
