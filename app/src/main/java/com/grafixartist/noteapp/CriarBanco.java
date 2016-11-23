package com.grafixartist.noteapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Cesar on 21/10/2016.
 */

public class CriarBanco  extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "notes.db";
    public static final String NOME_TABELA = "EVENTO";
    public static final String ID = "ID";
    public static final String NOME = "NOME";
    public static final String TELEFONE = "TELEFONE";
    public static final String EMAIL = "EMAIL";
    public static final String GOSTOU = "data_emprestimo";
    public static final String SUGESTOES = "SUGESTOES";

    public static final int VERSAO = 1;

    public CriarBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE "+ NOME_TABELA + "("
//                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + NOME + " TEXT, "
//                + TELEFONE +" TEXT, "
//                + EMAIL + " TEXT, "
//                + GOSTOU + " TEXT,"
//                + SUGESTOES + " TEXT"
//                +")";
//
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ NOME_TABELA);
        onCreate(db);

        Log.i("LOG: ", "Tabela apagada");
    }
}
