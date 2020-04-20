package com.bernardoms.repository;

import com.bernardoms.model.URLShortener;
import io.quarkus.cache.CacheResult;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class ShortenerRepository implements PanacheMongoRepository<URLShortener> {
    @CacheResult(cacheName = "alias-cache")
    public Optional<URLShortener> findByAlias(String alias){
        return find("alias", alias).firstResultOptional();
    }
    public List<URLShortener> findAllByCountDescending() {
        return findAll(Sort.by("count", Sort.Direction.Descending)).list();
    }
}
