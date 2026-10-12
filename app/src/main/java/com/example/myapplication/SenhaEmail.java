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

public class SenhaEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_senha_email);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CardView VideoEmail1 = findViewById(R.id.email);
        CardView VideoEmail2 = findViewById(R.id.email2);
        CardView VideoSenha1 = findViewById(R.id.senha1);
        CardView VideoCuidados1 = findViewById(R.id.emailesenha);
        CardView VideoCuidados2 = findViewById(R.id.emailesenha2);
        ImageView btnHome = findViewById(R.id.btnHome);


        VideoEmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.videoemail);
            }
        });

        VideoEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.videoemail1);
            }
        });

        VideoSenha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.senha1);
            }
        });

        VideoCuidados1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.emailesenha1);
            }
        });

        VideoCuidados2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPlayer(R.raw.emailesenha2);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SenhaEmail.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }




    private void abrirPlayer(int videoRawId) {
        Intent intent = new Intent(SenhaEmail.this, PlayerVideo.class);
        intent.putExtra("VIDEO_RES_ID", videoRawId);
        startActivity(intent);
    }
}

