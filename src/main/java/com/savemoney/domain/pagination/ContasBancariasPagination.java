package com.savemoney.domain.pagination;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.responses.ContaBancariaResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ContasBancariasPagination extends PageImpl<ContaBancariaResponse> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ContasBancariasPagination(@JsonProperty("content") List<ContaBancariaResponse> content,
                                     @JsonProperty("number") int number,
                                     @JsonProperty("size") int size,
                                     @JsonProperty("totalElements") Long totalElements,
                                     @JsonProperty("pageable") JsonNode pageable,
                                     @JsonProperty("last") boolean last,
                                     @JsonProperty("totalPages") int totalPages,
                                     @JsonProperty("sort") JsonNode sort,
                                     @JsonProperty("first") boolean first,
                                     @JsonProperty("numberOfElements") int numberOfElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public ContasBancariasPagination(List<ContaBancariaResponse> content) {
        super(content);
    }

    public ContasBancariasPagination(List<ContaBancariaResponse> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
