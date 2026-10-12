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

public class phishing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_phishing);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CardView Video1 = findViewById(R.id.phish1);
        CardView Video2 = findViewById(R.id.phish2);
        CardView Video3 = findViewById(R.id.engensocial1);
        ImageView btnHome = findViewById(R.id.btnHome);

        // Configura o clique do VÍDEO 1
        Video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.video5);
            }
        });

        // Configura o clique do VÍDEO 2
        Video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.video6);
            }
        });

        Video3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.video7);
            }
        });

        // Botão Home para voltar à tela principal
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(phishing.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Função auxiliar que abre a tela PlayerVideo passando o ID do arquivo MP4
    private void abrirPlayer(int videoRawId) {
        Intent intent = new Intent(phishing.this, PlayerVideo.class);
        intent.putExtra("VIDEO_RES_ID", videoRawId);
        startActivity(intent);
    }
}