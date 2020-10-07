package com.aulas.mobile.trabalho.common.helper;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Switch;

import com.aulas.mobile.trabalho.R;
import com.aulas.mobile.trabalho.model.Produto;

/**
 * Classe de ajuda nos formulários de criação
 *
 * @author Elton Veiga
 */
public class ProdutoHelper {

    private EditText produto_nome;
    private EditText produto_descricao;
    private EditText produto_preco;
    private Switch produto_bebida_selecionado;
    private EditText produto_quantidade;

    private Produto produto;

    public ProdutoHelper (Activity activity) {
        produto_nome = (EditText) activity.findViewById(R.id.produto_nome);
        produto_descricao = (EditText) activity.findViewById(R.id.produto_descricao);
        produto_preco = (EditText) activity.findViewById(R.id.produto_preco);
        produto_bebida_selecionado = (Switch) activity.findViewById(R.id.switch_produto);
        produto_quantidade = (EditText) activity.findViewById(R.id.produto_qtt);
        this.produto = new Produto();
    }

    /**
     * Pega o produto com os dados mais atuais possiveis
     * @return Produto
     */
    public Produto getProduto(){
        this.produto.setNome(produto_nome.getText().toString());
        this.produto.setDescricao(produto_descricao.getText().toString());
        if(!produto_preco.getText().toString().isEmpty())
            this.produto.setPreco(Double.parseDouble(produto_preco.getText().toString()));
        if(!produto_quantidade.getText().toString().isEmpty())
            this.produto.setQuantidade(Double.parseDouble(produto_quantidade.getText().toString()));

        this.produto.setBebida(produto_bebida_selecionado.isChecked() ? 1 : 0);
        this.produto.setId(this.produto.getId());
        return this.produto;
    }

    public void carregaDados(Produto produto) {
        this.produto_nome.setText(produto.getNome());
        this.produto_descricao.setText(produto.getDescricao());
        this.produto_preco.setText(produto.getPreco().toString());
        this.produto_quantidade.setText(produto.getQuantidade().toString());
        this.produto_bebida_selecionado.setChecked(produto.getBebida().equals(1));
        this.produto.setId(produto.getId());
        this.produto = produto;
    }
}
