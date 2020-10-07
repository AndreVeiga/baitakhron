package com.aulas.mobile.trabalho.common.validator;

import com.aulas.mobile.trabalho.model.Produto;

/**
 * Classe validador de criação de um produto.
 *
 * @author Elton Veiga
 */
public class ProdutoValidator implements IValidator<Produto> {

    private String erroDefault = "O atribudo {0} do produto é requirido!";

    @Override
    public String isCriacaoValida(Produto produto) {
        return this.validacaoDefault(produto);
    }

    @Override
    public String isAlteracaoValida(Produto produto) {
        return this.validacaoDefault(produto);
    }

    /**
     * Validação para um produto
     * @return resposta
     */
    private String validacaoDefault(Produto produto){
        if(produto.getNome() == null || produto.getNome().isEmpty())
            return this.erroDefault.replace("{0}", "nome");

        if(produto.getDescricao() == null || produto.getDescricao().isEmpty())
            return this.erroDefault.replace("{0}", "descrição");

        if(produto.getPreco() == null || produto.getPreco().isNaN())
            return this.erroDefault.replace("{0}", "preço");

        if(produto.getBebida() == null)
            return this.erroDefault.replace("{0}", "bebida");

        return null;
    }

    public String isDeletarValido(Produto produto) {
        if(produto.getId() == null)
            return this.erroDefault.replace("{0}", "id");

        return null;
    }
}
