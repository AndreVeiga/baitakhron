package com.aulas.mobile.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.aulas.mobile.trabalho.model.Mesa;
import com.aulas.mobile.trabalho.model.Pedido;
import com.aulas.mobile.trabalho.model.Produto;
import com.aulas.mobile.trabalho.service.PedidoService;

import java.text.DecimalFormat;

/**
 * Classe para tela de pagamento
 * @author Elton Veiga
 */
public class PagamentoActivity extends AppCompatActivity {

    private TextView pagamentoMesa;
    private TextView pagamentoTotalPratos;
    private TextView pagamentoTotalBebidas;
    private TextView pagamentoTotalNota;
    private Switch gorjeta;
    private PedidoService pedidoService;

    private Mesa mesa;
    private Double totalNota = 0D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        this.pedidoService = new PedidoService();
        findViews();
        loadMesa();
        loadData();
    }

    /**
     * Carrega os componentes
     */
    private void findViews(){
        this.pagamentoMesa = findViewById(R.id.pagamento_mesa);
        this.pagamentoTotalPratos = findViewById(R.id.pagamento_total_pratos);
        this.pagamentoTotalBebidas = findViewById(R.id.pagamento_total_bebidas);
        this.pagamentoTotalNota = findViewById(R.id.pagamento_total_nota);
        this.gorjeta = findViewById(R.id.gorjeta);
    }

    /**
     * Carrega a mesa
     */
    private void loadMesa(){
        Intent intent = getIntent();
        this.mesa = (Mesa) intent.getSerializableExtra("mesa");
        if(mesa == null) finish();
    }

    /**
     * Adiciona gorjeta ao valor total
     * @param v
     */
    public void adicionarGorjeta(View v){
        String text = "Valor total da nota: ";
        DecimalFormat df = new DecimalFormat("#,###.00");
        if(this.gorjeta.isChecked()) this.pagamentoTotalNota.setText(text + df.format(this.totalNota * 1.1));
        else this.pagamentoTotalNota.setText(text + df.format(this.totalNota));
    }

    /**
     * Calcula e mostra na tela os valores da nota
     */
    private void loadData() {
        Pedido pedido = this.pedidoService.getPedido(this.mesa);

        String idMesa = this.pagamentoMesa.getText().toString();
        this.pagamentoMesa.setText(idMesa + this.mesa.getId().toString());

        Double precoPratos = 0D;
        Double precoBebidas = 0D;

        for(Produto produto : pedido.getProdutos())
            if(produto.getBebida().equals(0))
                precoPratos += (produto.getPreco() * produto.getQuantidadeSelecionda());
            else
                precoBebidas += (produto.getPreco() * produto.getQuantidadeSelecionda());

        String valorPratos = this.pagamentoTotalPratos.getText().toString();
        this.pagamentoTotalPratos.setText(valorPratos + precoPratos.toString());

        String valorBebidas = this.pagamentoTotalBebidas.getText().toString();
        this.pagamentoTotalBebidas.setText(valorBebidas + precoBebidas.toString());

        this.totalNota = (precoPratos + precoBebidas);
        String notaTotal = this.pagamentoTotalNota.getText().toString();
        this.pagamentoTotalNota.setText(notaTotal + this.totalNota);
    }

    public void finalizarPedido(View v){
        this.pedidoService.pagamentoMesa(this.mesa);
        finish();
    }
}