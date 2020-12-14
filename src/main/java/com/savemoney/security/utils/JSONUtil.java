package com.savemoney.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savemoney.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class JSONUtil {

    public <T> String toJson(T object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Falha na conversão de dados para o formato JSON");
        }
    }

    public <T> T toObject(String json, Class<T> classe) {
        try {
            return new ObjectMapper().readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Falha na conversão de dados para o formato " + classe.getSimpleName());
        }
    }
}
