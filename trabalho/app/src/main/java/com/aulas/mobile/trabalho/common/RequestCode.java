package com.aulas.mobile.trabalho.common;

/**
 * Enum para auxiliar nos request codes do projeto
 *
 * @author Elton Veiga
 */
public enum RequestCode {
    RETORNO_DETALHES_PEDIDO_PRATO(1),
    RETORNO_DETALHES_PEDIDO_BEBIDA(2);

    private Integer valor;

    RequestCode(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }
}
