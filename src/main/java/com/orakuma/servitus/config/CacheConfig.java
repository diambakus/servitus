package com.orakuma.servitus.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {
    /** Dependencies attached to a servis, keyed by the servis publicId. */
    public static final String SERVIS_DEPENDENCIES = "servisDependencies";

    /**
     * In-memory cache, local to this instance. Entries are evicted explicitly by the write paths;
     * the TTL only bounds staleness for writes this instance cannot see (another instance, direct
     * DB changes).
     *
     * <p>The transaction-aware proxy defers evictions until the surrounding transaction commits, so
     * a concurrent read cannot re-cache the pre-commit state right after the eviction.
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(SERVIS_DEPENDENCIES);
        caffeineCacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .maximumSize(1_000)
                        .expireAfterWrite(Duration.ofMinutes(10))
                        .recordStats());
        return new TransactionAwareCacheManagerProxy(caffeineCacheManager);
    }
}
