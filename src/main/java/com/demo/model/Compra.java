package com.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;

@Getter
public class Compra {
    @JsonAlias("codigo")
    private int codigoVinho;
    private int quantidade;
}