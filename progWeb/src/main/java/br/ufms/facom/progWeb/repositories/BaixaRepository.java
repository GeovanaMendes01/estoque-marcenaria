package br.ufms.facom.progWeb.repositories;

import br.ufms.facom.progWeb.models.Baixa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BaixaRepository extends JpaRepository<Baixa, Long> {

    List<Baixa> findByDataBetween(LocalDate inicio, LocalDate fim);

    List<Baixa> findByDataGreaterThanEqual(LocalDate inicio);

    List<Baixa> findByDataLessThanEqual(LocalDate fim);
}