package com.savemoney.security.domain.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = "Bearer " + token;
    }
}
