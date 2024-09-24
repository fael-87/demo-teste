package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.ClienteRecomendationTipo;
import com.demo.model.ClienteTotalCompraDetail;
import com.demo.service.ClienteService;

@RestController
@RequestMapping("/")
public class ClienteController {
    
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("clientes-fieis")
    public List<ClienteTotalCompraDetail> get3MelhoresClientes() {
        return clienteService.melhoresClientes(3);
    }
    
    @GetMapping("recomendacao/cliente/tipo")
    public List<ClienteRecomendationTipo> getMaiorCompraByAno() {
        return clienteService.tipoVinhoPorCliente();
    }

}