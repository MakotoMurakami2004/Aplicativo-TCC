package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Quizz extends AppCompatActivity {
    private ImageView btnHome;
    private Button enviar;

    private RadioGroup radioGroup;
    private RadioButton op1, op2, op3, op4;
    private TextView pergunta;
    int IndiceAtualPergunta = 0;
    int TotalPerguntas = Perguntas.perguntas.length;
    int Pontuacao = 0;
    String RespostaSelecionada = null;
    ArrayList<Integer> indicesSorteados = new ArrayList<>();
    int indiceReal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_quizz);

        // Adiciona todos os índices das perguntas
        for (int i = 0; i < Perguntas.perguntas.length; i++) {
            indicesSorteados.add(i);
        }
        // Embaralha a ordem das perguntas
        Collections.shuffle(indicesSorteados);

        // Inicialização dos elementos da tela
        btnHome = findViewById(R.id.btnHome);
        enviar = findViewById(R.id.btn_enviar);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        op4 = findViewById(R.id.op4);
        pergunta = findViewById(R.id.pergunta);
        radioGroup = findViewById(R.id.radioGroup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Carrega a primeira pergunta
        NovasPerguntas();

        // Botão Home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quizz.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Evento do Botão Enviar Corrigido
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Captura o texto da opção que o usuário selecionou
                if (op1.isChecked()) {
                    RespostaSelecionada = op1.getText().toString();
                } else if (op2.isChecked()) {
                    RespostaSelecionada = op2.getText().toString();
                } else if (op3.isChecked()) {
                    RespostaSelecionada = op3.getText().toString();
                } else if (op4.isChecked()) {
                    RespostaSelecionada = op4.getText().toString();
                } else {
                    // Impede de avançar se nenhuma opção for selecionada
                    Toast.makeText(Quizz.this, "Por favor, selecione uma opção!", Toast.LENGTH_SHORT).show();
                    return;
                }

                indiceReal = indicesSorteados.get(IndiceAtualPergunta);
                // 2. Compara a resposta escolhida com o gabarito DA PERGUNTA ATUAL
                if (RespostaSelecionada.equals(Perguntas.respostaCorreta[indiceReal])) {
                    Pontuacao++;
                }

                // 3. Avança o índice para a próxima pergunta
                IndiceAtualPergunta++;

                // 4. Limpa os RadioGroup para a próxima pergunta não vir marcada
                radioGroup.clearCheck();
                RespostaSelecionada = null;

                // 5. Verifica se ainda existem perguntas
                if (IndiceAtualPergunta < TotalPerguntas) {
                    NovasPerguntas(); // Atualiza a tela com os novos textos
                } else {
                    // Fim das perguntas
                    Toast.makeText(Quizz.this, "Quiz finalizado! Você acertou " + Pontuacao + " de " + TotalPerguntas, Toast.LENGTH_LONG).show();
                    finish(); // Fecha o Quiz e volta para a tela principal
                }
            }
        });
    }

    void NovasPerguntas() {

        indiceReal = indicesSorteados.get(IndiceAtualPergunta);

        pergunta.setText(Perguntas.perguntas[indiceReal]);
        op1.setText(Perguntas.opcoes[indiceReal][0]);
        op2.setText(Perguntas.opcoes[indiceReal][1]);
        op3.setText(Perguntas.opcoes[indiceReal][2]);
        op4.setText(Perguntas.opcoes[indiceReal][3]);
    }
}