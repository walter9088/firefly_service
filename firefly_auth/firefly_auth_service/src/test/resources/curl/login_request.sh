#!/bin/bash

# 设置基础URL
BASE_URL="http://localhost:8080/api/auth"

# 设置颜色输出
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# 打印分隔线
print_separator() {
    echo -e "\n${GREEN}========================================${NC}\n"
}

# 打印请求信息
print_request() {
    echo -e "${GREEN}请求: $1${NC}"
    echo -e "${GREEN}URL: $2${NC}"
    echo -e "${GREEN}请求体:${NC}"
    echo "$3" | jq '.'
}

# 打印响应信息
print_response() {
    echo -e "${GREEN}响应:${NC}"
    echo "$1" | jq '.'
}

# 测试1: 正常登录
print_separator
print_request "正常登录" "${BASE_URL}/login" '{
    "userName": "testuser",
    "password": "password123"
}'
RESPONSE=$(curl -s -X POST "${BASE_URL}/login" \
    -H "Content-Type: application/json" \
    -d '{
        "userName": "testuser",
        "password": "password123"
    }')
print_response "$RESPONSE"

# 测试2: 密码错误
print_separator
print_request "密码错误" "${BASE_URL}/login" '{
    "userName": "testuser",
    "password": "wrongpassword"
}'
RESPONSE=$(curl -s -X POST "${BASE_URL}/login" \
    -H "Content-Type: application/json" \
    -d '{
        "userName": "testuser",
        "password": "wrongpassword"
    }')
print_response "$RESPONSE"

# 测试3: 用户不存在
print_separator
print_request "用户不存在" "${BASE_URL}/login" '{
    "userName": "nonexistent",
    "password": "password123"
}'
RESPONSE=$(curl -s -X POST "${BASE_URL}/login" \
    -H "Content-Type: application/json" \
    -d '{
        "userName": "nonexistent",
        "password": "password123"
    }')
print_response "$RESPONSE"

# 测试4: 账号被锁定
print_separator
print_request "账号被锁定" "${BASE_URL}/login" '{
    "userName": "lockeduser",
    "password": "password123"
}'
RESPONSE=$(curl -s -X POST "${BASE_URL}/login" \
    -H "Content-Type: application/json" \
    -d '{
        "userName": "lockeduser",
        "password": "password123"
    }')
print_response "$RESPONSE"

# 测试5: 请求参数缺失
print_separator
print_request "请求参数缺失" "${BASE_URL}/login" '{
    "userName": "testuser"
}'
RESPONSE=$(curl -s -X POST "${BASE_URL}/login" \
    -H "Content-Type: application/json" \
    -d '{
        "userName": "testuser"
    }')
print_response "$RESPONSE"

# 测试6: 请求格式错误
print_separator
print_request "请求格式错误" "${BASE_URL}/login" '{
    "username": "testuser",
    "pass": "password123"
}'
RESPONSE=$(curl -s -X POST "${BASE_URL}/login" \
    -H "Content-Type: application/json" \
    -d '{
        "username": "testuser",
        "pass": "password123"
    }')
print_response "$RESPONSE"

# 测试7: 连续登录失败
print_separator
echo -e "${GREEN}测试连续登录失败${NC}"
for i in {1..6}; do
    echo -e "\n${GREEN}第${i}次尝试${NC}"
    RESPONSE=$(curl -s -X POST "${BASE_URL}/login" \
        -H "Content-Type: application/json" \
        -d '{
            "userName": "testuser",
            "password": "wrongpassword"
        }')
    print_response "$RESPONSE"
done

# 测试8: 解锁后登录
print_separator
echo -e "${GREEN}测试解锁后登录${NC}"

# 先解锁账号
print_request "解锁账号" "${BASE_URL}/unlock" '{
    "userId": 1
}'
RESPONSE=$(curl -s -X POST "${BASE_URL}/unlock" \
    -H "Content-Type: application/json" \
    -d '{
        "userId": 1
    }')
print_response "$RESPONSE"

# 然后尝试登录
print_request "解锁后登录" "${BASE_URL}/login" '{
    "userName": "testuser",
    "password": "password123"
}'
RESPONSE=$(curl -s -X POST "${BASE_URL}/login" \
    -H "Content-Type: application/json" \
    -d '{
        "userName": "testuser",
        "password": "password123"
    }')
print_response "$RESPONSE" 