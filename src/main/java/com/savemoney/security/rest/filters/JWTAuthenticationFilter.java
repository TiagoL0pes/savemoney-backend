package com.savemoney.security.rest.filters;

import com.savemoney.security.domain.responses.TokenPayloadResponse;
import com.savemoney.security.domain.requests.AuthRequest;
import com.savemoney.security.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    public JWTAuthenticationFilter(UserDetailsService userDetailsService,
                                   JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String token = recoverToken(request);
        boolean isTokenValid = jwtUtil.isTokenValid(token);
        if (isTokenValid) {
            authenticateUser(token);
        }
        chain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }

    private void authenticateUser(String token) {
        TokenPayloadResponse payload = jwtUtil.getPayloadFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(payload.getEmail());
        UsernamePasswordAuthenticationToken authentication = new AuthRequest(payload.getEmail(), userDetails.getPassword())
                .generateCredencials(Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
