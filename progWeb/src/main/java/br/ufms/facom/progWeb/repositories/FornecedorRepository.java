package br.ufms.facom.progWeb.repositories;

import br.ufms.facom.progWeb.models.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositório JPA para operações de banco de dados do Fornecedor
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
