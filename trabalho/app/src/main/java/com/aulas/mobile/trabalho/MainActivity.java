package com.aulas.mobile.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Classe para tela principal
 * @author Elton Veiga
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Executa o fluxo de cadastro de Cardápio.
     * @param v
     */
    public void gerenciarCardapio(View v) {
        Intent intent = new Intent(this, ListaCardapioActivity.class);
        startActivity(intent);
    }

    /**
     * Executa o fluxo principal do trabalho 1.
     * @param v
     */
    public void gerenciarPedidos(View v){
        Intent intent = new Intent(this, MesaActivity.class);
        startActivity(intent);
    }
}