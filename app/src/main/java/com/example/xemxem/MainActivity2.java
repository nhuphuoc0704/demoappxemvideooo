package com.example.xemxem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.browse.MediaBrowser;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.xemxem.databinding.ActivityMain2Binding;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;
import java.util.List;

import home.item.video.Video;
import model.User;
import watchvideo.IWatchVideo;
import watchvideo.PresenterSQLVideo;

public class MainActivity2 extends Activity implements IWatchVideo  {
    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    ActivityMain2Binding binding;
    PresenterSQLVideo presenterSQLVideo;
    ImageView btFullScreen;
    boolean like=true;
    int i=1;
    ProgressBar process;
    long lastPosition;
    int currentWindow;
    SimpleExoPlayer simpleExoPlayer;
    ImaAdsLoader adsLoader;
    List<Video> list;
    int positionPlayer;
    boolean flag = false;
    float down, up;
    String ads;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);

        sharedPreferences = getBaseContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        presenterSQLVideo= new PresenterSQLVideo(this,getBaseContext());
        btFullScreen = binding.pvVideo.findViewById(R.id.bt_fullscreen);
        process = binding.pvVideo.findViewById(R.id.progressbarr);
        if(sharedPreferences.getBoolean("isPlayAd",true))
            ads= "";
        else ads= getString(R.string.ad_tag_url);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        positionPlayer = (int) bundle.get("position");
        list = (List<Video>) i.getSerializableExtra("list");
        adsLoader = new ImaAdsLoader.Builder(this).build();
        
        if(sharedPreferences.getBoolean("full",false)){
            binding.imgLike.setVisibility(View.GONE);
            binding.tvtitleVideoPlaying.setVisibility(View.GONE);
            binding.imgbExit.setVisibility(View.GONE);
        }


        exoPlayer();

        binding.imgbExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isPlayContinue",false);
                editor.apply();
                finish();
            }
        });
        binding.pvVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                //compat.onTouchEvent(e);
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        down = e.getX();
                        binding.pvVideo.showController();
                        return true;
                    case MotionEvent.ACTION_MOVE:

                    case MotionEvent.ACTION_UP:
                        up = e.getX();
                        long p = simpleExoPlayer.getCurrentPosition();
                        p = (long) (p + (up - down) * 5);

                        simpleExoPlayer.seekTo(Math.min(p, simpleExoPlayer.getDuration()));
                        return false;
                }
                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        binding.tvtitleVideoPlaying.setText(list.get(simpleExoPlayer.getCurrentWindowIndex()).getTitle());
        simpleExoPlayer.setPlayWhenReady(true);
        if(!sharedPreferences.getBoolean("isPlayAd",true)){
            adsLoader.onIsPlayingChanged(true);

        }
        if(sharedPreferences.getBoolean("isPlayContinue",false)){
            long lastPosition1= sharedPreferences.getLong("lastPosition",0);
            int currentWindow1= sharedPreferences.getInt("currentWindow",0);
            simpleExoPlayer.seekTo(currentWindow1,lastPosition1);
        }



        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsLoader.release();

    }



    public void exoPlayer() {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));

        MediaSourceFactory mediaSourceFactory =
                new DefaultMediaSourceFactory(dataSourceFactory)
                        .setAdsLoaderProvider(unusedAdTagUri -> adsLoader)
                        .setAdViewProvider(binding.pvVideo);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).setMediaSourceFactory(mediaSourceFactory).build();
        binding.pvVideo.setPlayer(simpleExoPlayer);
        binding.pvVideo.setKeepScreenOn(true);
        adsLoader.setPlayer(simpleExoPlayer);

        if(sharedPreferences.getBoolean("isPlayAd",false))
            ads="";

        MediaItem[] mediaItems = new MediaItem[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mediaItems[i] = new MediaItem.Builder().setUri(Uri.parse(list.get(i).getUrlLink()))
                    .setAdTagUri(Uri.parse(ads))
                    .build();
        }

        simpleExoPlayer.setMediaItems(Arrays.asList(mediaItems), positionPlayer, C.TIME_UNSET);
        simpleExoPlayer.prepare();
        simpleExoPlayer.setPlayWhenReady(false);

        binding.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(like){
                    binding.tvLiked.setText("LIKED");
                    presenterSQLVideo.updateLikedVideo(list.get(simpleExoPlayer.getCurrentWindowIndex()));
                    like=false;
                }
                else {
                    binding.tvLiked.setText("");
                    presenterSQLVideo.updateUnLikedVideo(list.get(simpleExoPlayer.getCurrentWindowIndex()));
                    like=true;
                }
            }
        });

        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                if(reason==Player.EVENT_MEDIA_ITEM_TRANSITION){
                        like=true;
                        binding.tvLiked.setText("");
                        i++;
                       binding.tvtitleVideoPlaying.setText(list.get(simpleExoPlayer.getCurrentWindowIndex()).getTitle());
                        //adsLoader = new ImaAdsLoader.Builder(getBaseContext()).build();
                }
                if(i%2==1)
                    presenterSQLVideo.addWatchedVideo(list.get(simpleExoPlayer.getCurrentWindowIndex()));
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {


            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING)
                    process.setVisibility(View.VISIBLE);
                else if (playbackState == Player.STATE_READY)
                    process.setVisibility(View.GONE);


            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {


            }
        });
        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPosition= simpleExoPlayer.getCurrentPosition();
                currentWindow=simpleExoPlayer.getCurrentWindowIndex();
                editor.putLong("lastPosition", lastPosition);
                editor.putInt("currentWindow",currentWindow);
                editor.putBoolean("isPlayContinue",true);
                editor.putBoolean("isPlayAd",true);
                editor.apply();
                if (flag) {
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                    editor.putBoolean("full",false);
                    editor.apply();


                } else {
                    editor.putBoolean("full",true);
                    editor.apply();
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreenexit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


                    flag = true;
                }
            }
        });
    }

    @Override
    public void onSuccessFull() {

    }

    @Override
    public void onMessenger(String mes) {

    }
}