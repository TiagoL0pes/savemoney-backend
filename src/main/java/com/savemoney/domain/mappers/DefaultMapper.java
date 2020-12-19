package com.savemoney.domain.mappers;

import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Mapper
public interface DefaultMapper {

    default BigDecimal setBigDecimalScale(BigDecimal value) {
        return Objects.isNull(value) ? new BigDecimal(0).setScale(5, RoundingMode.HALF_EVEN) :
                value.setScale(5, RoundingMode.HALF_EVEN);
    }
}
