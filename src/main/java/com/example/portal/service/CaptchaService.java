package com.example.portal.service;

import com.example.portal.model.enums.ErrorCode;
import com.example.portal.model.exception.BaseException;
import com.example.portal.model.resolver.KeyResolver;
import com.example.portal.utils.Captcha;
import com.example.portal.utils.RedisService;
import com.example.portal.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {

    @Autowired
    private RedisService redisService;

    public ResponseEntity<byte[]> create() throws IOException {
        //得到验证码 生成指定验证码
        String captchaId = UUID.randomUUID().toString().replace("-", "");//验证码id
        String code = new Captcha().randomStr(4);//生成验证码
        // 缓存验证码id以及验证码
        redisService.set(KeyResolver.captchaId(captchaId), code, 5L, TimeUnit.MINUTES);
        Captcha vCode = new Captcha(125, 45, 4, 10, code);
        HttpHeaders headers = new HttpHeaders();
        headers.add("captchaId", captchaId);
        headers.add("Content-Type", MediaType.IMAGE_PNG_VALUE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        vCode.write(stream);
        return ResponseEntity.ok().headers(headers).body(stream.toByteArray());
    }

    public void verifyCaptcha(String captchaId, String captchaCode) {
        if (!redisService.hasKey(KeyResolver.captchaId(captchaId))) {
            throw new BaseException(ErrorCode.CAPTCHA_EXPIRE);
        }
        if (!StringUtils.equalsIgnoreCase(redisService.get(KeyResolver.captchaId(captchaId)), captchaCode)) {
            throw new BaseException(ErrorCode.CAPTCHA_ERROR);
        }
    }

}
