package com.nnero.nnero.manager;

import android.content.ContentValues;
import android.content.Context;

import com.nnero.nnero.bean.Audio;
import com.nnero.nnero.db.DBWords;
import com.nnero.nnero.db.LocalMusicTableWrapper;
import com.nnero.nnero.db.RecentListenTableWrapper;
import com.nnero.nnero.db.SongOneDB;
import com.nnero.nnero.db.TableWrapper;
import com.nnero.nnero.db.listener.DBListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NNERO on 16/1/22.
 *
 * 其他类 和数据库交互
 */
public class DBManager {

    private static DBManager sManager;

    private ExecutorService executorService;
    private SongOneDB db;
    private Map<String,TableWrapper> tables;
    private Context context;

    private DBManager(Context context){
        this.context = context;
        tables = new HashMap<>();
        executorService = Executors.newCachedThreadPool();
        db = new SongOneDB(context);
    }
    //必须先init
    public static void init(Context context){
        sManager = new DBManager(context);
    }

    public static DBManager getManager(){
        return sManager;
    }

    //该方法是异步方法，用回调获取
    public synchronized void getLocalAudios(final DBListener.OnAudioQueryFinishListener l){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                TableWrapper<Audio> localTable = getTableWrapper(DBWords.Local.TABLE_NAME);
                l.onAudioQueryFinish(localTable.queryAll(TableWrapper.NO_LIMIT));
            }
        });
    }
    //100条数据限制
    public synchronized void getRecentAudios(final DBListener.OnAudioQueryFinishListener l){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                TableWrapper<Audio> recentTable = getTableWrapper(DBWords.RecentListen.TABLE_NAME);
                l.onAudioQueryFinish(recentTable.queryAll(TableWrapper.LIMIT_100));
            }
        });
    }

    public void insertOrUpdateRecentListen(final Audio audio){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                TableWrapper < Audio > recentTable = getTableWrapper(DBWords.RecentListen.TABLE_NAME);
                if(!recentTable.hasSame(audio)) {
                    ContentValues values = new ContentValues();
                    values.put(DBWords.RecentListen.AUDIO_ID, audio.getAudioId());
                    values.put(DBWords.RecentListen.NAME, audio.getName());
                    values.put(DBWords.RecentListen.ALBUM, audio.getAlbumName());
                    values.put(DBWords.RecentListen.IMAGE, audio.getAudioImage());
                    values.put(DBWords.RecentListen.ARTIST, audio.getArtist());
                    values.put(DBWords.RecentListen.SIZE, audio.getSize());
                    values.put(DBWords.RecentListen.DURATION, audio.getDuration());
                    values.put(DBWords.RecentListen.COME_FROM, audio.getComeFrom());
                    values.put(DBWords.RecentListen.SOURCE, audio.getSource());
                    values.put(DBWords.RecentListen.TIME, System.currentTimeMillis());
                    recentTable.insert(values);
                } else {
                    ContentValues values = new ContentValues();
                    values.put(DBWords.RecentListen.TIME, System.currentTimeMillis());
                    recentTable.update(values,DBWords.RecentListen.AUDIO_ID + "= ?",new String[]{audio.getAudioId()});
                }
            }
        });
    }


    //################################

    private TableWrapper getTableWrapper(String name){
        TableWrapper table = tables.get(name);
        if(table == null){
            switch (name){
            case DBWords.RecentListen.TABLE_NAME:
                table = new RecentListenTableWrapper(db);
                break;
            case DBWords.Local.TABLE_NAME:
                table = new LocalMusicTableWrapper(context);
                break;
            }
            tables.put(name,table);
        }
        return table;
    }

}
