package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.ClienteCompraProduto;
import com.demo.model.Vinho;
import com.demo.service.CompraService;
import com.demo.service.ProdutoService;

@RestController
@RequestMapping("/")
public class CompraController {
    
    private final ProdutoService produtoService;
    private final CompraService compraService;

    @Autowired
    public CompraController(ProdutoService produtoService, 
                            CompraService compraService) {
        this.produtoService = produtoService;
        this.compraService = compraService;
    }

    @GetMapping
    public Map<Integer, Vinho> getCatalogo() {
        return produtoService.catalogo();
    }

    @GetMapping("compras")
    public List<ClienteCompraProduto> getAllCompras() {
        return compraService.listarComprasOrdenado();
    }
    
    @GetMapping("/maior-compra/{ano}")
    public ClienteCompraProduto getMaiorCompraByAno(@PathVariable int ano) {
        return compraService.getMaiorCompraByAno(ano);
    }

}