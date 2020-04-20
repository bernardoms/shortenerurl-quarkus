package com.bernardoms.service;

import com.bernardoms.dto.URLShortenerDTO;
import com.bernardoms.exception.AliasNotFoundException;
import com.bernardoms.model.URLShortener;
import com.bernardoms.repository.ShortenerRepository;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class ShortenerService {

    @Inject
    private ShortenerRepository shortenerRepository;

    public String createShortenerURL(URLShortenerDTO urlShortenerDTO) {

        urlShortenerDTO.generateShortnerURL();

        log.info("CREATING URL SHORTENER FOR URL " + urlShortenerDTO.getOriginalURL());

        shortenerRepository.persist(URLShortener.builder().alias(urlShortenerDTO.getAlias()).originalURL(urlShortenerDTO.getOriginalURL()).build());

        return urlShortenerDTO.getAlias();
    }

    public String redirect(String alias) throws AliasNotFoundException {
        var urlShortener = findURLByAlias(alias);

        urlShortener.setRedirectCount(urlShortener.getRedirectCount() + 1);

        log.info("incrementing redirect count " + urlShortener.getRedirectCount());

        shortenerRepository.persistOrUpdate(urlShortener);

        return urlShortener.getOriginalURL();
    }


    public URLShortener findURLByAlias(String alias) throws AliasNotFoundException {
        return shortenerRepository.findByAlias(alias).orElseThrow(() -> {
            log.info("Alias not found: " + alias);
            return new AliasNotFoundException("Alias not found!");
        });
    }

    public List<URLShortenerDTO> findAll() {
        return shortenerRepository.findAllByCountDescending()
                .stream()
                .map(s-> new URLShortenerDTO(s.getOriginalURL(), s.getAlias(), s.getRedirectCount())).collect(Collectors.toList());
    }

//    public List<URLShortenerDTO> findAll(Pageable pageable) {
//
//        var paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
//        var pageUrlShortener = shortenerRepository.findAll(paging);
//        return pageUrlShortener.map(p -> new URLShortenerDTO(p.getOriginalURL(), p.getAlias(), p.getRedirectCount()));
//    }
}
