package com.romoreno.dentalium.infraestructure.config.security;

import com.romoreno.dentalium.domain.port.in.UserPort;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserPort userPort;

    public CustomUserDetailsService(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = userPort.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else if (!user.isActive()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user.getIdentification(), user.getUser(),
                user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
    }

}
