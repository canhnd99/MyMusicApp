package com.duccanh.musicappv1;

public class Song {

    private String name;
    private String singer;
    private int file;

    Song(String name, String singer, int file){
        this.name = name;
        this.singer = singer;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
