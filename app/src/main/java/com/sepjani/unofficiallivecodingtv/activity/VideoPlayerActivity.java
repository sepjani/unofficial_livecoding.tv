package com.sepjani.unofficiallivecodingtv.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.google.android.exoplayer.AspectRatioFrameLayout;
import com.sepjani.unofficiallivecodingtv.R;
import com.sepjani.unofficiallivecodingtv.api.RestAPIClient;
import com.sepjani.unofficiallivecodingtv.api.models.ViewingKeyModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayerActivity extends AppCompatActivity implements OnPreparedListener {

    private EMVideoView videoPlayer;
    private AspectRatioFrameLayout aspectFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        videoPlayer = (EMVideoView) findViewById(R.id.video_view);

//        aspectFrame = (AspectRatioFrameLayout) findViewById(R.id.video_frame_aspect);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setupVideoView();
    }

    private void setupVideoView() {
        new RestAPIClient()
                .getAPI()
                .getViewingKey()
                .enqueue(new Callback<ViewingKeyModel>() {
                    @Override
                    public void onResponse(Call<ViewingKeyModel> call, Response<ViewingKeyModel> response) {
                        System.out.println("VIEWING KEY = " + response.body().viewingKey);
                        videoPlayer.setOnPreparedListener(VideoPlayerActivity.this);
                        String testVideoURL = "https://eumedia4.livecoding.tv:8443/vod/toby3d-1464292246.mp4";
                        testVideoURL += "?t=" + response.body().viewingKey;
                        System.out.println("Play video = " + testVideoURL);
                        videoPlayer.setVideoURI(Uri.parse(testVideoURL));
                    }

                    @Override
                    public void onFailure(Call<ViewingKeyModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


    }

    @Override
    public void onPrepared() {
//        aspectFrame.setAspectRatio(videoPlayer.getMeasuredWidth());
//        int width = videoPlayer.getMeasuredWidth();
//        int height = videoPlayer.getMeasuredHeight();
//        int pixelWidthAspectRatio = 2;
//        aspectFrame.setAspectRatio(
//                height == 0 ? 1 : (width * pixelWidthAspectRatio) / height);


        int width = videoPlayer.getMeasuredWidth();
        float aspectRatio = 2.2f;
        int height = (int) (width / aspectRatio);
//        (left, top, right, bottom
        videoPlayer.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        videoPlayer.start();
    }
}
