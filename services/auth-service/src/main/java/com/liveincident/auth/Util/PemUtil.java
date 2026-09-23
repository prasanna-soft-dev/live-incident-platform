package com.liveincident.auth.Util;

import java.util.Base64;

public class PemUtil {
    private PemUtil() {

    }

    public static byte[] decode(String pem) {

        String content = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        return Base64.getDecoder().decode(content);
    }
}
