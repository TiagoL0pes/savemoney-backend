package com.savemoney.rest.repositories;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.ItemCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemCartaoRepository extends JpaRepository<ItemCartao, Long> {

    Optional<ItemCartao> findByIdItemCartaoAndContaBancaria(Long idItemCartao, ContaBancaria contaBancaria);
}
