package com.grv.vo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Vizinho implements Cloneable, Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    @ColumnInfo(index = true)
    private String nome;
    private String endereco;
    private String contato;
    private String observacao;
    private NivelConfianca nivelConfianca;

    public Vizinho(String nome, String endereco, String contato, String observacao, NivelConfianca nivelConfianca) {
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
        this.observacao = observacao;
        this.nivelConfianca = nivelConfianca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public NivelConfianca getNivelConfianca() {
        return nivelConfianca;
    }

    public void setNivelConfianca(NivelConfianca nivelConfianca) {
        this.nivelConfianca = nivelConfianca;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vizinho vizinho = (Vizinho) o;
        return Objects.equals(id, vizinho.id) && Objects.equals(nome, vizinho.nome) && Objects.equals(endereco, vizinho.endereco) && Objects.equals(contato, vizinho.contato) && Objects.equals(observacao, vizinho.observacao) && nivelConfianca == vizinho.nivelConfianca;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, endereco, contato, observacao, nivelConfianca);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return String.format("Nome: %d");
    }
}
