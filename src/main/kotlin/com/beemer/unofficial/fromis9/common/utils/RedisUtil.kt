package com.beemer.unofficial.fromis9.common.utils

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisUtil(
    private val redisTemplate: StringRedisTemplate
) {
    fun getData(key: String): String? {
        val valueOperations = redisTemplate.opsForValue()
        return valueOperations[key]
    }

    fun setData(key: String, value: String) {
        val valueOperations = redisTemplate.opsForValue()
        valueOperations.set(key, value)
    }

    fun deleteData(key: String) {
        redisTemplate.delete(key)
    }

    fun hasKey(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }
}