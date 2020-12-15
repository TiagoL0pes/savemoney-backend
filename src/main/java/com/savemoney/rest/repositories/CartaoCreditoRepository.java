package com.savemoney.rest.repositories;

import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ContaBancaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {

    Optional<CartaoCredito> findByNumero(String numero);

    Optional<CartaoCredito> findByIdCartaoCreditoAndContaBancaria(Long idCartaoCredito, ContaBancaria contaBancaria);

    Page<CartaoCredito> findAllByContaBancaria(Pageable pageable, ContaBancaria contaBancaria);
}
