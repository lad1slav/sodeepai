package com.vmsd.sodeepai.service;

import com.vmsd.sodeepai.controller.TextController;
import com.vmsd.sodeepai.model.Cache;
import com.vmsd.sodeepai.repository.RedisCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GeneratorService {

    private static final Logger logg = LoggerFactory.getLogger(TextController.class);

    @Autowired
    private RedisCacheRepository redisCacheRepository;

    public Cache savePromptToCache(String prompt, String context, boolean isImage) {
        logg.info("Saving to cache: ");
        Cache cacheContext = Cache.builder()
                .id(UUID.randomUUID().toString())
                .prompt(prompt)
                .context(context)
                .type(isImage ? Cache.RecordType.IMAGE : Cache.RecordType.MESSAGE)
                .build();
        logg.info(cacheContext.toString());

        redisCacheRepository.save(cacheContext);

        return cacheContext;
    }

    public List<Cache> getAllCache() {
        logg.info("Get all cache...");
        List<Cache> cache = new  ArrayList<>();
        redisCacheRepository.findAll().forEach(cache::add);
        return cache;
    }
}