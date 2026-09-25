package com.liveincident.auth.Config;

import com.liveincident.auth.Util.PemUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class JwtKeyConfig {

    private final JwtProperties jwtProperties;
    private final PemUtil pemUtil;

    public JwtKeyConfig(JwtProperties jwtProperties, PemUtil pemUtil) {
        this.jwtProperties = jwtProperties;
        this.pemUtil = pemUtil;
    }

    @Bean
    public RSAPrivateKey privateKey() {
        try {
            String pem = Files.readString(
                    Path.of(jwtProperties.getPrivateKeyPath())
            );

            byte[] keyBytes = pemUtil.decode(pem);

            PKCS8EncodedKeySpec keySpec =
                    new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory =
                    KeyFactory.getInstance("RSA");

            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to load JWT private key",
                    e
            );
        }
    }

    @Bean
    public RSAPublicKey publicKey() {
        try {
            String pem = Files.readString(
                    Path.of(jwtProperties.getPublicKeyPath())
            );

            byte[] keyBytes = pemUtil.decode(pem);

            X509EncodedKeySpec keySpec =
                    new X509EncodedKeySpec(keyBytes);

            KeyFactory keyFactory =
                    KeyFactory.getInstance("RSA");

            return (RSAPublicKey) keyFactory.generatePublic(keySpec);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to load JWT public key",
                    e
            );
        }
    }


}