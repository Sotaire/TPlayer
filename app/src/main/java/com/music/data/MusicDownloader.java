package com.music.data;

import android.content.Context;
import android.os.AsyncTask;

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
                        mediaMetaData.setMediaArt("art");
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
