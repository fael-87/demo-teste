package com.demo.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClienteCompraProduto {
    private final String nome;
    private final String cpf;
    private final Vinho vinho;
    private final Compra compra;

    public ClienteCompraProduto(String nome, String cpf, Vinho vinho, Compra compra ) {
        this.nome = nome;
        this.cpf = cpf;
        this.vinho = vinho;
        this.compra = compra;
    }

    public BigDecimal getTotal(){
        if (this.compra == null)
            return null;
        return this.vinho.getPreco().multiply(BigDecimal.valueOf(this.compra.getQuantidade()));
    }

}