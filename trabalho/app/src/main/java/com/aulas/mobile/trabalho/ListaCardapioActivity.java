package com.aulas.mobile.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aulas.mobile.trabalho.common.helper.ProdutoHelper;
import com.aulas.mobile.trabalho.model.Produto;
import com.aulas.mobile.trabalho.service.ProdutoService;

import java.util.List;

public class ListaCardapioActivity extends AppCompatActivity {

    private ProdutoService produtoService;
    private ListView listaProdutos;
    private ProdutoHelper produtoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cardapio);

        this.produtoHelper = new ProdutoHelper(this);
        this.listaProdutos = findViewById(R.id.lista_cardapio);
        this.produtoService = new ProdutoService(this, produtoHelper);

        carregaListaProdutos();
        registerForContextMenu(listaProdutos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaProdutos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.titulo_cardapio_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Carrega a lista de produtos e listener
     */
    private void carregaListaProdutos(){
        List<Produto> produtos = this.produtoService.getProdutos();
        ArrayAdapter<Produto> stringArrayAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_expandable_list_item_1, produtos){
            @Override
            public View getView(int position, View viewOk, ViewGroup parent) {
                Produto produto = produtos.get(position);
                View view = getLayoutInflater().inflate(R.layout.detalhe_cardapio, null);
                ImageView image = view.findViewById(R.id.image_detalhes_cardapio);
                if(produto != null && produto.getBebida() != null && produto.getBebida().equals(1)){
                    image.setImageResource(R.drawable.drink_icon);
                } else {
                    image.setImageResource(R.drawable.food_icon);
                }

                TextView nomeProduto = view.findViewById(R.id.cardapio_nome);
                nomeProduto.setText(produto.getNome());
                return view;
            }
        };
        listaProdutos.setAdapter(stringArrayAdapter);
        setListenerEmListaProdutos();
    }

    /**
     * Carrega o evento de click na lista
     */
    private void setListenerEmListaProdutos() {
        listaProdutos.setOnItemClickListener((list, item, position, id) -> {
            Produto produto = (Produto) listaProdutos.getItemAtPosition(position);
            Intent intent = new Intent(ListaCardapioActivity.this, CardapioActivity.class);
            intent.putExtra("produto", produto);
            startActivity(intent);
        });
    }

    public void abrirCardapioActivity(View v){
        Intent intent = new Intent(this, CardapioActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Produto produto = (Produto) listaProdutos.getItemAtPosition(info.position);
        openDeletar(menu, produto);
    }

    private void openDeletar(ContextMenu menu, Produto produto){
        MenuItem deletar = menu.add("Deletar produto");
        deletar.setOnMenuItemClickListener((menuItem) -> {
            produtoService.deleteProduto(produto);
            carregaListaProdutos();
            return false;
        });
    }
}