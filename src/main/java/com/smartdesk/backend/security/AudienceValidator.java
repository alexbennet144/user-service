package com.smartdesk.backend.security;

import java.util.List;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class AudienceValidator  {

    private final String audience;
    private final OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);

    public AudienceValidator(String audience) {
        this.audience = audience;
    }

  //  @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        if (audience == null || audience.isBlank()) {
            // no audience configured, accept
            return OAuth2TokenValidatorResult.success();
        }

        Object audClaim = token.getClaims().get("aud");
        if (audClaim instanceof String) {
            if (audience.equals(audClaim)) return OAuth2TokenValidatorResult.success();
        } else if (audClaim instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> audList = (List<String>) audClaim;
            if (audList.contains(audience)) return OAuth2TokenValidatorResult.success();
        }
        return OAuth2TokenValidatorResult.failure(error);
    }
}
