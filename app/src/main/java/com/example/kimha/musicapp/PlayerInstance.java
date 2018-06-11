package com.example.kimha.musicapp;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.example.kimha.musicapp.Model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimha on 5/26/2018.
 */

public class PlayerInstance {
    private static PlayerInstance instance = null;
    private MediaPlayer mediaPlayer;
    private List<Song> songList;
    private String state;
    private Song currentSong;

    public static PlayerInstance getInstance() {
        if (instance == null) {
            instance = new PlayerInstance();
        }
        return instance;
    }

    private PlayerInstance() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        songList = new ArrayList<>();
        state = Const.STOP;
        currentSong = null;
    }

    public void play(final Song song){

        addSong(song);
        currentSong = song;
        mediaPlayer.reset();
        try{
            @SuppressLint("StaticFieldLeak") final AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    try {
                        mediaPlayer.setDataSource(strings[0]);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    try{
                        mediaPlayer.start();
                        state = Const.PLAY;
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            };
            task.execute(song.getLinkGG());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onNext();
            }
        });
        MainActivity.btnPlay.setImageResource(R.drawable.ic_pause_black_24dp);
        MainActivity.nameSong.setText(currentSong.getNameSong());
    }

    public void onPause() {
        if (state == Const.PLAY) {
            mediaPlayer.pause();
            state = Const.PAUSE;
        }
    }

    public void onResume() {
        if (state == Const.PAUSE) {
            mediaPlayer.start();
            state = Const.PLAY;
        }
    }

    public void onStop() {
        mediaPlayer.stop();
        state = Const.STOP;
        currentSong = null;
        songList = null;
    }

    public void onNext(){
        int position = songList.indexOf(currentSong);
        if (position + 1 < songList.size()) {
            currentSong = songList.get(position + 1);
            play(currentSong);
        } else {
            currentSong = songList.get(0);
            play(currentSong);
        }
    }

    public void removeSong(int id) {

    }

    public void addSong(Song song) {
        if (!songList.contains(song)) {
            songList.add(song);
        }
    }

    public static void setInstance(PlayerInstance instance) {
        PlayerInstance.instance = instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }
}