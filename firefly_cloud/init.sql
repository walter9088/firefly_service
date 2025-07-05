-- Firefly Cloud 模块数据库初始化脚本
-- 创建时间: 2025-06-15
-- 描述: 云存储模块相关表结构

-- 创建数据库
CREATE DATABASE IF NOT EXISTS firefly_cloud DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE firefly_cloud;

-- 云服务提供商配置表
CREATE TABLE IF NOT EXISTS csp_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    csp VARCHAR(20) NOT NULL COMMENT '云服务提供商：ali-阿里云，huawei-华为云，aws-亚马逊云',
    bucket VARCHAR(100) NOT NULL COMMENT '存储桶名称',
    region VARCHAR(50) COMMENT '区域ID',
    oss_region VARCHAR(50) COMMENT 'OSS存储地域ID',
    internet_endpoint VARCHAR(200) COMMENT '外网访问endpoint',
    intranet_endpoint VARCHAR(200) COMMENT '内网endpoint',
    access_key VARCHAR(100) NOT NULL COMMENT '访问密钥ID',
    secret VARCHAR(100) NOT NULL COMMENT '访问密钥Secret',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_csp_bucket (csp, bucket),
    INDEX idx_csp (csp),
    INDEX idx_bucket (bucket)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='云服务提供商配置表';

-- 存储目录表
CREATE TABLE IF NOT EXISTS storage_dir (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    parent_dir VARCHAR(500) DEFAULT '/' COMMENT '父目录路径',
    dir_name VARCHAR(100) NOT NULL COMMENT '目录名称',
    source VARCHAR(20) DEFAULT 'local' COMMENT '存储源：local-本地，ali-阿里云，huawei-华为云',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time VARCHAR(50) COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_parent_dir (parent_dir),
    INDEX idx_source (source),
    UNIQUE KEY uk_user_parent_dir (user_id, parent_dir, dir_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储目录表';

-- 文件信息表
CREATE TABLE IF NOT EXISTS file_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    file_name VARCHAR(255) NOT NULL COMMENT '文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_size BIGINT COMMENT '文件大小（字节）',
    file_type VARCHAR(50) COMMENT '文件类型',
    mime_type VARCHAR(100) COMMENT 'MIME类型',
    md5 VARCHAR(32) COMMENT '文件MD5值',
    source VARCHAR(20) DEFAULT 'local' COMMENT '存储源：local-本地，ali-阿里云，huawei-华为云',
    bucket VARCHAR(100) COMMENT '存储桶名称',
    object_key VARCHAR(500) COMMENT '对象键',
    url VARCHAR(500) COMMENT '访问URL',
    status TINYINT DEFAULT 1 COMMENT '状态：0-删除，1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_file_path (file_path),
    INDEX idx_source (source),
    INDEX idx_bucket (bucket),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';

-- 文件分享表
CREATE TABLE IF NOT EXISTS file_share (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    file_id BIGINT NOT NULL COMMENT '文件ID',
    user_id BIGINT NOT NULL COMMENT '分享用户ID',
    share_code VARCHAR(32) NOT NULL UNIQUE COMMENT '分享码',
    share_type TINYINT DEFAULT 1 COMMENT '分享类型：1-公开，2-私密',
    expire_time DATETIME COMMENT '过期时间',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_file_id (file_id),
    INDEX idx_user_id (user_id),
    INDEX idx_share_code (share_code),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件分享表';

-- 插入初始数据
INSERT INTO csp_config (csp, bucket, region, oss_region, internet_endpoint, access_key, secret) VALUES 
('ali', 'firefly-cloud-demo', 'cn-hangzhou', 'oss-cn-hangzhou', 'https://oss-cn-hangzhou.aliyuncs.com', 'your-access-key-id', 'your-access-key-secret'); 


-- firefly_cloud.cloud_file_meta_data definition

CREATE TABLE `cloud_file_meta_data` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `file_obj` varchar(100) DEFAULT NULL COMMENT '文件对象',
  `type` varchar(100) DEFAULT NULL COMMENT '文件类型',
  `length` varchar(100) DEFAULT NULL COMMENT '文件大小',
  `dir_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属文件夹目录id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储元数据';


-- firefly_cloud.csp_config definition

CREATE TABLE `csp_config` (
  `id` bigint NOT NULL COMMENT '存储桶唯一标识',
  `csp` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '云存服务商：huawei,ali,s3',
  `bucket` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '存储桶',
  `region` varchar(12) DEFAULT NULL COMMENT '区域id',
  `oss_region` varchar(16) DEFAULT NULL COMMENT 'oss存储地域ID',
  `internet_endpoint` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '外网访问endpoint',
  `intranet_endpoint` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内网endpoint',
  `access_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '令牌ak',
  `secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '令牌秘钥',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `csp_config_unique` (`bucket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- firefly_cloud.storage_dir definition

CREATE TABLE `storage_dir` (
  `id` bigint NOT NULL COMMENT '唯一标识',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `parent_dir` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父级目录',
  `dir_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目录名',
  `source` varchar(12) NOT NULL COMMENT '该路径创建业务来源：root:用户初始路径,custom,用户自己创建。',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(100) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `storage_dir_unique` (`user_id`,`parent_dir`,`dir_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件存储目录';


-- firefly_cloud.user_storage_space definition

CREATE TABLE `user_storage_space` (
  `id` bigint NOT NULL COMMENT '主键唯一标识',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'use_id',
  `storage_space` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户存储空间路径',
  `csp_id` bigint NOT NULL COMMENT '云存配置',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_csp_space_unique` (`user_id`,`storage_space`),
  KEY `user_csp_space_user_id_IDX` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;