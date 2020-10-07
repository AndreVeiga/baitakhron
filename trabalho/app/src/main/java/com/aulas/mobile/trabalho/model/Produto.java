package com.aulas.mobile.trabalho.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Classe de representação Generica de um produto
 * @author Elton Veiga
 */
@Data
public class Produto implements Serializable {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private byte[] imagem;
    private Double quantidade;
    private Integer bebida;
    private Integer quantidadeSelecionda;

    @Override
    public String toString(){
        return "Produto: " + this.nome + " " + " Preço: " + "R$ " + this.preco + " Quantidade: " + this.quantidade;
    }
}
