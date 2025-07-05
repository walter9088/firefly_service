package com.firefly.auth.repository.mapper;

import com.firefly.auth.repository.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface AccountMapper {
    AccountEntity selectByUsername(@Param("userName") String userName);
    
    AccountEntity selectByNickName(@Param("nickName") String nickName);
    
    AccountEntity selectByEmail(@Param("email") String email);
    
    AccountEntity selectByMobile(@Param("mobile") String mobile);
    
    int insert(AccountEntity account);
    
    int updateLastLogin(@Param("userId") Long userId, @Param("lastLoginTime") LocalDateTime lastLoginTime);
    
    int updateStatus(@Param("userId") Long userId, @Param("status") String status);
} 