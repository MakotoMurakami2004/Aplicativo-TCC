package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_video);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CardView cardVideo1 = findViewById(R.id.cardVideo1);
        CardView cardVideo2 = findViewById(R.id.phish2);
        CardView cardVideo3 = findViewById(R.id.cardVideo3);
        CardView cardVideo4 = findViewById(R.id.cardVideo4);
        ImageView btnHome = findViewById(R.id.btnHome);

        // Configura o clique do VÍDEO 1
        cardVideo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.video1);
            }
        });

        // Configura o clique do VÍDEO 2
        cardVideo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.video2);
            }
        });

        // Configura o clique do VÍDEO 3
        cardVideo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.video3);
            }
        });

        cardVideo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.video4);
            }
        });

        // Botão Home para voltar à tela principal
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(video.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Função auxiliar que abre a tela PlayerVideo passando o ID do arquivo MP4
    private void abrirPlayer(int videoRawId) {
        Intent intent = new Intent(video.this, PlayerVideo.class);
        intent.putExtra("VIDEO_RES_ID", videoRawId);
        startActivity(intent);
    }
}