package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. CONFIGURAÇÃO DAS BARRAS DO SISTEMA (Deixe esse bloco isolado aqui)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. CONFIGURAÇÃO DO BOTÃO VERDE (Fica livre dentro do onCreate)
        // ATENÇÃO: Mude o "cardVerdeBotao" abaixo para o ID exato que está no seu XML!
        CardView cardVerificar = findViewById(R.id.cardVerdeBotao);
        CardView cardVideoPhish = findViewById(R.id.idPhish);
        CardView VideosInformativos = findViewById(R.id.idvideo);
        CardView Quizz = findViewById(R.id.cardQuizzes);
        CardView SenhaEmail = findViewById(R.id.cardVideos);


        cardVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria a intenção de ir da MainActivity para a VerificarLink
                Intent intent = new Intent(MainActivity.this, VerificarLink.class);
                startActivity(intent);
            }
        });

        cardVideoPhish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria a intenção de ir da MainActivity para a VerificarLink
                Intent intent = new Intent(MainActivity.this, phishing.class);
                startActivity(intent);
            }
        });

        VideosInformativos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria a intenção de ir da MainActivity para a video
                Intent intent = new Intent(MainActivity.this, video.class);
                startActivity(intent);
            }
        });

        Quizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria a intenção de ir da MainActivity para a Quizz
                Intent intent = new Intent(MainActivity.this, Quizz.class);
                startActivity(intent);
            }
        });

        SenhaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria a intenção de ir da MainActivity para a SenhaEmail
                Intent intent = new Intent(MainActivity.this, SenhaEmail.class);
                startActivity(intent);
            }
        });



    }
}