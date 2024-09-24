package com.demo.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClienteRecomendationTipo {
    private final String cpfNome;
    private final String tipoVinho;

    public ClienteRecomendationTipo(String cpfNome, String tipoVinho) {
        this.cpfNome = cpfNome;
        this.tipoVinho = tipoVinho;
    }

}