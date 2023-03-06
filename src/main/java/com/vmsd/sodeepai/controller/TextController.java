package com.vmsd.sodeepai.controller;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(value = "/chat")
@Slf4j
public class TextController {

    private static final Logger logg = LoggerFactory.getLogger(TextController.class);

    @Value("${OPENAI_TOKEN}")
    String token;

    @GetMapping
    public String getTextForPrompt(@RequestParam String prompt) {
        OpenAiService service = new OpenAiService(token);

        AtomicReference<String> answer = new AtomicReference<>("");

        logg.info("Creating chat...");
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage("user", prompt)))
                .build();
        service.createChatCompletion(chatCompletionRequest)
                .getChoices()
                .forEach(chatCompletionChoice -> {
                    answer.set(answer.get() + chatCompletionChoice.getMessage().getContent() + "\n");
                });

        return "hello i'm new neuro by lad1slavv<br><br>" + answer;
    }
}
