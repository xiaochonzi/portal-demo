package com.example.portal.model.resolver;

public class KeyResolver {

    public static String captchaId(String captchaId) {
        return new StringBuilder("captcha:").append(captchaId).toString();
    }
}
