package com.bernardoms.integration;

import com.bernardoms.model.URLShortener;
import com.bernardoms.repository.ShortenerRepository;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;

public abstract class IntegrationTest {
    @Inject
    ShortenerRepository shortenerRepository;

    private static boolean alreadySaved = false;

    @BeforeEach
    public void setUp() {

        if (alreadySaved) {
            return;
        }
        shortenerRepository.persist(URLShortener.builder().originalURL("http://www.test.com").alias("abdefg").build());
        alreadySaved = true;
    }
}
