package com.demo.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClienteTotalCompraDetail {
    private final String nome;
    private final String cpf;
    private final long quantidadeCompras;
    private final BigDecimal valorTotal;

    public ClienteTotalCompraDetail(String nome, String cpf, long quantidadeCompras, BigDecimal valorTotal) {
        this.nome = nome;
        this.cpf = cpf;
        this.quantidadeCompras = quantidadeCompras;
        this.valorTotal = valorTotal;
    }

}