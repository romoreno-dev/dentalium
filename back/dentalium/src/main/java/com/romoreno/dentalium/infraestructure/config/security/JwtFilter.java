package com.romoreno.dentalium.infraestructure.config.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtAdministrator jwtAdministrator;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtAdministrator jwtAdministrator, UserDetailsService userDetailsService) {
        this.jwtAdministrator = jwtAdministrator;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final var authorizationHeader = request.getHeader("Authorization");
        final var securityContext = SecurityContextHolder.getContext();
        String role = "";

        if (securityContext.getAuthentication() != null && securityContext.getAuthentication().isAuthenticated()) {
            role = securityContext.getAuthentication().getAuthorities().stream().findFirst().get().getAuthority();
        }

        if (!"OPTIONS".equals(request.getMethod()) && StringUtils.isNotBlank(authorizationHeader)) {
            if (authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                try {
                    jwtAdministrator.validateToken(token);

                    final var username = jwtAdministrator.getUsernameFromJwt(token);
                    role = jwtAdministrator.getRolesFromJwt(token).get(0);
                    final var id = jwtAdministrator.getIdFromJwt(token);

                    final var userDetails = new CustomUserDetails(id, username,
                            null, AuthorityUtils.commaSeparatedStringToAuthorityList(role));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    securityContext.setAuthentication(authentication);

                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                    return;
                }
            }

            if (securityContext.getAuthentication() != null && securityContext.getAuthentication().isAuthenticated()) {

                response.setHeader("Authorization", String.format("Bearer %s",
                        jwtAdministrator.generateJwt(securityContext.getAuthentication().getName(),
                                ((CustomUserDetails) securityContext.getAuthentication().getPrincipal()).getId(),
                                Collections.singletonList(role))));

                response.setHeader("X-User-Role", role);
                response.setHeader("X-User-Id", ((CustomUserDetails) securityContext.getAuthentication().getPrincipal()).getId());
            }
        } else if ("/user/change".equals(request.getServletPath())) {
            final var userDetails = new CustomUserDetails("", "",
                    null, AuthorityUtils.commaSeparatedStringToAuthorityList(role));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            securityContext.setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
