package com.smartdesk.backend.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        Set<GrantedAuthority> authorities = new HashSet<>();

        // 1️⃣ Extract Scopes → SCOPE_xxx
        Object scopeObj = jwt.getClaim("scope");
        if (scopeObj instanceof String scopeStr) {
            Arrays.stream(scopeStr.split(" "))
                    .forEach(scope ->
                            authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope)));
        }

        // 2️⃣ Extract Cognito Groups → ROLE_xxx
        Object groupsObj = jwt.getClaim("cognito:groups");
        if (groupsObj instanceof Collection<?> groups) {
            groups.forEach(g ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + g)));
        }

        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }
}
