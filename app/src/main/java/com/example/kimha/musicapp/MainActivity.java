package com.example.kimha.musicapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimha.musicapp.Adapter.TabAdapter;
import com.example.kimha.musicapp.fragment.HomeFragment;
import com.example.kimha.musicapp.fragment.PlayListFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager vpForTabLayout;
    public static TextView nameSong;
    public static ImageButton btnPlay, btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setTabLayout();
    }

    private void init(){
        tabLayout = findViewById(R.id.sliding_tabs);
        vpForTabLayout = findViewById(R.id.viewpager);
        nameSong = findViewById(R.id.nameSong);
        btnNext = findViewById(R.id.btnNext);
        btnPlay = findViewById(R.id.btnPlayPause);
        playPause();
        next();
    }

    private void setTabLayout(){
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(HomeFragment.newInstance(), "Home");
        tabAdapter.addFragment(PlayListFragment.newInstance(), "Play List");
        vpForTabLayout.setAdapter(tabAdapter);
        vpForTabLayout.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(vpForTabLayout);
    }

    private void playPause(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayerInstance.getInstance().getState() == Const.PLAY){
                    PlayerInstance.getInstance().onPause();
                    btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else if(PlayerInstance.getInstance().getState() == Const.PAUSE){
                    PlayerInstance.getInstance().onResume();
                    btnPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        });
    }
    private void next(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(PlayerInstance.getInstance().getSongList().size() > 0){
                   PlayerInstance.getInstance().onNext();
               }else {
                   Toast.makeText(getApplicationContext(), "No Song", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}
