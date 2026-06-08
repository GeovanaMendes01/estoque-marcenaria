package br.ufms.facom.progWeb.repositories;

import br.ufms.facom.progWeb.models.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositório JPA para operações de banco de dados do Fornecedor
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    // Verifica se já existe um fornecedor cadastrado com o CNPJ informado
    boolean existsByCnpj(String cnpj);

    // Verifica se existe outro fornecedor (id diferente) com esse CNPJ - usado na edição
    boolean existsByCnpjAndIdNot(String cnpj, Long id);
}
