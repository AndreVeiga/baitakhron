package com.aulas.mobile.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aulas.mobile.trabalho.model.Mesa;
import com.aulas.mobile.trabalho.model.Produto;
import com.aulas.mobile.trabalho.service.PedidoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe para gerenciar as mesas do aplicativo
 * @author Elton Veiga
 */
public class MesaActivity extends AppCompatActivity {

    private ListView listaMesas;

    private List<Mesa> mesas;
    private PedidoService pedidoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa);

        this.listaMesas = (ListView) findViewById(R.id.mesa_list);
        this.pedidoService = new PedidoService();

        carregaMesas();
        carregaListaMesa();
        adicionarListener();

        registerForContextMenu(listaMesas);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Mesa mesa = (Mesa) listaMesas.getItemAtPosition(info.position);
        openPagamento(menu, mesa);
    }

    private void openPagamento(ContextMenu menu, Mesa mesa) {
        MenuItem deletar = menu.add("Pagamento");
        deletar.setOnMenuItemClickListener((menuItem) -> {
            if(mesa.isOcupada()) {
                Intent intent = new Intent(this, PagamentoActivity.class);
                intent.putExtra("mesa", mesa);
                startActivity(intent);
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.titulo_mesa_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Carrega o listener para as mesas.
     */
    private void adicionarListener() {
        this.listaMesas.setOnItemClickListener((list, item, position, id) -> {
            Mesa mesa = (Mesa) listaMesas.getItemAtPosition(position);
            Intent intent = new Intent(MesaActivity.this, DetalhesPedido.class);
            intent.putExtra("mesa", mesa);
            startActivity(intent);
        });
    }

    /**
     * Cria a lista e carrega a numeração das mesas.
     */
    private void carregaMesas() {
        this.mesas = new ArrayList<>();
        for(int index = 1; index <= 15; index++) {
            Mesa mesa = new Mesa();
            mesa.setId(index);
            mesa.setOcupada(this.pedidoService.mesaComPedido(mesa));
            this.mesas.add(mesa);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaMesas();
        carregaListaMesa();
    }

    /**
     * Carrega a lista de mesas com auxilio de um adapter
     */
    private void carregaListaMesa() {
        if(listaMesas != null && this.mesas != null && !this.mesas.isEmpty()) {
            ArrayAdapter<Mesa> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, this.mesas);
            listaMesas.setAdapter(stringArrayAdapter);
        }
    }
}