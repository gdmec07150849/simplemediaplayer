package com.gdmec.jacky.simplemediaplayer;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="SimpleMediaPlayer";
    private MediaPlayer mediaPlayer;
    private String path;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        Uri uri =intent.getData();
        if(uri!=null){
            path=uri.getPath();
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            setTitle(path);
            if(intent.getType().contains("audio")){
                setContentView(android.R.layout.simple_list_item_1);
                mediaPlayer=new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }else if(intent.getType().contains("video")){
                videoView =new VideoView(this);
                videoView.setVideoPath(path);
                videoView.setMediaController(new MediaController(this));
                videoView.start();
                setContentView(videoView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"开始");
        menu.add(0,2,0,"暂停");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                if(mediaPlayer==null||!mediaPlayer.isPlaying()){
                    Toast.makeText(this,"没有音乐文件，不需要暂停！",Toast.LENGTH_SHORT).show();
                }else{
                    mediaPlayer.pause();
                }
                break;
            case 2:
                if(mediaPlayer==null){
                    Toast.makeText(this,"没有文件，请选择",Toast.LENGTH_SHORT).show();
                }else{
                    mediaPlayer.start();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
        if(videoView!=null){
            videoView.stopPlayback();
        }
    }
}