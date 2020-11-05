package com.music.data;

import java.util.List;

import dm.audiostreamer.MediaMetaData;

public interface MusicDownloaderListener {

    void onLoadSuccess(List<MediaMetaData> listMusic);

    void onLoadFailed();

}
