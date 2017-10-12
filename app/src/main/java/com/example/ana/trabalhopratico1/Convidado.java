package com.example.ana.trabalhopratico1;

import java.io.Serializable;

/**
 * Created by Ana on 21/09/2017.
 */

public class Convidado implements Serializable{
    private String nome;
    private String email;
    private String numero;

    public Convidado () { }

    public Convidado ( String nome, String email, String numero) {
        this.nome = nome;
        this.email = email;
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
