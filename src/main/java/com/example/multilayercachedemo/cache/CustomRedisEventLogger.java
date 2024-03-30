package com.example.multilayercachedemo.cache;

import lombok.extern.slf4j.Slf4j;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryRemovedListener;

@Slf4j
public class CustomRedisEventLogger implements CacheEntryRemovedListener<Object, Object> {
    @Override
    public void onRemoved(Iterable<CacheEntryEvent<?, ?>> iterable) throws CacheEntryListenerException {
        for (var e : iterable) {
            log.info("Event from redis: {}", e.getEventType());
        }
    }
}
