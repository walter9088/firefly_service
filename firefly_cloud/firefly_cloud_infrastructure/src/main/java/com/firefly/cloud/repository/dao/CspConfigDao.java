package com.firefly.cloud.repository.dao;

import com.firefly.cloud.repository.entity.CspConfigEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CspConfigDao {
    
    @Insert("INSERT INTO csp_config (id, csp, bucket, region, oss_region, internet_endpoint, intranet_endpoint, access_key, secret, create_time, update_time) " +
            "VALUES (#{id}, #{csp}, #{bucket}, #{region}, #{ossRegion}, #{internetEndpoint}, #{intranetEndpoint}, #{accessKey}, #{secret}, #{createTime}, #{updateTime})")
    int insert(CspConfigEntity config);
    
    @Update("UPDATE csp_config SET csp = #{csp}, region = #{region}, oss_region = #{ossRegion}, " +
            "internet_endpoint = #{internetEndpoint}, intranet_endpoint = #{intranetEndpoint}, " +
            "access_key = #{accessKey}, secret = #{secret}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    int update(CspConfigEntity config);
    
    @Delete("DELETE FROM csp_config WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT * FROM csp_config WHERE id = #{id}")
    CspConfigEntity findById(Long id);
    
    @Select("SELECT * FROM csp_config WHERE bucket = #{bucket}")
    CspConfigEntity findByBucket(String bucket);
    
    @Select("SELECT * FROM csp_config")
    List<CspConfigEntity> findAll();
} 