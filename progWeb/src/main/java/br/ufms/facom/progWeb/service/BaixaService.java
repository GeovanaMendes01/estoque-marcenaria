package br.ufms.facom.progWeb.service;

import br.ufms.facom.progWeb.models.Baixa;
import br.ufms.facom.progWeb.models.Produto;
import br.ufms.facom.progWeb.repositories.BaixaRepository;
import br.ufms.facom.progWeb.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BaixaService {

    private final BaixaRepository repository;
    private final ProdutoRepository produtoRepository;

    public BaixaService(BaixaRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    public void registrarBaixa(Baixa baixa) {
        Produto produto = produtoRepository.findById(baixa.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidade() < baixa.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente");
        }

        produto.setQuantidade(produto.getQuantidade() - baixa.getQuantidade());
        produtoRepository.save(produto);

        repository.save(baixa);
    }

    public List<Baixa> getBaixas() {
        return repository.findAll();
    }

    public List<Baixa> getBaixasPorPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null) {
            return repository.findByDataBetween(inicio, fim);
        } else if (inicio != null) {
            return repository.findByDataGreaterThanEqual(inicio);
        } else if (fim != null) {
            return repository.findByDataLessThanEqual(fim);
        }
        return repository.findAll();
    }

    public Baixa getBaixa(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Baixa não encontrada"));
    }

    public void excluirBaixa(Long id) {
        repository.deleteById(id);
    }
}