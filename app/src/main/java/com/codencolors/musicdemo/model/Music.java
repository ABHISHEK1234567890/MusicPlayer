package com.codencolors.musicdemo.model;

public class Music {

    private String songName;
    private int songSize;
    private String artist;
    private String source;
    private String songPath;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongSize() {
        return songSize;
    }

    public void setSongSize(int songSize) {
        this.songSize = songSize;
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

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
}
