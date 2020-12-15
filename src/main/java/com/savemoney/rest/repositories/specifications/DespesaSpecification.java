package com.savemoney.rest.repositories.specifications;

import com.savemoney.domain.models.Despesa;
import com.savemoney.rest.filters.FiltroPaginacao;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class DespesaSpecification implements Specification<Despesa> {

    private FiltroPaginacao filtro;

    public DespesaSpecification(FiltroPaginacao filtro) {
        super();
        this.filtro = filtro;
    }

    @Override
    public Predicate toPredicate(Root<Despesa> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate condicoesData = cb.conjunction();
        Predicate condicoesContaBancaria = cb.disjunction();

        if (Objects.nonNull(filtro.getMes()) && Objects.nonNull(filtro.getAno())) {
            condicoesData.getExpressions()
                    .add(cb.equal(cb.function("MONTH", Integer.class, root.get("dataEntrada")),
                            filtro.getMes()));

            condicoesData.getExpressions()
                    .add(cb.equal(cb.function("YEAR", Integer.class, root.get("dataEntrada")),
                            filtro.getAno()));
        }

        condicoesContaBancaria.getExpressions()
                .add(cb.equal(root.get("contaBancaria"), filtro.getContaBancaria()));

        condicoesData.getExpressions().add(condicoesContaBancaria);

        return condicoesData;
    }
}
