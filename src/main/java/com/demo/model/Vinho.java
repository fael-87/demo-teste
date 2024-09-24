package com.demo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Vinho {

    private Integer codigo;

    private String safra;

    @JsonAlias("tipo_vinho")
    private String tipo;

    @JsonAlias("ano_compra")
    private int ano;

    private BigDecimal preco;

}