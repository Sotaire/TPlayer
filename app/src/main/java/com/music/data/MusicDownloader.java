package com.music.data;

import android.content.Context;
import android.os.AsyncTask;

import com.music.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dm.audiostreamer.MediaMetaData;

public class MusicDownloader {
    public static ArrayList<String> songsURL = new ArrayList<>();
    public static ArrayList<MediaMetaData> songModels = new ArrayList<>();

    public static final String urlHome = "http://hotcharts.ru/";

    private static final String BASE_URL_FOR_SONG = "http://45.88.104.12//hc/preview/temp_067TG/";
    private static final String MP3 = ".mp3";

    public static void loadMusic(final Context context, final MusicDownloaderListener loaderListener) {

        final AsyncTask<Void, Void, Void> loadTask = new AsyncTask<Void, Void, Void>() {
            String[] resp = {"", ""};
            List<MediaMetaData> listMusic = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... voids) {
                //resp = getDataResponse();
                try {
                    Document doc = Jsoup.connect(urlHome)
                            .referrer("http://hotcharts.ru/")
                            .get();
                    final Elements els = doc.select("td[class=song]");
                    for (int i = 0; i < els.size(); i++) {
                        final Element as = els.get(i);
                        String url1 = as.getElementsByTag("a").attr("href");
                        songsURL.add(url1);
                        String artist = as.getElementsByTag("a").attr("data-artist-name");
                        String song = as.getElementsByTag("a").attr("data-song-name");
                        MediaMetaData mediaMetaData = new MediaMetaData();
                        mediaMetaData.setMediaArtist(artist);
                        mediaMetaData.setMediaTitle(song);
                        mediaMetaData.setMediaArt("data:image/jpeg;base64,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");
                        mediaMetaData.setMediaAlbum("album");
                        mediaMetaData.setMediaComposer("composer");
                        mediaMetaData.setMediaDuration("4");
                        mediaMetaData.setMediaId("id" + i);
                        songModels.add(mediaMetaData);
                    }

                    for (int i = 0; i < songsURL.size(); i++) {
                        char[] chars = songsURL.get(i).toString().toCharArray();
                        String mp3Url = "";
                        for (int j = 0; j < chars.length; j++) {
                            if (chars[j] != '#') {
                                if (chars[j] == ' ') {
                                    mp3Url = mp3Url + "%20";
                                } else if (chars[j] == '\'') {
                                    mp3Url = mp3Url + "%27";
                                } else if (chars[j] == '&') {
                                    mp3Url = mp3Url + "%26";
                                } else if (chars[j] == ')') {
                                    mp3Url = mp3Url + "%29";
                                } else if (chars[j] == '(') {
                                    mp3Url = mp3Url + "%28";
                                } else {
                                    mp3Url = mp3Url + chars[j];
                                }
                            }
                        }
                        final String url = BASE_URL_FOR_SONG + mp3Url + MP3;
                        songModels.get(i).setMediaUrl(url);
                    }

                } catch (
                        IOException e) {
                    e.printStackTrace();
                }

                listMusic = songModels;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (loaderListener != null && listMusic != null && listMusic.size() >= 1) {
                    loaderListener.onLoadSuccess(listMusic);
                } else {
                    loaderListener.onLoadFailed();
                }
            }
        }
                ;
        loadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    }
