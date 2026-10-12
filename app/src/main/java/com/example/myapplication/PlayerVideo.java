package com.example.myapplication;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PlayerVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_video);

        VideoView videoView = findViewById(R.id.localVideoView);

        // Recebe o ID do vídeo vindo da tela de lista (video.java)
        int videoResId = getIntent().getIntExtra("VIDEO_RES_ID", 0);

        if (videoResId == R.raw.video7) {

            ConstraintLayout.LayoutParams params =
                    (ConstraintLayout.LayoutParams) videoView.getLayoutParams();

            params.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;

            videoView.setLayoutParams(params);
        }

        if (videoResId != 0) {
            // Monta o caminho do vídeo dentro da pasta res/raw
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
            videoView.setVideoURI(uri);

            // Adiciona a barra com controles nativos (Play/Pause/Scroll)
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            // Inicia a reprodução assim que carregar
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.requestFocus();
                    videoView.start();
                }
            });

        } else {
            Toast.makeText(this, "Nenhum vídeo foi encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}