package com.firefly.auth.service;

import com.firefly.auth.service.dto.AccountLoginReqDTO;
import com.firefly.auth.service.dto.AccountRegisterReqDTO;
import com.firefly.auth.service.vo.AccountRspVO;
import com.firefly.auth.service.vo.LoginRspVO;
import com.firefly.common.model.HttpResponse;

public interface AccountService {
    AccountRspVO getByUsername(String userName);
    
    AccountRspVO getByNickName(String nickName);
    
    AccountRspVO getByEmail(String email);
    
    AccountRspVO getByMobile(String mobile);

    int updateLastLogin(Long userId);
    
    HttpResponse<Void> updateStatus(Long userId, String status);
    
    /**
     * 检查账号是否被锁定
     * @param userId 用户ID
     * @return 是否被锁定
     */
    boolean isAccountLocked(Long userId);
    
    /**
     * 增加登录失败次数
     * @param userId 用户ID
     */
    void incrementLoginFailCount(Long userId);
    
    /**
     * 注册新用户
     * @param request 注册请求
     * @return 注册结果
     */
    HttpResponse<Void> registerAccount(AccountRegisterReqDTO request);
    
    /**
     * 用户登录
     * @param request 登录请求
     * @return 登录结果
     */
    HttpResponse<LoginRspVO> login(AccountLoginReqDTO request);
    
    /**
     * 锁定账号
     * @param userId 用户ID
     * @return 操作结果
     */
    HttpResponse<Void> lockAccount(Long userId);
    
    /**
     * 解锁账号
     * @param userId 用户ID
     * @return 操作结果
     */
    HttpResponse<Void> unlockAccount(Long userId);
} 