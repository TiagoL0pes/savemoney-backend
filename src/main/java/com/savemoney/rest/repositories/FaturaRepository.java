package com.savemoney.rest.repositories;

import com.savemoney.domain.models.Fatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

    @Query("SELECT f FROM Fatura f WHERE month(f.dataVencimento) = :mes AND year(f.dataVencimento) = :ano")
    Optional<Fatura> buscarPorDataVencimento(@Param("mes") Integer mes,
                                             @Param("ano") Integer ano);

    @Query("SELECT f FROM Fatura f WHERE f.cartaoCredito.idCartaoCredito = :idCartao")
    Page<Fatura> buscarTodosPorCartaoCredito(@Param("idCartao") Long idCartao, Pageable pageable);
}
