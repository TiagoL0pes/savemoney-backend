package com.savemoney.rest.repositories;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Despesa;
import com.savemoney.domain.models.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    @Query("SELECT p FROM CartaoCredito c " +
            "JOIN c.contaBancaria acc " +
            "JOIN c.itens it " +
            "JOIN it.parcelas p WHERE " +
            "month(p.dataVencimento) = :mes AND year(p.dataVencimento) = :ano AND " +
            "p.statusPagamento = 'PENDENTE' AND acc = :contaBancaria")
    List<Parcela> buscarParcelasParaGerarFatura(@Param("mes") Integer mes,
                                                @Param("ano") Integer ano,
                                                @Param("contaBancaria") ContaBancaria contaBancaria);
}
