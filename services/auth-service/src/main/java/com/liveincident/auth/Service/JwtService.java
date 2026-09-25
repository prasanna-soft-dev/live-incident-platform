package com.liveincident.auth.Service;

import com.liveincident.auth.Config.JwtProperties;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public JwtService(
            JwtEncoder jwtEncoder,
            JwtProperties jwtProperties
    ) {
        this.jwtEncoder = jwtEncoder;
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(
            String userId,
            String role,
            String teamId
    ) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(
                jwtProperties.getAccessTokenExpiration()
        );

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(userId)
                .audience(java.util.List.of(jwtProperties.getAudience()))
                .issuedAt(now)
                .expiresAt(expiresAt)
                .id(UUID.randomUUID().toString())
                .claim("role", role)
                .claim("team_id", teamId)
                .build();

        JwsHeader header = JwsHeader.with(
                        SignatureAlgorithm.RS256
                )
                .keyId(jwtProperties.getKeyId())
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }
}