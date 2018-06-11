package com.example.kimha.musicapp.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kimha.musicapp.Adapter.SongAdapter;
import com.example.kimha.musicapp.Api.DoDelete;
import com.example.kimha.musicapp.Api.DoGetSong;
import com.example.kimha.musicapp.CallBack;
import com.example.kimha.musicapp.Model.Song;
import com.example.kimha.musicapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView lvSong;
    SongAdapter songAdapter;
    List<Song> listSong;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        initSong(view);
        return view;
    }

    private void init(View view){
        lvSong = view.findViewById(R.id.listSong);
        listSong = new ArrayList<>();
    }

    private void initSong(View view){
        lvSong.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(
                view.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        lvSong.setLayoutManager(manager);
        songAdapter = new SongAdapter(getContext(), listSong);
        lvSong.setAdapter(songAdapter);
        getSongs();
    }

    private void getSongs(){
        new DoGetSong(new CallBack<String>() {
            @Override
            public void success(String object) {
                try {
                    JSONArray jsonArray = new JSONArray(object);
                    Log.i("AAA", object);
                    for (int i = 0; i < jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        listSong.add(new Song(
                                jsonObject.getInt("idSong"),
                                 jsonObject.getString("nameSong"),
                                jsonObject.getString("imgSong"),
                                jsonObject.getString("nameAuthor"),
                                jsonObject.getString("nameSinger"),
                                jsonObject.getString("nameKind"),
                                jsonObject.getString("linkGG")
                        ));
                    }
                    songAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(String object) {

            }
        }).execute("/songsapi");
    }


}
