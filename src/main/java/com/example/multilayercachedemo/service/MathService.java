package com.example.multilayercachedemo.service;

import com.example.multilayercachedemo.cache.BeanIds;
import com.example.multilayercachedemo.cache.CacheIds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.example.multilayercachedemo.cache.CacheableConfig;
import com.example.multilayercachedemo.cache.CacheableConfig.CacheTarget;
import org.springframework.cache.annotation.Cacheable;

@Service
public class MathService {

    @CacheableConfig(cacheTarget = CacheTarget.MEMORY)
    @Cacheable(cacheNames = {CacheIds.MATH_CACHE}, key="#root.methodName + '_' + #a + '_' + #b",
            cacheResolver = BeanIds.CACHE_RESOLVER)
    public Integer sum(int a, int b) {
        return a + b;
    }

    @CacheableConfig(cacheTarget = CacheTarget.SHARED)
    @Cacheable(cacheNames = {CacheIds.MATH_CACHE}, key="#root.methodName + '_' + #a + '_' + #b",
            cacheResolver = BeanIds.CACHE_RESOLVER)
    public Integer multiply(int a, int b) {
        return a * b;
    }

    @CacheableConfig(cacheTarget = CacheTarget.MEMORY_AND_SHARED)
    @Cacheable(cacheNames = {CacheIds.MATH_CACHE}, key="#root.methodName + '_' + #a + '_' + #b",
            cacheResolver = BeanIds.CACHE_RESOLVER)
    public Integer subtract(int a, int b) {
        return a - b;
    }

    @CacheableConfig(cacheTarget = CacheTarget.MEMORY_AND_SHARED)
    @CacheEvict(cacheNames = {CacheIds.MATH_CACHE}, key="#name",
            cacheResolver = BeanIds.CACHE_RESOLVER)
    public void evictMathCache(String name){}

}
