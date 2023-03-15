package com.vmsd.sodeepai.controller;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import com.vmsd.sodeepai.model.Message;
import com.vmsd.sodeepai.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(value = "/chat")
@Slf4j
public class MessageController {

    private static final Logger logg = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private CacheService cacheService;

    @Value("${OPENAI_TOKEN}")
    String token;

    @PostMapping("/saveMessage")
    public String saveMessage(@RequestBody Message message) {
        OpenAiService service = new OpenAiService(token, Duration.ofMinutes(2));

        AtomicReference<String> answer = new AtomicReference<>("");

        logg.info("Creating chat...");
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage("system",
                                "You are a helpful and so smart assistant that know everything, " +
                                        "but you will communicate in bad boy style."),
                        new ChatMessage("user",
                                message.getPrompt())))
                .build();

        service.createChatCompletion(chatCompletionRequest)
                .getChoices()
                .forEach(chatCompletionChoice -> {
                    answer.set(answer.get() + chatCompletionChoice.getMessage().getContent());
                });

        Message newMessage = Message.builder()
                .id(UUID.randomUUID().toString())
                .type(message.getType())
                .senderName(message.getSenderName())
                .prompt(message.getPrompt())
                .context(answer.get())
                .time(LocalDateTime.now())
                .build();

        cacheService.saveMessageToCache(newMessage);

        return newMessage.getPrompt();
    }

    @GetMapping("/getMessages")
    public List<Message> getMessages(@RequestParam String senderName) {
        return cacheService.getAllCacheForSenderName(senderName);
    }

}
