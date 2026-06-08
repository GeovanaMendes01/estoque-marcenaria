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
        // Evita salvar dois fornecedores com o mesmo CNPJ (a coluna é unique no banco)
        if (repository.existsByCnpj(fornecedor.getCnpj())) {
            throw new IllegalArgumentException("Já existe um fornecedor cadastrado com esse CNPJ");
        }
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

        // Evita alterar para um CNPJ que já pertence a outro fornecedor
        if (dadosNovos.getCnpj() != null && repository.existsByCnpjAndIdNot(dadosNovos.getCnpj(), id)) {
            throw new IllegalArgumentException("Já existe um fornecedor cadastrado com esse CNPJ");
        }

        if (dadosNovos.getNome() != null) fornecedor.setNome(dadosNovos.getNome());
        if (dadosNovos.getCnpj() != null) fornecedor.setCnpj(dadosNovos.getCnpj());
        if (dadosNovos.getTelefone() != null) fornecedor.setTelefone(dadosNovos.getTelefone());
        if (dadosNovos.getEmail() != null) fornecedor.setEmail(dadosNovos.getEmail());

        repository.save(fornecedor); 
    }
}