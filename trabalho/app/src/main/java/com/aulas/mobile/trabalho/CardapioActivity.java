package com.aulas.mobile.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aulas.mobile.trabalho.common.helper.ProdutoHelper;
import com.aulas.mobile.trabalho.model.Produto;
import com.aulas.mobile.trabalho.service.ProdutoService;

/**
 * Classe para formulário do cardápio
 * @author Elton Veiga
 */
public class CardapioActivity extends AppCompatActivity {

    private ProdutoService produtoService;
    private ProdutoHelper produtoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);
        this.produtoHelper = new ProdutoHelper(this);
        this.produtoService = new ProdutoService(this, this.produtoHelper);
        loadIntent();
    }

    private void loadIntent() {
        Intent intent = getIntent();
        Produto produto = (Produto) intent.getSerializableExtra("produto");
        if(produto != null){
            produtoHelper.carregaDados(produto);
        }
    }

    public void salvarProduto(View view){
        String result = this.produtoService.salvarProduto();
        Toast.makeText(this, result, Toast.LENGTH_LONG);
        finish();
    }
}