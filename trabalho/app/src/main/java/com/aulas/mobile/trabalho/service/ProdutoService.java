package com.aulas.mobile.trabalho.service;

import android.app.Activity;

import com.aulas.mobile.trabalho.common.helper.ProdutoHelper;
import com.aulas.mobile.trabalho.common.validator.ProdutoValidator;
import com.aulas.mobile.trabalho.dao.IModelDao;
import com.aulas.mobile.trabalho.dao.ProdutoDao;
import com.aulas.mobile.trabalho.model.Produto;

import java.util.List;

public class ProdutoService {
    private ProdutoValidator produtoValidator = new ProdutoValidator();
    private ProdutoHelper produtoHelper;
    private IModelDao dao;
    private String SAVE_DEFAULT =  "Salvo com Sucesso!";

    public ProdutoService(Activity activity, ProdutoHelper produtoHelper){
        this.produtoHelper = produtoHelper;
        this.dao = new ProdutoDao(activity.getBaseContext());
    }

    public ProdutoService(Activity activity){
        this.dao = new ProdutoDao(activity.getBaseContext());
    }

    private IModelDao getDao(){
        return this.dao;
    }

    public String salvarProduto() {
        Produto produto = this.produtoHelper.getProduto();
        String  result = this.produtoValidator.isCriacaoValida(produto);

        if(result != null)
            return result;

        if(produto.getId() == null)
            getDao().save(produto);
        else
            getDao().update(produto);

        return SAVE_DEFAULT;
    }

    public List<Produto> getProdutos(){ return getDao().getAll(); }

    public String deleteProduto(Produto produto) {
        String resposta = this.produtoValidator.isDeletarValido(produto);
        if(resposta != null)
            return resposta;

        this.getDao().deleteById(produto.getId());
        return "";
    }

    public List<Produto> getListaPorCategoria(int categoria){
        return getDao().getAllByCategories(categoria);
    }
}
