package com.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.model.Vinho;

@Service
public class ProdutoService {

    @Value("${external.url.produtos}")
    private String PRODUTOS_URL;

    private final RestTemplate restTemplate;

    private static final Map<Integer, Vinho> catalogo = new HashMap<>();

    @Autowired
    public ProdutoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public Map<Integer, Vinho> catalogo() {
        if (!catalogo.isEmpty())
            return catalogo;

        preencherCatalogo();

        return catalogo;
    }

    private void preencherCatalogo() {
        Vinho[] vinhos = restTemplate.getForObject(PRODUTOS_URL, Vinho[].class);
        if (vinhos == null)
            return;

        for (Vinho vinho : vinhos) 
            catalogo.put(vinho.getCodigo(), vinho);
    }

}
