package com.aulas.mobile.trabalho;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aulas.mobile.trabalho.common.RequestCode;
import com.aulas.mobile.trabalho.model.Mesa;
import com.aulas.mobile.trabalho.model.Pedido;
import com.aulas.mobile.trabalho.model.Produto;
import com.aulas.mobile.trabalho.service.PedidoService;

/**
 * Classe que mostrar os detalhes de um pedido em uma mesa
 * @author Elton Veiga
 */
public class DetalhesPedido extends AppCompatActivity {

    private Mesa mesa;
    private PedidoService pedidoService;
    private final String MESA = "mesa";
    private Pedido pedido;

    private LinearLayout detalhesPedidoBody;
    private LinearLayout detalhesPedidoBodyEmpty;
    private TextView txt_title;
    private TextView valorTotalPedido;
    private ListView listaProdutosDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pedido);

        this.pedidoService = new PedidoService();

        findViews();
        loadTitle();
        loadPedido();
    }

    /**
     * Carrega mesa conforme o número dela
     */
    private void loadTitle(){
        Intent intent = getIntent();
        this.mesa = (Mesa) intent.getSerializableExtra(MESA);
        String newTitle = txt_title.getText().toString().replace("{0}", this.mesa.getId().toString());
        txt_title.setText(newTitle);
    }

    /**
     * Carrega os componentes
     */
    private void findViews(){
        this.txt_title = findViewById(R.id.txt_title);
        this.detalhesPedidoBodyEmpty = findViewById(R.id.detalhes_pedido_body_empty);
        this.detalhesPedidoBody = findViewById(R.id.detalhes_pedido_body);
        this.listaProdutosDetalhes = findViewById(R.id.lista_produtos_detalhes);
        this.valorTotalPedido = findViewById(R.id.lbl_valorTotal);
    }

    /**
     * Busca um pedido no DB
     */
    private void loadPedido() {
        if(this.mesa == null || this.pedidoService == null) return;
        this.pedido = this.pedidoService.getPedido(this.mesa);
    }

    /**
     * Abrir a lista de pratos.
     * @param v - ignore
     */
    public void abrirListaDePratos(View v){
        startActivityForResult(intentFromListaDeProdutos(0), RequestCode.RETORNO_DETALHES_PEDIDO_PRATO.getValor());
    }

    /**
     * Abrir a lista de bebidas.
     * @param v - ignore
     */
    public void abrirListaDeBebidas(View v){
        startActivityForResult(intentFromListaDeProdutos(1), RequestCode.RETORNO_DETALHES_PEDIDO_BEBIDA.getValor());
    }

    /**
     * Cria a intent para a página de listagem do produto.
     * @param tipo  0 - pratos / 1 - bebidas
     * @return
     */
    private Intent intentFromListaDeProdutos(Integer tipo){
        Intent intent = new Intent(this, PedidoProduto.class);
        intent.putExtra(MESA, this.mesa);
        intent.putExtra("tipo", tipo);
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        loadPedido();
        if(this.pedido == null) carregaPedidoVazio();
        else carregaPedido();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void carregaPedido(){
        this.detalhesPedidoBodyEmpty.setVisibility(View.GONE);
        this.detalhesPedidoBody.setVisibility(View.VISIBLE);
        double total = this.pedido.getProdutos().stream()
                .reduce(0D, (valor, produto) -> produto.getPreco() * produto.getQuantidadeSelecionda() + valor, Double::sum)
                .doubleValue();
        this.valorTotalPedido.setText("" + total);

        carregaListViewComProdutos();
    }

    private void carregaListViewComProdutos() {
        ArrayAdapter<Produto> produtoAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_expandable_list_item_1, this.pedido.getProdutos()) {
            @Override
            public View getView(int position, View viewOk, ViewGroup parent) {
                View view = getLayoutInflater().inflate(R.layout.lista_item_pedido, null);
                Produto produto = pedido.getProdutos().get(position);
                ((TextView)view.findViewById(R.id.produto_detalhe_nome)).setText(produto.getNome());
                ((TextView)view.findViewById(R.id.produto_detalhe_quantidade)).setText(produto.getQuantidadeSelecionda().toString());
                ((TextView)view.findViewById(R.id.produto_detalhe_valor)).setText(produto.getPreco().toString());
                return view;
            }
        };
        this.listaProdutosDetalhes.setAdapter(produtoAdapter);
    }

    private void carregaPedidoVazio(){
        this.detalhesPedidoBodyEmpty.setVisibility(View.VISIBLE);
        this.detalhesPedidoBody.setVisibility(View.GONE);
    }
}