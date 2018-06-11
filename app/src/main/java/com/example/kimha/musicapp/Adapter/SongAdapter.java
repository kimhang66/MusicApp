package com.example.kimha.musicapp.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimha.musicapp.Api.DoDelete;
import com.example.kimha.musicapp.Api.DoGetSong;
import com.example.kimha.musicapp.CallBack;
import com.example.kimha.musicapp.Const;
import com.example.kimha.musicapp.MainActivity;
import com.example.kimha.musicapp.Model.Playlist;
import com.example.kimha.musicapp.Model.Song;
import com.example.kimha.musicapp.PlayerInstance;
import com.example.kimha.musicapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimha on 5/23/2018.
 */

public class SongAdapter  extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
    private Context context;
    private List<Song> songList;

    public SongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context)
                .load(songList.get(position).getImgSong())
                .resize(130, 130)
                .centerCrop()
                .into(holder.cover);
        holder.nameSinger.setText(songList.get(position).getNameSinger());
        holder.nameSong.setText(songList.get(position).getNameSong());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView nameSong, nameSinger;
        public ViewHolder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.imageSong);
            nameSong = itemView.findViewById(R.id.txtNameSong);
            nameSinger = itemView.findViewById(R.id.txtNameSinger);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.option_song);

                    Button btnPlaySong = (Button)dialog.findViewById(R.id.btnplaysong);
                    Button btnDeleteSong = (Button)dialog.findViewById(R.id.btndeletesong);

                    btnPlaySong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PlayerInstance.getInstance().play(songList.get(getAdapterPosition()));
                            MainActivity.nameSong.setText(songList.get(getAdapterPosition()).getNameSong());
                        }
                    });
                    btnDeleteSong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteSong(getAdapterPosition());
                        }
                    });

                    dialog.show();
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    addToPlayList(songList.get(getAdapterPosition()).getIdSong());
                    return false;
                }
            });
        }
    }
    private void addToPlayList(int idSong){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose the Playlist");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_play_list, null);
        builder.setView(view);

        List<Playlist> playlists = new ArrayList<>();
        RecyclerView lvPlaylist = view.findViewById(R.id.listPlayList);
        PlaylistAdapter playlistAdapter;
        Button btnAddPlaylist = view.findViewById(R.id.btnAddPlaylist);
        btnAddPlaylist.setVisibility(View.GONE);


        lvPlaylist.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(
                view.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        lvPlaylist.setLayoutManager(manager);
        playlistAdapter = new PlaylistAdapter(context, playlists, idSong);
        lvPlaylist.setAdapter(playlistAdapter);

        getPlayList(playlistAdapter, playlists);


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void getPlayList(final PlaylistAdapter playlistAdapter, final List<Playlist> playlists) {
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

    private void deleteSong(final int idSong){
        new DoDelete(new CallBack<String>() {
            @Override
            public void success(String object) {
                songList.remove(songList.get(idSong));
                notifyDataSetChanged();
            }

            @Override
            public void error(String object) {

            }
        }).execute("/songsapi/"+songList.get(idSong).getIdSong());
    }
}
