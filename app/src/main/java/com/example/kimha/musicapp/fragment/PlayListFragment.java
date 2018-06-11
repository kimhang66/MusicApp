package com.example.kimha.musicapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kimha.musicapp.Adapter.PlaylistAdapter;
import com.example.kimha.musicapp.Api.DoGetSong;
import com.example.kimha.musicapp.Api.DoPost;
import com.example.kimha.musicapp.CallBack;
import com.example.kimha.musicapp.Model.Playlist;
import com.example.kimha.musicapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PlayListFragment extends Fragment {

    private List<Playlist> playlists;
    private RecyclerView lvPlaylist;
    private PlaylistAdapter playlistAdapter;
    private Button btnAddPlaylist;

    public PlayListFragment() {
        // Required empty public constructor
    }

    public static PlayListFragment newInstance() {
        PlayListFragment fragment = new PlayListFragment();
        return fragment;
    }

    //nghe noi gi ko ko nghe

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_play_list, container, false);
        init(view);
        return view;
    }

    private void init(View v){
        playlists = new ArrayList<>();
        lvPlaylist = v.findViewById(R.id.listPlayList);
        btnAddPlaylist = v.findViewById(R.id.btnAddPlaylist);
        initPlaylist(v);
        addNewPlayList();
    }

    private void addNewPlayList() {
        btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add New Playlist");
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add_playlist, null);
                builder.setView(view);

                final EditText edtAddPlaylist = view.findViewById(R.id.edtNamePlaylist);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addPlayList(edtAddPlaylist.getText().toString(), playlistAdapter, playlists);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void initPlaylist(View view){
        lvPlaylist.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(
                view.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        lvPlaylist.setLayoutManager(manager);
        playlistAdapter = new PlaylistAdapter(getContext(), playlists, 0);
        lvPlaylist.setAdapter(playlistAdapter);

        loadPlayList();
    }

    private void loadPlayList(){
        new DoGetSong(new CallBack<String>() {
            @Override
            public void success(String object) {
                try {
                    JSONArray jsonArray = new JSONArray(object);
                    for(int i = 0; i < jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        playlists.add(new Playlist(
                                jsonObject.getInt("idPlaylist"),
                                jsonObject.getString("namePlaylist")
                        ));
                    }
                    playlistAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(String object) {

            }
        }).execute("/playlistsApi?idUser=1");
    }

    private void addPlayList(String name, final PlaylistAdapter adapter, final List<Playlist> playlists){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nameplaylist", name);
            jsonObject.put("iduser", 1);//id user
            new DoPost(new CallBack<String>() {
                @Override
                public void success(String object) {
                    try {
                        JSONObject object1 = new JSONObject(object);
                        playlists.add(new Playlist(
                                object1.getInt("idplaylist"),
                                object1.getString("nameplaylist")
                        ));
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void error(String object) {

                }
            }, jsonObject).execute("/playlistsApi");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
