package com.nnero.nnero.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.google.common.base.Strings;
import com.nnero.nnero.bean.Audio;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Audio.AudioColumns.ALBUM;
import static android.provider.MediaStore.Audio.AudioColumns.ALBUM_ID;
import static android.provider.MediaStore.Audio.AudioColumns.ARTIST;
import static android.provider.MediaStore.Audio.AudioColumns.DURATION;
import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;
import static android.provider.MediaStore.MediaColumns.SIZE;
import static android.provider.MediaStore.MediaColumns.TITLE;

/**
 * Created by NNERO on 16/1/21.
 */
public class LocalMusicTableWrapper implements TableWrapper<Audio> {

    private Context context;
    private static final long MIN_DURATION = 0;

    public LocalMusicTableWrapper(Context context){
        this.context = context;
    }

    @Override
    public void insert(ContentValues values) {
    }

    @Override
    public void delete(String where, String[] args) {
    }

    @Override
    public void update(ContentValues values, String where, String[] args) {
    }

    @Override
    public List<Audio> query(String select, String[] selectArgs, String limit) {
        return null;
    }

    @Override
    public List<Audio> queryAll(String limit) {
        Cursor cursor = context.getContentResolver().query(
                EXTERNAL_CONTENT_URI,
                new String[]{TITLE, DURATION, ARTIST, _ID, ALBUM, DATA, ALBUM_ID, MIME_TYPE, SIZE},
                MediaStore.Audio.Media.DURATION + " > " + MIN_DURATION + " AND " + MIME_TYPE + " IN (?,?,?,?,?,?)",
                new String[]{"audio/mpeg", "audio/wav", "audio/x-wav", "audio/flac", "audio/x-ms-wma", "audio/ext-mpeg"},
                TITLE
        );
        if (cursor == null) {
            return null;
        }
        return parseAudios(cursor);
    }

    @Override
    public boolean hasSame(Audio audio) {
        return false;
    }

    private List<Audio> parseAudios(Cursor cursor){
        List<Audio> audios = new ArrayList<Audio>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Audio audio = getAudio(cursor);
            if (audio.getSource() != null) {
                audios.add(audio);
            }
        }
        cursor.close();
        return audios;
    }

    private Audio getAudio(Cursor cursor){
        Audio audio = new Audio();
        audio.setAudioId(cursor.getString(cursor.getColumnIndex(_ID)));
        String title = cursor.getString(cursor.getColumnIndex(TITLE));
        if(null != title){
            audio.setName(title);
        } else {
            audio.setName("未知");
        }
        String artist = cursor.getString(cursor.getColumnIndex(ARTIST));
        if (Strings.isNullOrEmpty(artist) || "<unknown>".equals(artist)) {
            audio.setArtist("未知");
        } else {
            audio.setArtist(artist);
        }
        String album = cursor.getString(cursor.getColumnIndex(ALBUM));
        if(Strings.isNullOrEmpty(album) || "<unknown>".equals(album)){
            audio.setAlbumName("未知");
        } else {
            audio.setAlbumName(album);
        }

        audio.setSource(cursor.getString(cursor.getColumnIndex(DATA)));
        audio.setComeFrom(Audio.LOCAL);
        audio.setDuration(cursor.getLong(cursor.getColumnIndex(DURATION)));
        audio.setSize(cursor.getLong(cursor.getColumnIndex(SIZE)));

        return audio;
    }
}
