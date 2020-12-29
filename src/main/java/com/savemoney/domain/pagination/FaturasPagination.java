package com.savemoney.domain.pagination;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.savemoney.domain.responses.FaturaResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class FaturasPagination extends PageImpl<FaturaResponse> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public FaturasPagination(@JsonProperty("content") List<FaturaResponse> content,
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

    public FaturasPagination(List<FaturaResponse> content) {
        super(content);
    }

    public FaturasPagination(List<FaturaResponse> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
