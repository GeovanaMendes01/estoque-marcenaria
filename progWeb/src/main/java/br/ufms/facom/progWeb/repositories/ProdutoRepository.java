package br.ufms.facom.progWeb.repositories;

import br.ufms.facom.progWeb.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
