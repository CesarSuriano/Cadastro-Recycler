package com.grafixartist.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Cesar on 21/10/2016.
 */

public class BancoController {
    private SQLiteDatabase db;
    private CriarBanco banco;

    public BancoController(Context context) {
        banco = new CriarBanco(context);
    }

//    public String insereDado(String nome, String descricao, String situacao, String data_emprestimo, String data_devolucao, String valor) {
//        ContentValues valores;
//        long resultado;
//
//        db = banco.getWritableDatabase();
//
//        valores = new ContentValues();
//
//        valores.put(CriarBanco.PESSOA, nome);
//        valores.put(CriarBanco.DESCRICAO, descricao);
//        valores.put(CriarBanco.SITUACAO, situacao);
//        valores.put(CriarBanco.DATA_EMPRESTIMO, data_emprestimo);
//        valores.put(CriarBanco.DATA_DEVOLUCAO, data_devolucao);
//        valores.put(CriarBanco.VALOR, valor);
//
//        resultado = db.insert(CriarBanco.NOME_TABELA, null, valores);
//
//        Log.i("LOG: ", valores.toString());
//        db.close();
//
//
//        if (resultado == -1) {
//            return "Erro de inserção";
//        } else {
//            return "Registro inserido com sucesso";
//        }
//    }

//    public Cursor carregaDados(){
//        Cursor cursor;
//        String[] campos = {banco.ID, banco.PESSOA};
//        db = banco.getReadableDatabase();
//        cursor = db.query(banco.NOME_TABELA, campos, null, null, null, null, null);
//        if(cursor != null){
//            cursor.moveToFirst();
//        }
//        db.close();
//        return  cursor;
//    }
//
//    public Cursor carregaDadosPendentes(){
//        Cursor cursor;
//        String[] campos = {banco.ID, banco.PESSOA};
//        String where = CriarBanco.SITUACAO + "=" + "'PENDENTE'";
//        db = banco.getReadableDatabase();
//        cursor = db.query(banco.NOME_TABELA, campos, where, null, null, null, null, null);
//        if(cursor != null){
//            cursor.moveToFirst();
//        }
//        db.close();
//        return  cursor;
//    }
//
//    public Cursor carregaDadosById(int id){
//        Cursor cursor;
//        String[] campos = {banco.ID, banco.PESSOA, banco.DESCRICAO, banco.SITUACAO, banco.DATA_EMPRESTIMO, banco.DATA_DEVOLUCAO};
//        String where = CriarBanco.ID + "=" + id;
//        db = banco.getReadableDatabase();
//        cursor = db.query(banco.NOME_TABELA, campos, where, null, null, null, null, null);
//        if(cursor != null){
//            cursor.moveToFirst();
//        }
//        db.close();
//        return  cursor;
//    }
//
//    public void alteraRegistro(int id, String nome, String descricao, String situacao, String data_emprestimo, String data_devolucao, String valor) {
//        ContentValues valores;
//
//        db = banco.getWritableDatabase();
//        String where;
//        where = CriarBanco.ID + "=" + id;
//
//        valores = new ContentValues();
//
//        valores.put(CriarBanco.PESSOA, nome);
//        valores.put(CriarBanco.DESCRICAO, descricao);
//        valores.put(CriarBanco.SITUACAO, situacao);
//        valores.put(CriarBanco.DATA_EMPRESTIMO, data_emprestimo);
//        valores.put(CriarBanco.DATA_DEVOLUCAO, data_devolucao);
//        valores.put(CriarBanco.VALOR, valor);
//
//        db.update(CriarBanco.NOME_TABELA, valores, where, null);
//        db.close();
//    }
//
//    public void excuirRegistro(int id){
//        String where = CriarBanco.ID + "="+id;
//        db = banco.getReadableDatabase();
//        db.delete(CriarBanco.NOME_TABELA, where, null);
//        db.close();
//    }

    public void deletaTudo(){
        db = banco.getWritableDatabase();
        db.delete(CriarBanco.NOME_TABELA, null,null);
        String selectQuery = "DROP TABLE "+ CriarBanco.NOME_TABELA;
        Cursor cursor = banco.getReadableDatabase().rawQuery(selectQuery, null);
    }
}
