package com.nnero.nnero.bean;

/**
 * Created by NNERO on 16/1/18.
 *
 * 封装一首歌
 */
public class Audio {
    //comefrom
    public static final int LOCAL = 1;

    private String audioId;
    private String audioDBId;
    private String name;
    private String artist;
    private String AlbumName;
    private String audioImage; //封面图
    private String source; //可能是url 或者 本地路径
    private int comeFrom;
    private long duration;
    private long size;

    public Audio(){}

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public String getAudioImage() {
        return audioImage;
    }

    public void setAudioImage(String audioImage) {
        this.audioImage = audioImage;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getAudioDBId() {
        return audioDBId;
    }

    public void setAudioDBId(String audioDBId) {
        this.audioDBId = audioDBId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(int comeFrom) {
        this.comeFrom = comeFrom;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "audioId='" + audioId + '\'' +
                ", audioDBId='" + audioDBId + '\'' +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", AlbumName='" + AlbumName + '\'' +
                ", audioImage='" + audioImage + '\'' +
                ", source='" + source + '\'' +
                ", comeFrom=" + comeFrom +
                ", duration=" + duration +
                ", size=" + size +
                '}';
    }
}
