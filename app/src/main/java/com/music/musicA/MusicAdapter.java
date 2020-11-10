package com.music.musicA;

import android.media.MediaMetadata;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.music.R;

import java.util.ArrayList;
import java.util.List;

import dm.audiostreamer.MediaMetaData;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    List<MediaMetaData> metadata = new ArrayList<>();
    ClickListener listener;

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public void setMetadata(List<MediaMetaData> metadata) {
        this.metadata = metadata;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_view_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.description.setText(metadata.get(position).getMediaTitle());
        holder.title.setText(metadata.get(position).getMediaArtist());
        holder.imageView.setImageResource(R.drawable.ic_action_play);
    }

    @Override
    public int getItemCount() {
        return metadata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.play_pause_iv);
            description = itemView.findViewById(R.id.description);
            imageView.setOnClickListener(view -> listener.onClick(getAdapterPosition()));
        }
    }
}
