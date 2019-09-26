package com.codencolors.musicdemo.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Movie;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import com.codencolors.musicdemo.R;
import com.codencolors.musicdemo.adapter.MusicAdapter;
import com.codencolors.musicdemo.helper.RecyclerTouchListener;
import com.codencolors.musicdemo.model.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_music) RecyclerView recyclerView;

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private MediaPlayer musicPlayer;
    private boolean isPlayed = false;
    private List<Music> songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMusicList();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            setRecyclerView();
        else
            requestRead();

        onRecyclerViewClick();
    }

    //set layout manager to recyclerView
    private void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MusicAdapter musicAdapter = new MusicAdapter(songsList, this);
        recyclerView.setAdapter(musicAdapter);
    }

    //take read permission from user
    private void requestRead() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    private void onRecyclerViewClick(){
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                playMusic(songsList.get(position).getSongPath());
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    //get list of music
    private void getMusicList(){
        Cursor cursor;
        songsList = new ArrayList<>();

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE
        };

        cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        while(cursor.moveToNext()){
            Music music = new Music();
            music.setSongName(cursor.getString(2));
            music.setArtist(cursor.getString(4));
            music.setSongPath(cursor.getString(3));
            music.setSongSize(cursor.getInt(5));
            music.setSource(cursor.getString(1));
            songsList.add(music);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if  (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    requestRead();
                break;
        }
    }

    void playMusic(String musicPath) {
        if (isPlayed)
            stopMusic();

        try {
            startMusic(musicPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startMusic(String fileName ) throws IOException {
        isPlayed = true;
        musicPlayer = new MediaPlayer();
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicPlayer.setDataSource(fileName);
        musicPlayer.prepareAsync();
        musicPlayer.setOnPreparedListener(mp -> musicPlayer.start());
    }

    public void stopMusic(){
        musicPlayer.stop();
        musicPlayer.release();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (musicPlayer != null) {
            musicPlayer.reset();
            musicPlayer.release();
            musicPlayer = null;
        }
    }
}
