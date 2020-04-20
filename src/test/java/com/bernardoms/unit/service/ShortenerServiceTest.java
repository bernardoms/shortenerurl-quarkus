package com.bernardoms.unit.service;


import com.bernardoms.dto.URLShortenerDTO;
import com.bernardoms.exception.AliasNotFoundException;
import com.bernardoms.model.URLShortener;
import com.bernardoms.repository.ShortenerRepository;
import com.bernardoms.service.ShortenerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShortenerServiceTest {

    @Mock
    private ShortenerRepository shortenerRepository;

    @InjectMocks
    private ShortenerService shortenerService;

    @Test
    public void test_create_alias(){
        var alias = shortenerService.createShortenerURL(URLShortenerDTO.builder().originalURL("http://test.com").build());

        verify(shortenerRepository, times(1)).persist(any(URLShortener.class));

        assertEquals(alias.length(), 6);
    }

    @Test
    public void test_redirect_found_alias() throws AliasNotFoundException {
        var optionalURLShortener = Optional.of(URLShortener.builder().originalURL("http://test.com").alias("abdefg").redirectCount(0).build());

        when(shortenerRepository.findByAlias(anyString())).thenReturn(optionalURLShortener);

        String originalURL = shortenerService.redirect("abdefg");

        verify(shortenerRepository, times(1)).persistOrUpdate(any(URLShortener.class));

        assertEquals(originalURL, "http://test.com");
    }

    @Test
    public void test_redirect_not_found_alias() {
        try {
            shortenerService.redirect("abdefg");
        } catch (AliasNotFoundException e) {
            verify(shortenerRepository, times(0)).persistOrUpdate(any(URLShortener.class));
        }
    }

    @Test
    public void test_find_all_shortened_URL() {

        var urlShortener = URLShortener.builder().originalURL("http://test.com").alias("abdefg").redirectCount(0).build();

        when(shortenerRepository.findAllByCountDescending()).thenReturn(Collections.singletonList(urlShortener));

        var allShortened = shortenerService.findAll();

        assertEquals(allShortened.get(0).getAlias(), "abdefg");

        assertEquals(allShortened.get(0).getOriginalURL(), "http://test.com");

        assertEquals(allShortened.get(0).getRedirectCount(), 0);
    }
}
