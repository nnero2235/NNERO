package com.nnero.nnero.db;

/**
 * Created by NNERO on 16/1/21.
 *
 * 数据库字段
 */
public class DBWords {

    public static class RecentListen {
        public static final String TABLE_NAME = "recent_listen";//表名
        public static final String ID = "s_id";
        public static final String AUDIO_ID = "s_audio_id";
        public static final String NAME = "s_name";
        public static final String ALBUM = "s_album";
        public static final String ARTIST = "s_artist";
        public static final String COME_FROM = "s_come_from";
        public static final String IMAGE = "s_image";
        public static final String DURATION = "s_duration";
        public static final String SIZE = "s_size";
        public static final String SOURCE = "s_source"; //可能是url 或者本地 路径
        public static final String TIME = "s_time"; //时间戳
    }

    public static class Local{
        public static final String TABLE_NAME = "local";
    }
}
