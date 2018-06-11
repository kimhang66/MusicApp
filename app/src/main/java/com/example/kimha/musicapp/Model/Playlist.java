package com.example.kimha.musicapp.Model;

/**
 * Created by kimha on 5/26/2018.
 */

public class Playlist {
    private int idPlaylist;
    private String namePlaylist;

    public Playlist() {
    }

    public Playlist(int idPlaylist, String namePlaylist) {
        this.idPlaylist = idPlaylist;
        this.namePlaylist = namePlaylist;
    }

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getNamePlaylist() {
        return namePlaylist;
    }

    public void setNamePlaylist(String namePlaylist) {
        this.namePlaylist = namePlaylist;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "idPlaylist=" + idPlaylist +
                ", namePlaylist='" + namePlaylist + '\'' +
                '}';
    }
}
