package com.aulas.mobile.trabalho.dao;

import com.aulas.mobile.trabalho.model.Mesa;
import com.aulas.mobile.trabalho.model.Pedido;
import com.aulas.mobile.trabalho.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class PedidoDao {

    public static List<Pedido> pedidos = new ArrayList<>();

    public Pedido getPedido(Pedido pedido) {
        for(Pedido ped: pedidos)
            if(pedido.getMesa().getId().equals(ped.getMesa().getId())) return ped;
        return null;
    }

    public void addPedido(Pedido pedido){
        Pedido pedidoRetornado = this.getPedido(pedido);
        if(pedidoRetornado != null)
            for(Produto produto: pedido.getProdutos())
                pedidoRetornado.addProduto(produto);
        else pedidos.add(pedido);
    }

    public boolean mesaOcupada(Mesa mesa) {
        for(Pedido pedido: pedidos)
            if(pedido.getMesa().getId().equals(mesa.getId())) return true;
        return false;
    }

    public Pedido getPedidoPorMesa(Mesa mesa) {
        for(Pedido pedido: pedidos)
            if(pedido.getMesa() != null && pedido.getMesa().getId().equals(mesa.getId())) return pedido;
        return null;
    }

    public void pagamentoMesa(Mesa mesa) {
        Pedido pedidoAserRemovido = null;
        for(Pedido pedido: pedidos)
            if(pedido.getMesa().getId().equals(mesa.getId())){
                pedidoAserRemovido = pedido;
                break;
            }

        if(pedidoAserRemovido != null)
            pedidos.remove(pedidoAserRemovido);
    }
}
