package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;


public class VerificarLink extends AppCompatActivity {
    EditText editUrl;
    Button btnVerificar;
    TextView txtResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verificar_link);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        ImageView btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerificarLink.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editUrl = findViewById(R.id.editTextText2);
        btnVerificar = findViewById(R.id.verificar_link);
        txtResultado = findViewById(R.id.txtResultado);
    }
    public void verificarLink(View view) {
        String url = editUrl.getText().toString().trim();

        if (url.isEmpty()) {
            Toast.makeText(this, "Digite uma URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(this));
            }

            Python py = Python.getInstance();
            PyObject modulo = py.getModule("modelo");

            // Executa a função do Python
            PyObject resultadoDict = modulo.callAttr("verificar_url", url);

            
            int classe = resultadoDict.callAttr("get", "resultado").toInt();
            double probPhishing = resultadoDict.callAttr("get", "phishing").toDouble() * 100;
            double probSeguro = resultadoDict.callAttr("get", "seguro").toDouble() * 100;

            // Atualiza o resultado na tela
            if (classe == 1) {
                txtResultado.setText(String.format("ALERTA: LINK SUSPEITO!\nConfiança de Golpe: %.1f%%", probPhishing));
                txtResultado.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                txtResultado.setText(String.format("SEGURO: LINK LEGÍTIMO\nConfiança: %.1f%%", probSeguro));
                txtResultado.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }

        } catch (Exception e) {
            e.printStackTrace();
            txtResultado.setText("Erro ao analisar: " + e.getMessage());
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}