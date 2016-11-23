package com.grafixartist.noteapp;

import com.orm.SugarRecord;

/**
 * Created by Suleiman19 on 1/21/16.
 */
public class Evento extends SugarRecord {
    String nome;
    String email;
    String telefone;
    String gostou;
    String sugestoes;

    public Evento() {
    }


    public Evento(String nome, String email, String telefone, String gostou, String sugestoes) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.gostou = gostou;
        this.sugestoes = sugestoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getGostou() {
        return gostou;
    }

    public void setGostou(String gostou) {
        this.gostou = gostou;
    }

    public String getSugestoes() {
        return sugestoes;
    }

    public void setSugestoes(String sugestoes) {
        this.sugestoes = sugestoes;
    }
}
