package com.firefly.cloud.adapter.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.firefly.cloud.repository.dao.CspConfigDao;
import com.firefly.cloud.repository.entity.CspConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class OssClientFactory {
    
    @Autowired
    private CspConfigDao cspConfigDao;
    
    // 使用ConcurrentHashMap存储不同bucket的OSS客户端
    private final Map<String, OSS> ossClientMap = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void init() {
        // 初始化时加载所有配置的OSS客户端
        refreshOssClients();
    }
    
    /**
     * 获取指定bucket的OSS客户端
     * @param bucket bucket名称
     * @return OSS客户端实例
     */
    public OSS getOssClient(String bucket) {
        OSS ossClient = ossClientMap.get(bucket);
        if (ossClient == null) {
            synchronized (this) {
                ossClient = ossClientMap.get(bucket);
                if (ossClient == null) {
                    // 如果不存在，尝试创建新的客户端
                    refreshOssClients();
                    ossClient = ossClientMap.get(bucket);
                }
            }
        }
        return ossClient;
    }
    
    /**
     * 刷新所有OSS客户端
     */
    public void refreshOssClients() {
        try {
            // 获取所有CSP配置
            List<CspConfigEntity> configs = cspConfigDao.findAll();
            
            // 关闭旧的客户端
            for (OSS client : ossClientMap.values()) {
                try {
                    client.shutdown();
                } catch (Exception e) {
                    log.error("关闭OSS客户端失败", e);
                }
            }
            ossClientMap.clear();
            
            // 创建新的客户端
            for (CspConfigEntity config : configs) {
                if ("ali".equals(config.getCsp())) {
                    OSS ossClient = new OSSClientBuilder().build(
                        config.getInternetEndpoint(),
                        config.getAccessKey(),
                        config.getSecret()
                    );
                    ossClientMap.put(config.getBucket(), ossClient);
                }
                // 可以在这里添加其他云服务商的客户端创建逻辑
            }
        } catch (Exception e) {
            log.error("刷新OSS客户端失败", e);
            throw new RuntimeException("刷新OSS客户端失败", e);
        }
    }
    
    /**
     * 关闭所有OSS客户端
     */
    public void shutdown() {
        for (OSS client : ossClientMap.values()) {
            try {
                client.shutdown();
            } catch (Exception e) {
                log.error("关闭OSS客户端失败", e);
            }
        }
        ossClientMap.clear();
    }
} 