#!/bin/bash

# 设置基础URL
BASE_URL="http://localhost:8080/api/auth"

# 测试1: 正常登录
echo "测试1: 正常登录"
curl -X POST "${BASE_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "testuser",
    "password": "password123"
  }'
echo -e "\n\n"

# 测试2: 密码错误
echo "测试2: 密码错误"
curl -X POST "${BASE_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "testuser",
    "password": "wrongpassword"
  }'
echo -e "\n\n"

# 测试3: 用户不存在
echo "测试3: 用户不存在"
curl -X POST "${BASE_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "nonexistent",
    "password": "password123"
  }'
echo -e "\n\n"

# 测试4: 账号被锁定
echo "测试4: 账号被锁定"
curl -X POST "${BASE_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "lockeduser",
    "password": "password123"
  }'
echo -e "\n\n"

# 测试5: 请求参数缺失
echo "测试5: 请求参数缺失"
curl -X POST "${BASE_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "testuser"
  }'
echo -e "\n\n"

# 测试6: 请求格式错误
echo "测试6: 请求格式错误"
curl -X POST "${BASE_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "pass": "password123"
  }'
echo -e "\n\n"

# 测试7: 连续登录失败
echo "测试7: 连续登录失败"
for i in {1..6}; do
  echo "第${i}次尝试"
  curl -X POST "${BASE_URL}/login" \
    -H "Content-Type: application/json" \
    -d '{
      "userName": "testuser",
      "password": "wrongpassword"
    }'
  echo -e "\n"
done
echo -e "\n\n"

# 测试8: 解锁后登录
echo "测试8: 解锁后登录"
# 先解锁账号
curl -X POST "${BASE_URL}/unlock" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1
  }'
echo -e "\n"

# 然后尝试登录
curl -X POST "${BASE_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "testuser",
    "password": "password123"
  }'
echo -e "\n\n" 