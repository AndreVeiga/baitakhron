package com.aulas.mobile.trabalho.service;

import com.aulas.mobile.trabalho.dao.PedidoDao;
import com.aulas.mobile.trabalho.model.Mesa;
import com.aulas.mobile.trabalho.model.Pedido;

import lombok.Data;

@Data
public class PedidoService {
    private PedidoDao pedidoDao;

    public PedidoService(){
        this.pedidoDao = new PedidoDao();
    }

    public void addPedido(Pedido pedido){
        this.pedidoDao.addPedido(pedido);
    }

    public boolean mesaComPedido(Mesa mesa){ return this.pedidoDao.mesaOcupada(mesa); }

    public Pedido getPedido(Mesa mesa) { return this.pedidoDao.getPedidoPorMesa(mesa); }

    public void pagamentoMesa(Mesa mesa){ this.pedidoDao.pagamentoMesa(mesa); }
}
