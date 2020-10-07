package com.aulas.mobile.trabalho.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.aulas.mobile.trabalho.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de representação de um produto
 * @author Elton Veiga
 */
public class ProdutoDao extends SQLiteOpenHelper implements IModelDao<Produto> {

    private String TABLE = "produto";
    private final String ID = "id";
    private final String NOME = "nome";
    private final String DESCRICAO = "descricao";
    private final String PRECO = "preco";
    private final String IMAGEM = "imagem";
    private final String QUANTIDADE = "quantidade";
    private final String BEBIDA = "bebida";

    public ProdutoDao(@Nullable Context context) {
        super(context, "produto", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql =  new StringBuilder();
        sql.append("CREATE TABLE " + TABLE + " ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NOME + " TEXT NOT NULL, ");
        sql.append(DESCRICAO + " TEXT, ");
        sql.append(PRECO + " REAL NOT NULL, ");
        sql.append(IMAGEM + " BLOB, ");
        sql.append(QUANTIDADE + " REAL, ");
        sql.append(BEBIDA  + " INTEGER NOT NULL ");
        sql.append(" ); " );
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS produto";
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public void save(Produto produto) {
        getWritableDatabase().insert(TABLE, null, getContentValue(produto));
    }

    @Override
    public Produto getById(Long id) {
        return null;
    }

    @Override
    public List<Produto> getAll() {
        String sql = "SELECT * FROM " + TABLE + "; ";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Produto> lista = new ArrayList<>();

        while(cursor.moveToNext())
            lista.add(convertCursorEmProduto(cursor));

        cursor.close();
        return lista;
    }

    @Override
    public void deleteById(Long id) {
        String [] params = { id.toString() };
        getWritableDatabase().delete(TABLE, "id = ?", params);
    }

    @Override
    public void update(Produto produto) {
        String[] params = {produto.getId().toString()};
        getWritableDatabase().update(TABLE, getContentValue(produto), "id = ?", params);
    }

    @Override
    public List<Produto> getAllByCategories(int c) {
        String sql = new StringBuilder("SELECT * FROM ").append(TABLE).append(" WHERE " + BEBIDA + " = " + c).toString();
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        List<Produto> lista = new ArrayList<>();
        while(cursor.moveToNext())
            lista.add(convertCursorEmProduto(cursor));

        cursor.close();
        return lista;
    }

    @Override
    public ContentValues getContentValue(Produto produto) {
        ContentValues dados = new ContentValues();
        dados.put(NOME, produto.getNome());
        dados.put(DESCRICAO, produto.getDescricao());
        dados.put(PRECO, produto.getPreco());
        dados.put(IMAGEM, produto.getImagem());
        dados.put(QUANTIDADE, produto.getQuantidade());
        dados.put(BEBIDA, produto.getBebida());
        return dados;
    }

    /**
     * Converte cursor em produto
     * @param cursor
     * @return
     */
    public Produto convertCursorEmProduto(Cursor cursor){
        Produto produto = new Produto();
        produto.setId(cursor.getLong(cursor.getColumnIndex(ID)));
        produto.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
        produto.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
        produto.setPreco(cursor.getDouble(cursor.getColumnIndex(PRECO)));
        produto.setBebida(cursor.getInt(cursor.getColumnIndex(BEBIDA)));
        produto.setImagem(cursor.getBlob(cursor.getColumnIndex(IMAGEM)));
        produto.setQuantidade(cursor.getDouble(cursor.getColumnIndex(QUANTIDADE)));
        return produto;
    }
}
