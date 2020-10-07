package com.aulas.mobile.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aulas.mobile.trabalho.model.Mesa;
import com.aulas.mobile.trabalho.model.Pedido;
import com.aulas.mobile.trabalho.model.Produto;
import com.aulas.mobile.trabalho.service.PedidoService;
import com.aulas.mobile.trabalho.service.ProdutoService;

import java.util.List;

public class PedidoProduto extends AppCompatActivity {

    /**
     * Componentes para classe
     */
    private ListView listaProdutos;
    private TextView valorTotal;
    private Button btnAdicionar;

    private boolean isPrato;
    private ProdutoService produtoService;
    private Mesa mesa;
    private List<Produto> listaProdutosDoBanco;
    private PedidoService pedidoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_produto);

        this.pedidoService = new PedidoService();
        this.produtoService = new ProdutoService(this);

        Intent intent = getIntent();
        int tipo = intent.getIntExtra("tipo", 0);

        this.mesa = (Mesa) intent.getSerializableExtra("mesa");

        carregaTitulo(tipo);
        findViewByIdActivity();
        loadDados(tipo);
        addListener();
    }

    private void addListener() {
        this.btnAdicionar.setOnClickListener((v) -> {
            Pedido pedido = new Pedido();
            for(Produto produto: listaProdutosDoBanco){
                if(produto.getQuantidadeSelecionda() != null && produto.getQuantidadeSelecionda() > 0){
                    pedido.setMesa(this.mesa);
                    pedido.addProduto(produto);
                }
            }

            if(!pedido.getProdutos().isEmpty())
                pedidoService.addPedido(pedido);
            finish();
        });
    }

    /**
     * Carrega os componentes
     */
    private void findViewByIdActivity() {
        this.listaProdutos = findViewById(R.id.lista_produtos);
        this.valorTotal = findViewById(R.id.valor_total);
        this.btnAdicionar = findViewById(R.id.btn_adicionar);
    }

    /**
     * Adiciona o valor no rodapé
     * @param valor
     */
    private void addValorTotal(Double valor){
        Double valorTotal = Double.valueOf(this.valorTotal.getText().toString());
        valorTotal += valor;
        this.valorTotal.setText(valorTotal.toString());
    }


    /**
     * Carrega título no top da activity
     * @param valor - 0 Pratos / 1 - Bebidas
     */
    private void carregaTitulo(int valor){
        String titulo = "";
        TextView titulo_t = (TextView) findViewById(R.id.title_pedido_produto);
        titulo = titulo_t.getText().toString();
        if(valor == 0) titulo_t.setText(titulo.replace("{0}", "pratos"));
        else titulo_t.setText(titulo.replace("{0}", "bebidas"));
    }

    /**
     * Carrega produtos na lista
     * @param categoria - Para carregar os produtos sendo 0 - pratos e 1 - bebidas
     */
    private void loadDados(int categoria) {
        if(this.listaProdutos == null) return;
        this.listaProdutosDoBanco = this.produtoService.getListaPorCategoria(categoria);
        ArrayAdapter<Produto> produtoAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_expandable_list_item_1, this.listaProdutosDoBanco) {
            @Override
            public View getView(int position, View viewOk, ViewGroup parent) {
                View view = getLayoutInflater().inflate(R.layout.lista_item_produto, null);
                Produto produto = listaProdutosDoBanco.get(position);
                ((TextView)view.findViewById(R.id.nome_produto)).setText(produto.getNome());
                TextView text = view.findViewById(R.id.quantidade_produto);

                view.findViewById(R.id.quantidade_menos).setOnClickListener((v) -> {
                    Integer quantidade = Integer.valueOf(text.getText().toString());
                    if(quantidade >  0) {
                        text.setText((--quantidade).toString());
                        addValorTotal(-produto.getPreco());
                        produto.setQuantidadeSelecionda(quantidade);
                    }
                });

                view.findViewById(R.id.quantidade_mais).setOnClickListener((v) -> {
                    Integer quantidade = Integer.valueOf(text.getText().toString());
                    text.setText((++quantidade).toString());
                    addValorTotal(produto.getPreco());
                    produto.setQuantidadeSelecionda(quantidade);
                });

                return view;
            }
        };
        this.listaProdutos.setAdapter(produtoAdapter);
    }
}