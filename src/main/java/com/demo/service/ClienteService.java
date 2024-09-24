package com.demo.service;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.ClienteRecomendationTipo;
import com.demo.model.ClienteTotalCompraDetail;


@Service
public class ClienteService {
    
    private final CompraService compraService;

    @Autowired
    public ClienteService(CompraService compraService) {
        this.compraService = compraService;
    }

    /**
     * Retorna uma lista dos melhores clientes com base no número total de compras e valor total gasto.
     *
     * @param limite O número máximo de clientes a retornar.
     * @return Uma lista de objetos `ClienteTotalCompraDetail` contendo os dados dos melhores clientes.
     * @throws IllegalArgumentException Se o parâmetro `limite` for menor que 0(ZERO).
     */
    public List<ClienteTotalCompraDetail> melhoresClientes(int limite) {
        if (limite <= 0)
            throw new IllegalArgumentException("limite deve ser maior que 0(ZERO)");

        Map<AbstractMap.SimpleEntry<String, String>, DoubleSummaryStatistics> collect = compraService.getComprasCliente()
            .stream()
                .collect(Collectors.groupingBy(
                        e -> new AbstractMap.SimpleEntry<>(e.getCpf(), e.getNome()),
                        Collectors.summarizingDouble(f -> f.getTotal().doubleValue())
                ));
        List<ClienteTotalCompraDetail> listClientes = ordernarPorCountETotal(collect);
        return listClientes.subList(0, limite);
    }

    private List<ClienteTotalCompraDetail> ordernarPorCountETotal(Map<AbstractMap.SimpleEntry<String, String>, DoubleSummaryStatistics> mapGrouped) {
        List<ClienteTotalCompraDetail> clientes = new LinkedList<>();
        for (Entry<SimpleEntry<String, String>, DoubleSummaryStatistics> cliente : mapGrouped.entrySet()) {
            DoubleSummaryStatistics clienteTotaisDetail = cliente.getValue();
            clientes.add(ClienteTotalCompraDetail.builder()
                                .cpf(cliente.getKey().getKey())
                                .nome(cliente.getKey().getValue())
                                .quantidadeCompras(clienteTotaisDetail.getCount())
                                .valorTotal(BigDecimal.valueOf(clienteTotaisDetail.getSum())).build());
        }
        Collections.sort(clientes, Comparator.comparing(ClienteTotalCompraDetail::getValorTotal).thenComparingLong(ClienteTotalCompraDetail::getQuantidadeCompras).reversed());
        return clientes;
    }

    public List<ClienteRecomendationTipo> tipoVinhoPorCliente() {
        Map<String, Map<String,Long>> map = 
            compraService.listarComprasOrdenado()
                .stream()
                    .collect(Collectors.groupingBy(c -> c.getCpf().concat(" - ").concat(c.getNome()),
                        Collectors.groupingBy(c -> c.getVinho().getTipo(), Collectors.counting()))
                );
        List<ClienteRecomendationTipo> listRecomendacoes = new ArrayList<>();
        for (Entry<String, Map<String,Long>> elem : map.entrySet()) {
            Map.Entry<String, Long> maxEntry = elem.getValue().entrySet().stream()
                .max(Map.Entry.comparingByValue()).get();
            listRecomendacoes.add(ClienteRecomendationTipo.builder()
                .cpfNome(elem.getKey())
                .tipoVinho(maxEntry.getKey())
                .build()
            );
        }
        return listRecomendacoes;
    }

}
