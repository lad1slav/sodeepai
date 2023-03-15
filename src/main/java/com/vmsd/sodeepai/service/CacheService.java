package com.vmsd.sodeepai.service;

import com.vmsd.sodeepai.controller.MessageController;
import com.vmsd.sodeepai.model.Message;
import com.vmsd.sodeepai.repository.RedisCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CacheService {

    private static final Logger logg = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private RedisCacheRepository redisCacheRepository;

    public Message savePromptToCache(String prompt, String context, boolean isImage) {
        logg.info("Saving to cache: ");
        Message messageContext = com.vmsd.sodeepai.model.Message.builder()
                .id(UUID.randomUUID().toString())
                .prompt(prompt)
                .context(context)
                .type(isImage ? Message.RecordType.IMAGE : Message.RecordType.MESSAGE)
                .build();
        logg.info(messageContext.toString());

        redisCacheRepository.save(messageContext);

        return messageContext;
    }

    public String saveMessageToCache(Message message) {
        logg.info("Saving message to cache: " + message);

        return redisCacheRepository.save(message).getId();
    }

    public List<Message> getAllCache() {
        logg.info("Get all cache...");
        List<Message> message = new  ArrayList<>();
        redisCacheRepository.findAll().forEach(message::add);
        return message;
    }

    public List<Message> getAllCacheForSenderName(String senderName) {
        logg.info("Get all cache for sender: " + senderName);
        return redisCacheRepository.findAllBySenderName(senderName);
    }
}