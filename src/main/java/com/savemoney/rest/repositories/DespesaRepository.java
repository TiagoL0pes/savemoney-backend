package com.savemoney.rest.repositories;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>, JpaSpecificationExecutor<Despesa> {

    Optional<Despesa> findByIdDespesaAndContaBancaria(Long idDespesa, ContaBancaria contaBancaria);
}
