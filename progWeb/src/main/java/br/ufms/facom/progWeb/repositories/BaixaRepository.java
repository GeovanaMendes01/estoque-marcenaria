package br.ufms.facom.progWeb.repositories;

import br.ufms.facom.progWeb.models.Baixa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaixaRepository extends JpaRepository<Baixa, Long> {
}