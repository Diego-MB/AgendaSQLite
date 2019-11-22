package br.edu.ifsp.agendasqlite.model;

import java.io.Serializable;

public class Contato implements Serializable {

    private int id;
    private String nome;
    private String fone;
    private String email;
    private boolean favorito;
    private String fone2;

    public Contato() {
    }

    public Contato(String nome, String fone, String email, String fone2) {
        this.nome = nome;
        this.fone = fone;
        this.email = email;
        this.fone2 = fone2;

    }


    public boolean equals(Object obj)
    {
        Contato c2= (Contato) obj;
        if (this.id ==c2.getId())
            return true;
           else
            return false;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getFavorito() { return favorito; }

    public void setFavorito(boolean favorito) { this.favorito = favorito; }

    public String getFone2() { return fone2; }

    public void setFone2(String fone2) { this.fone2 = fone2; }

}
