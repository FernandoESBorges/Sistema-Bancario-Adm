/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unincor.sistema.bancario.admin.model.domain;

import java.time.LocalDate;

/**
 *
 * @author Ferna
 */
public class Gerente extends Pessoa {

    private long idGerente;
    private Agencia agencia;

    public Gerente() {
    }

    public Gerente(long idGerente, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senhaHash, Agencia idAgencia) {
        this.idGerente = idGerente;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.senhaHash = senhaHash;
        this.agencia = agencia;
    }

    public long getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(long idGerente) {
        this.idGerente = idGerente;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    
    

}
