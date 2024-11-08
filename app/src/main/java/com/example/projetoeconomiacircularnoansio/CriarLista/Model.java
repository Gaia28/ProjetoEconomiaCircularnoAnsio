package com.example.projetoeconomiacircularnoansio.CriarLista;

public class Model {
    String id, Nome, Email, Saldo;


    public Model(String id, String nome, String email, String saldo) {
        this.id = id;
        Nome = nome;
        Email = email;
        Saldo = saldo;
    }
    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String saldo) {
        Saldo = saldo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
