# Firefly 服务

## 开发环境要求

- Java 11+
- Maven 3.6+
- IDE (推荐使用 IntelliJ IDEA)

## 项目结构

```
firefly_service/
├── firefly_common/          # 通用基础组件模块
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/       # Java 源代码
│   │   │   └── resources/  # 配置文件
│   │   └── test/           # 测试代码
│   └── pom.xml             # Maven 配置文件
├── firefly_auth/           # 账号业务模块
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/       # Java 源代码
│   │   │   └── resources/  # 配置文件
│   │   └── test/           # 测试代码
│   └── pom.xml             # Maven 配置文件
├── firefly_cloud/          # 云存储相关业务模块
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/       # Java 源代码
│   │   │   └── resources/  # 配置文件
│   │   └── test/           # 测试代码
│   └── pom.xml             # Maven 配置文件
└── pom.xml                 # 父模块 Maven 配置文件
```

## 快速开始

1. 安装依赖：
   ```bash
   mvn clean install
   ```

2. 启动应用：
   ```bash
   mvn spring-boot:run
   ```

3. 访问 API：
   - 开发环境：http://localhost:8080
   - 生产环境：根据实际部署配置

## 开发指南

- 使用 Spring Boot 框架
- 遵循 RESTful API 设计规范
- 使用 JPA 进行数据访问
- 实现完整的单元测试和集成测试
- 遵循微服务架构原则

## 模块说明

### firefly_common
通用基础组件模块，包含：
- 通用工具类
- 基础配置
- 数据访问层
- 通用异常处理
- 通用响应封装

### firefly_auth
账号业务模块，包含：
- 用户认证
- 权限管理
- 会话管理
- JWT 令牌处理 