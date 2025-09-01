package com.example;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private Float saldo;

    public Usuario(String nome, String cpf, Float saldo) {
        this.nome = nome;
        this.cpf = cpf;
        this.saldo = saldo;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Float getSaldo(){
        return saldo;
    }

    public void setSaldo(Float saldo){
        this.saldo = saldo;
    }

    public void depositar(Float depositar){
        this.saldo += depositar;
    }

    public void sacar(Float sacar) {
        if (sacar <= this.saldo) {
            this.saldo -= sacar;
            System.out.println("Saque de R$" + sacar + " realizado com sucesso.");
        } else {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
    }
}