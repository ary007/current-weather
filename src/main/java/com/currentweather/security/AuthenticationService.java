package com.currentweather.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import java.time.Duration;

public class AuthenticationService {
    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String AUTH_TOKEN = "weather";

    static Bucket bucket = resolveBucket();

    public static Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        System.out.println("probe: "+probe.getRemainingTokens());
        if(!probe.isConsumed()) {
            throw new BadCredentialsException("Too many requests");
        }

        return new ApiKeyAuthenticationToken(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

    public static Bucket resolveBucket() {
        System.out.println(" Bucket created");
        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofHours(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
