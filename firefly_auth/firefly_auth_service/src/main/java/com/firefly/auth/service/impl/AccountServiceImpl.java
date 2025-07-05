package com.firefly.auth.service.impl;

import com.firefly.auth.repository.entity.AccountEntity;
import com.firefly.auth.repository.mapper.AccountMapper;
import com.firefly.auth.service.AccountService;
import com.firefly.auth.service.dto.AccountLoginReqDTO;
import com.firefly.auth.service.dto.AccountRegisterReqDTO;
import com.firefly.auth.service.vo.AccountRspVO;
import com.firefly.auth.service.vo.LoginRspVO;
import com.firefly.common.model.HttpResponse;
import com.firefly.common.util.SnowflakeIdGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private static final String LOGIN_FAIL_COUNT_KEY = "auth:login:fail:count:";
    private static final int MAX_LOGIN_FAIL_COUNT = 5;
    private static final int LOCK_TIME_MINUTES = 30;
    private static final long JWT_EXPIRATION = 24 * 60 * 60 * 1000; // 24小时

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // 使用配置文件中的密钥创建 SecretKey
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public AccountRspVO getByUsername(String userName) {
        AccountEntity account = accountMapper.selectByUsername(userName);
        if (account == null) {
            return null;
        }
        return convertToVO(account);
    }

    @Override
    public AccountRspVO getByNickName(String nickName) {
        AccountEntity account = accountMapper.selectByNickName(nickName);
        if (account == null) {
            return null;
        }
        return convertToVO(account);
    }

    @Override
    public AccountRspVO getByEmail(String email) {
        AccountEntity account = accountMapper.selectByEmail(email);
        if (account == null) {
            return null;
        }
        return convertToVO(account);
    }

    @Override
    public AccountRspVO getByMobile(String mobile) {
        AccountEntity account = accountMapper.selectByMobile(mobile);
        if (account == null) {
            return null;
        }
        return convertToVO(account);
    }

    private AccountRspVO convertToVO(AccountEntity account) {
        AccountRspVO response = new AccountRspVO();
        response.setId(account.getId());
        response.setUserName(account.getUserName());
        response.setNickName(account.getNickName());
        response.setEmail(account.getEmail());
        response.setMobile(account.getMobile());
        response.setAccountStatus(account.getAccountStatus());
        response.setLastLoginTime(account.getLastLoginTime());
        response.setCreateTime(account.getCreateTime());
        response.setUpdateTime(account.getUpdateTime());
        return response;
    }


    @Override
    public int updateLastLogin(Long userId) {
        try {
            int result = accountMapper.updateLastLogin(userId, LocalDateTime.now());
            if (result == 1) {
                // 登录成功，清除失败次数
                redisTemplate.delete(LOGIN_FAIL_COUNT_KEY + userId);
                log.info("更新最后登录时间成功：userId={}", userId);
                return result;
            }
            log.warn("更新最后登录时间失败：数据库操作异常，入参：userId:{},影响行数={}", userId, result);
            return result;
        } catch (Exception e) {
            log.error("更新最后登录时间失败：{}", e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public HttpResponse<Void> updateStatus(Long userId, String status) {
        try {
            int result = accountMapper.updateStatus(userId, status);
            if (result == 1) {
                return HttpResponse.success();
            }
            log.warn("更新账号状态失败：数据库操作异常，入参：userId:{},status:{},影响行数={}", userId, status, result);
            return HttpResponse.badRequest("更新记录数" + result);
        } catch (Exception e) {
            log.error("更新账号状态失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("更新账号状态失败：数据库操作异常");
        }
    }

    @Override
    public boolean isAccountLocked(Long userId) {
        String key = LOGIN_FAIL_COUNT_KEY + userId;
        Long failCount = redisTemplate.opsForValue().get(key);
        return failCount != null && failCount >= MAX_LOGIN_FAIL_COUNT;
    }

    @Override
    public void incrementLoginFailCount(Long userId) {
        String key = LOGIN_FAIL_COUNT_KEY + userId;
        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, LOCK_TIME_MINUTES, TimeUnit.MINUTES);

        if (redisTemplate.opsForValue().get(key) >= MAX_LOGIN_FAIL_COUNT) {
            updateStatus(userId, "LOCKED");
        }
    }

    @Override
    public HttpResponse<Void> registerAccount(AccountRegisterReqDTO request) {
        try {
            // 检查用户名是否已存在
            if (getByUsername(request.getUserName()) != null) {
                return HttpResponse.badRequest("用户名已存在");
            }

            // 检查邮箱是否已存在
            if (getByEmail(request.getEmail()) != null) {
                return HttpResponse.badRequest("邮箱已被注册");
            }

            // 检查手机号是否已存在
            if (request.getMobile() != null && getByMobile(request.getMobile()) != null) {
                return HttpResponse.badRequest("手机号已被注册");
            }

            // 创建账号实体并注册
            AccountEntity account = convertToEntity(request);

            int result = accountMapper.insert(account);
            if (result == 1) {
                return HttpResponse.success();
            }
            log.warn("注册失败：数据库操作异常，入参：account:{},影响行数={}", account, result);

            return HttpResponse.badRequest("注册失败：数据库操作异常");
        } catch (Exception e) {
            log.error("注册账号失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("注册账号失败：" + e.getMessage());
        }
    }

    private AccountEntity convertToEntity(AccountRegisterReqDTO request) {
        AccountEntity account = new AccountEntity();
        account.setId(snowflakeIdGenerator.nextId()); // 使用雪花算法生成ID
        account.setUserName(request.getUserName());
        account.setNickName(request.getNickName());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setEmail(request.getEmail());
        account.setMobile(request.getMobile());
        account.setAccountStatus("ACTIVE"); // 默认激活状态
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        return account;
    }

    @Override
    public HttpResponse<LoginRspVO> login(AccountLoginReqDTO request) {
        try {
            AccountEntity account = accountMapper.selectByUsername(request.getUserName());
            if (account == null) {
                return HttpResponse.badRequest("用户名或密码错误");
            }

            // 检查账号是否被锁定
            if (isAccountLocked(account.getId())) {
                return HttpResponse.badRequest("账号已被锁定，请30分钟后重试");
            }

            // 验证密码
            if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
                incrementLoginFailCount(account.getId());
                return HttpResponse.badRequest("用户名或密码错误");
            }

            // 检查账号状态
            if (!"ACTIVE".equals(account.getAccountStatus())) {
                return HttpResponse.badRequest("账号已被锁定或删除");
            }

            // 更新最后登录时间
            int result = updateLastLogin(account.getId());
            if (result == 1) {
                // 生成JWT token
                String token = generateJwtToken(account);
                
                // 构建响应对象
                LoginRspVO response = new LoginRspVO();
                response.setToken(token);
                response.setExpiresIn(JWT_EXPIRATION);
                
                return HttpResponse.success(response);
            }
            log.warn("更新最后登录时间失败：数据库操作异常，入参：userId={},影响行数={}", account.getId(), result);
            return HttpResponse.serverError("更新最后登录时间失败：数据库操作异常");
        } catch (Exception e) {
            log.error("登录失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("登录失败：" + e.getMessage());
        }
    }

    private String generateJwtToken(AccountEntity account) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(account.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("userName", account.getUserName())
                .claim("nickName", account.getNickName())
                .signWith(key)
                .compact();
    }

    @Override
    public HttpResponse<Void> lockAccount(Long userId) {
        try {
            return updateStatus(userId, "LOCKED");
        } catch (Exception e) {
            log.error("锁定账号失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("锁定账号失败：" + e.getMessage());
        }
    }

    @Override
    public HttpResponse<Void> unlockAccount(Long userId) {
        try {
            return updateStatus(userId, "ACTIVE");
        } catch (Exception e) {
            log.error("解锁账号失败：{}", e.getMessage(), e);
            return HttpResponse.serverError("解锁账号失败：" + e.getMessage());
        }
    }
} 