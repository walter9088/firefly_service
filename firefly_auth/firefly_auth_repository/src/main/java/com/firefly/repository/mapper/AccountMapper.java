package com.firefly.repository.mapper;

import com.firefly.auth.repository.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface AccountMapper {
    AccountEntity selectByUsername(@Param("username") String username);

    AccountEntity selectByEmail(@Param("email") String email);

    AccountEntity selectByMobile(@Param("mobile") String mobile);
    
    int insert(AccountEntity account);
    
    int updateLastLogin(@Param("userId") Integer userId, @Param("lastLogin") LocalDateTime lastLogin);
    
    int updateStatus(@Param("userId") Integer userId, @Param("status") String status);
} 