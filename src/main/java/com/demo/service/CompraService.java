package com.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.model.ClienteCompraProduto;
import com.demo.model.Compra;
import com.demo.model.CompraCliente;
import com.demo.model.Vinho;


@Service
public class CompraService {
    
    @Value("${external.url.compras}")
    private String COMPRAS_URL;

    private final ProdutoService produtoService;

    private final RestTemplate restTemplate;

    @Autowired
    public CompraService(ProdutoService produtoService,
                         RestTemplate restTemplate) {
        this.produtoService = produtoService;
        this.restTemplate = restTemplate;
    }

    /**
     * Retorna uma lista de compras do cliente, ordenada pelo valor total da compra.
     *
     * @return Uma lista de objetos `ClienteCompraProduto` ordenada pelo valor total da compra em ordem crescente.
     */
    public List<ClienteCompraProduto> listarComprasOrdenado() {
        List<ClienteCompraProduto> comprasCliente = getComprasCliente();
        Collections.sort(comprasCliente, Comparator.comparing(ClienteCompraProduto::getTotal));
        return comprasCliente;
    }

    protected List<ClienteCompraProduto> getComprasCliente() {
        List<ClienteCompraProduto> comprasCliente = new ArrayList<>();
        for (CompraCliente compraCliente : comprasClientes()) {
            for (Compra compra : compraCliente.getCompras()) {
                Vinho vinho = produtoService.catalogo().get(compra.getCodigoVinho());
                ClienteCompraProduto item = ClienteCompraProduto.builder()
                    .cpf(compraCliente.getCpf())
                    .nome(compraCliente.getNome())
                    .vinho(vinho)
                    .compra(compra)
                    .build();
                comprasCliente.add(item);
            }
        }
        return comprasCliente;
    }

    private CompraCliente[] comprasClientes() {
        CompraCliente[] compras = restTemplate.getForObject(COMPRAS_URL, CompraCliente[].class);
        if (compras == null)
            return new CompraCliente[0];
        return compras;
    }

    /**
     * Retorna a maior compra realizada por um cliente em um determinado ano.
     *
     * @param ano O ano da compra.
     * @return O objeto `ClienteCompraProduto` representando a maior compra do ano, ou `null` se não houver compras para o ano especificado.
     */
    public ClienteCompraProduto getMaiorCompraByAno(int ano) {
        return getComprasCliente().stream()
                .filter(e -> e.getVinho().getAno() == ano)
                .sorted(Comparator.comparing(ClienteCompraProduto::getTotal).reversed())
                .findFirst().get();
    }

}
