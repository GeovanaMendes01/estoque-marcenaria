package br.ufms.facom.progWeb.service;

import br.ufms.facom.progWeb.models.Fornecedor;
import br.ufms.facom.progWeb.repositories.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public void salvarFornecedor(Fornecedor fornecedor) {
        repository.save(fornecedor);
    }

    public List<Fornecedor> getFornecedores() {
        return repository.findAll();
    }

    public Fornecedor getFornecedor(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
    }

    public void excluirFornecedor(Long id) {
        repository.deleteById(id);
    }

    public void atualizarFornecedor(Long id, Fornecedor dadosNovos) {
        Fornecedor fornecedor = repository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

        if (dadosNovos.getNome() != null) fornecedor.setNome(dadosNovos.getNome());
        if (dadosNovos.getCnpj() != null) fornecedor.setCnpj(dadosNovos.getCnpj());
        if (dadosNovos.getTelefone() != null) fornecedor.setTelefone(dadosNovos.getTelefone());
        if (dadosNovos.getEmail() != null) fornecedor.setEmail(dadosNovos.getEmail());

        repository.save(fornecedor); 
    }
}