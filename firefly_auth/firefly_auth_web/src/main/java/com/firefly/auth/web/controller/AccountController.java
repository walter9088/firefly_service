package com.firefly.auth.web.controller;

import com.firefly.auth.service.AccountService;
import com.firefly.auth.service.dto.AccountLoginReqDTO;
import com.firefly.auth.service.dto.AccountRegisterReqDTO;
import com.firefly.auth.service.vo.LoginRspVO;
import com.firefly.common.model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    private AccountService accountService;
    
    @PostMapping("/register")
    public HttpResponse<Void> register(@RequestBody AccountRegisterReqDTO request) {
        try {
            return accountService.registerAccount(request);
        } catch (Exception e) {
            log.error("注册失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("注册失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public HttpResponse<LoginRspVO> login(@RequestBody AccountLoginReqDTO request) {
        try {
            return accountService.login(request);
        } catch (Exception e) {
            log.error("登录失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("登录失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/lock/{userId}")
    public HttpResponse<Void> lockAccount(@PathVariable Long userId) {
        try {
            return accountService.lockAccount(userId);
        } catch (Exception e) {
            log.error("锁定账号失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("锁定账号失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/unlock/{userId}")
    public HttpResponse<Void> unlockAccount(@PathVariable Long userId) {
        try {
            return accountService.unlockAccount(userId);
        } catch (Exception e) {
            log.error("解锁账号失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("解锁账号失败：" + e.getMessage());
        }
    }
} 