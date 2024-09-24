package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;

@Getter
public class CompraCliente {
    private String nome;
    private String cpf;
    private final List<Compra> compras;

    public CompraCliente() {
        this.compras = new ArrayList<>();
    }

    // @Getter
    // public class Compra {
    //     @JsonAlias("codigo")
    //     private int codigoVinho;
    //     private int quantidade;
    // }
}