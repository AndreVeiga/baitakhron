package com.aulas.mobile.trabalho.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Pedido implements Serializable {
    private Mesa mesa;
    private List<Produto> produtos = new ArrayList<>();

    public void addProduto(Produto produto){
        Produto produtoExistente = null;
        for(Produto p: this.produtos)
            if(p.getId().equals(produto.getId()))
                produtoExistente = p;

        if(produtoExistente != null) produtoExistente.setQuantidadeSelecionda(produtoExistente.getQuantidadeSelecionda() + produto.getQuantidadeSelecionda());
        else this.produtos.add(produto);

        this.mesa.setOcupada(true);
    }
}
