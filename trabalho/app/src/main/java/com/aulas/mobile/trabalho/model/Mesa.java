package com.aulas.mobile.trabalho.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Classe de representação de uma mesa,
 * sendo composta por pedidos.
 *
 * @author Elton Veiga
 */
@Data
public class Mesa implements Serializable {
    /**
     * Id representará também o número da mesa.
     */
    private Integer id;
    private List<Pedido> pedidos = new ArrayList<>();
    private boolean ocupada;

    @Override
    public String toString() {
        return " [" + (ocupada ? " Ocupado " : " Livre ") + "]"  + " Mesa " + this.id;
    }
}
