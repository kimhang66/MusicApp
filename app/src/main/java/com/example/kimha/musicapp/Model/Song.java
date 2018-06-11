package com.example.kimha.musicapp.Model;

/**
 * Created by kimha on 5/20/2018.
 */

public class Song {
    private int idSong;
    private String nameSong;
    private String imgSong;
    private String nameAuthor;
    private String nameSinger;
    private String nameKind;
    private String linkGG;

    public Song() {
    }

    public Song(int idSong, String nameSong, String imgSong, String nameAuthor, String nameSinger, String nameKind, String linkGG) {
        this.idSong = idSong;
        this.nameSong = nameSong;
        this.imgSong = imgSong;
        this.nameAuthor = nameAuthor;
        this.nameSinger = nameSinger;
        this.nameKind = nameKind;
        this.linkGG = linkGG;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getImgSong() {
        return imgSong;
    }

    public void setImgSong(String imgSong) {
        this.imgSong = imgSong;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getNameKind() {
        return nameKind;
    }

    public void setNameKind(String nameKind) {
        this.nameKind = nameKind;
    }

    public String getLinkGG() {
        return linkGG;
    }

    public void setLinkGG(String linkGG) {
        this.linkGG = linkGG;
    }

    @Override
    public String toString() {
        return "Song{" +
                "idSong=" + idSong +
                ", nameSong='" + nameSong + '\'' +
                ", imgSong='" + imgSong + '\'' +
                ", nameAuthor='" + nameAuthor + '\'' +
                ", nameSinger='" + nameSinger + '\'' +
                ", nameKind='" + nameKind + '\'' +
                ", linkGG='" + linkGG + '\'' +
                '}';
    }
}
