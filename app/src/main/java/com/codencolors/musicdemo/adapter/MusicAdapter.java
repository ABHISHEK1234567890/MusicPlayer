package com.codencolors.musicdemo.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codencolors.musicdemo.R;
import com.codencolors.musicdemo.model.Music;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private static final String TAG = "MusicAdapter";

    private List<Music> musicList;
    private Context context;


    public MusicAdapter(List<Music> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate( R.layout.row_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Music music = musicList.get(position);
        holder.lblMusicName.setText(music.getSongName());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lbl_music_name) TextView lblMusicName;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
