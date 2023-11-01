package com.everyschool.chatservice.api.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisUtils {
    private RedisTemplate<String, String> redisTemplate;

    public void insertList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public void getListSize(String key) {
        redisTemplate.opsForList().size(key);
    }

    public void insertString(String key, String value) {
        redisTemplate.delete(key);
        redisTemplate.opsForValue().append(key, value);
    }

    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteString(String key) {
        redisTemplate.delete(key);
    }
}


// TODO: 2023-11-01 이거 임시 커밋함 해야함