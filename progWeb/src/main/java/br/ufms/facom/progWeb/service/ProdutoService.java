package br.ufms.facom.progWeb.service;


import br.ufms.facom.progWeb.models.Produto;
import br.ufms.facom.progWeb.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void salvarProduto(Produto produto){
        repository.save(produto);
    }

    public List<Produto> getProdutos(){
        return repository.findAll();
    }

    public Produto getProduto(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));


    }

    public void excluirProduto(Long id){
        repository.deleteById(id);
    }

    public void atualizarProduto(Long id, Produto dadosNovos){
        Produto produto = repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if(dadosNovos.getNome() != null){
            produto.setNome(dadosNovos.getNome());
        }

        if(dadosNovos.getDescricao() != null){
            produto.setDescricao(dadosNovos.getDescricao());
        }

        if(dadosNovos.getQuantidade() != null){
            produto.setQuantidade(dadosNovos.getQuantidade());
        }

        if(dadosNovos.getPreco() != null){
            produto.setPreco(dadosNovos.getPreco());
        }

        if (dadosNovos.getFornecedor() != null) {
            produto.setFornecedor(dadosNovos.getFornecedor());
        }

        repository.save(produto);

    }
}
