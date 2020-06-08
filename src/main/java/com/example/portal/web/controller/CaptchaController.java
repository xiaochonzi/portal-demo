package com.example.portal.web.controller;

import com.example.portal.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping
    public ResponseEntity<byte[]> get() throws IOException {
        return captchaService.create();
    }
}
