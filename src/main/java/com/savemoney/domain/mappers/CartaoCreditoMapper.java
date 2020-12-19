package com.savemoney.domain.mappers;

import com.savemoney.domain.models.CartaoCredito;
import com.savemoney.domain.models.ItemCartao;
import com.savemoney.domain.models.Parcela;
import com.savemoney.domain.requests.CartaoCreditoRequest;
import com.savemoney.domain.responses.CartaoCreditoResponse;
import com.savemoney.domain.responses.ItemCartaoResponse;
import com.savemoney.domain.responses.ResumoItemCartaoResponse;
import com.savemoney.utils.helpers.DateHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Mapper(imports = {DateHelper.class})
public interface CartaoCreditoMapper extends DefaultMapper {

    @Mapping(target = "idCartaoCredito", source = "cartaoCredito.idCartaoCredito")
    @Mapping(target = "totalItens", expression = "java(cartaoCredito.getItens().size())")
    @Mapping(target = "valorTotal", expression = "java(valorTotalItensCartao(cartaoCredito.getItens()))")
    @Mapping(target = "itens", source = "cartaoCredito.itens")
    ResumoItemCartaoResponse toResumoItemCartaoResponse(CartaoCredito cartaoCredito);

    ItemCartaoResponse toItemCartaoResponse(ItemCartao itemCartao);

    CartaoCreditoResponse toCartaoCreditoResponse(CartaoCredito cartaoCredito);

    List<CartaoCreditoResponse> toCartoesCreditoResponse(List<CartaoCredito> cartaoCredito);

    CartaoCredito toCartaoCredito(CartaoCreditoRequest request);

    @Named("valorTotalItensCartao")
    default BigDecimal valorTotalItensCartao(List<ItemCartao> itens) {
        return CollectionUtils.isEmpty(itens) ?
                BigDecimal.ZERO :
                itens.stream()
                        .map(ItemCartao::getParcelas)
                        .flatMap(List::stream)
                        .map(Parcela::getValor)
                        .reduce(BigDecimal::add)
                        .get();
    }
}
