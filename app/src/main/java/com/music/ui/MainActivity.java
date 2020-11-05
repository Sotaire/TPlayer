package com.music.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.Toast;

import com.music.R;
import com.music.data.MusicDownloader;
import com.music.data.MusicDownloaderListener;
import com.music.musicA.ClickListener;
import com.music.musicA.MusicAdapter;

import java.util.ArrayList;
import java.util.List;

import dm.audiostreamer.AudioStreamingManager;
import dm.audiostreamer.CurrentSessionCallback;
import dm.audiostreamer.MediaMetaData;

public class MainActivity extends AppCompatActivity implements CurrentSessionCallback {

    Context context;
    AudioStreamingManager streamingManager;
    RecyclerView recyclerView;
    MusicAdapter adapter;
    List<MediaMetaData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        streamingManager = AudioStreamingManager.getInstance(context);
        recyclerView = findViewById(R.id.music_recycler);
        adapter = new MusicAdapter();
        loadM();
        click();
    }

    private void loadM() {
        MusicDownloader.loadMusic(this, new MusicDownloaderListener() {
            @Override
            public void onLoadSuccess(List<MediaMetaData> listMusic) {
                data = listMusic;
                adapter.setMetadata(data);

                playing();
            }

            @Override
            public void onLoadFailed() {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playing() {
        streamingManager = AudioStreamingManager.getInstance(context);
        //Set PlayMultiple 'true' if want to playing sequentially one by one songs
        // and provide the list of songs else set it 'false'
        streamingManager.setPlayMultiple(true);
        streamingManager.setMediaList(data);
        //If you want to show the Player Notification then set ShowPlayerNotification as true
        //and provide the pending intent so that after click on notification it will redirect to an activity
        streamingManager.setShowPlayerNotification(true);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction("openplayer");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        streamingManager.setPendingIntentAct(mPendingIntent);
    }

    private void click() {
        adapter.setListener(new ClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (streamingManager != null) {
            streamingManager.subscribesCallBack(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (streamingManager != null) {
            streamingManager.unSubscribeCallBack();
        }
    }

    @Override
    public void updatePlaybackState(int state) {
        switch (state) {
            case PlaybackStateCompat.STATE_PLAYING:
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                break;
            case PlaybackStateCompat.STATE_NONE:
                break;
            case PlaybackStateCompat.STATE_STOPPED:
                break;
            case PlaybackStateCompat.STATE_BUFFERING:
                break;
        }
    }

    @Override
    public void playSongComplete() {
    }

    @Override
    public void currentSeekBarPosition(int progress) {
    }

    @Override
    public void playCurrent(int indexP, MediaMetaData currentAudio) {
    }

    @Override
    public void playNext(int indexP, MediaMetaData CurrentAudio) {
    }

    @Override
    public void playPrevious(int indexP, MediaMetaData currentAudio) {
    }

}