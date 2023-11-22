package com.karaoke.karaoke_backend.security;

import com.karaoke.karaoke_backend.model.Employee;
import com.karaoke.karaoke_backend.model.LoginSession;
import com.karaoke.karaoke_backend.repository.LoginSessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final LoginSessionRepository loginSessionRepository;

    public AuthFilter(LoginSessionRepository loginSessionRepository) {
        this.loginSessionRepository = loginSessionRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && !token.isEmpty()) {
            String rawToken = token.substring("Bearer ".length());
            Optional<LoginSession> account = loginSessionRepository.findByToken(rawToken);
            if (account.isPresent()) {
                Set<String> permissions = Set.of("USER");
                List<SimpleGrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                Authentication authentication = new UsernamePasswordAuthenticationToken(account.get().getEmployee(), token, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
