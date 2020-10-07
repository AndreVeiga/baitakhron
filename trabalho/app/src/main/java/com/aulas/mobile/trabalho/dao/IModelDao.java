package com.aulas.mobile.trabalho.dao;

import android.content.ContentValues;

import java.util.List;

/**
 * Interface usada para os Dados afim de garantir que os
 * métodos principais (CRUD) serão implementados
 * @param <M> - Model
 */
public interface IModelDao<M> {
    void save(M m);
    M getById(Long id);
    List<M> getAll();
    void deleteById(Long id);
    void update(M m);
    List<M> getAllByCategories(int c);
    ContentValues getContentValue(M m);
}
