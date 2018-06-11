package com.example.kimha.musicapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimha.musicapp.Api.DoDelete;
import com.example.kimha.musicapp.Api.DoGetSong;
import com.example.kimha.musicapp.Api.DoPost;
import com.example.kimha.musicapp.CallBack;
import com.example.kimha.musicapp.MainActivity;
import com.example.kimha.musicapp.Model.Playlist;
import com.example.kimha.musicapp.Model.Song;
import com.example.kimha.musicapp.R;
import com.example.kimha.musicapp.fragment.HomeFragment;
import com.example.kimha.musicapp.fragment.PlayListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimha on 5/26/2018.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private Context context;
    private List<Playlist> playlists;
    private int idSong;

    public PlaylistAdapter(Context context, List<Playlist> playlists, int idSong) {
        this.context = context;
        this.playlists = playlists;
        this.idSong = idSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.namePlaylist.setText(playlists.get(position).getNamePlaylist());
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namePlaylist;
        public ViewHolder(View itemView) {
            super(itemView);
            namePlaylist = itemView.findViewById(R.id.txtPlaylistName);

            if(idSong == 0){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSong(playlists.get(getAdapterPosition()).getIdPlaylist());
                    }
                });

            }else {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addSongToPlaylist(idSong, playlists.get(getAdapterPosition()).getIdPlaylist());
                    }
                });
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deletePlayList(getAdapterPosition());
                    return true;
                }
            });


        }
    }

    private void addSongToPlaylist(int idSong, int idPlaylist) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("idsong", idSong);
            jsonObject.put("idPlaylist", idPlaylist);
            new DoPost(new CallBack<String>() {
                @Override
                public void success(String object) {
                    Toast.makeText(context, "Thành công!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void error(String object) {

                }
            }, jsonObject).execute("/detailplaylistsApi");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    final SongAdapter songAdapter = null;
    final List<Song> songs = null;


    private void showSong(int idPlaylist){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.show_song, null);
        builder.setView(v);


        final List<Song> songs = new ArrayList<>();
        RecyclerView lvShowSong = v.findViewById(R.id.showSong);
        final SongAdapter songAdapter;


        lvShowSong.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(
                v.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        lvShowSong.setLayoutManager(manager);
        songAdapter =  new SongAdapter(context, songs);
        lvShowSong.setAdapter(songAdapter);


        new DoGetSong(new CallBack<String>() {
            @Override
            public void success(String object) {
                try {
                    JSONArray jsonArray = new JSONArray(object);
                    Log.i("AAA", object);
                    for (int i = 0; i < jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        songs.add(new Song(
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
        }).execute("/playlistsApi/"+idPlaylist);

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
            }
        });
        builder.show();
    }

    private void deletePlayList(final int idPL){
        new DoDelete(new CallBack<String>() {
            @Override
            public void success(String object) {
                playlists.remove(playlists.get(idPL));
                notifyDataSetChanged();
            }

            @Override
            public void error(String object) {

            }
        }).execute("/playlistsApi/"+playlists.get(idPL).getIdPlaylist());
    }
}
