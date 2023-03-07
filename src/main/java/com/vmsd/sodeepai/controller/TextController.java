package com.vmsd.sodeepai.controller;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
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
        OpenAiService service = new OpenAiService(token, Duration.ofMinutes(2));

        AtomicReference<String> answer = new AtomicReference<>("");

        AtomicReference<String> imageContext = new AtomicReference<>("");

        logg.info("Creating chat...");
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage("system",
                                "You are a helpful assistant that write middle size story about given text."),
                        new ChatMessage("user",
                                prompt)))
                .build();
        try {
            service.createChatCompletion(chatCompletionRequest)
                    .getChoices()
                    .forEach(chatCompletionChoice -> {
                        answer.set(answer.get() + chatCompletionChoice.getMessage().getContent() + "<br><br>");
                        imageContext.set(chatCompletionChoice.getMessage().getContent());
                    });
        } catch (Exception e) {
            logg.error(e.getMessage());
            e.printStackTrace();
        }

        logg.info("Creating story...");
        CompletionRequest completionStoryRequest = CompletionRequest.builder()
                .model("davinci")
                .prompt("Please write crazy amazing story in 20 sentences about this context" +
                        ": \"" + prompt + "\"")
                .user("user")
                .n(1)
                .build();
        try {
            service.createCompletion(completionStoryRequest).getChoices().forEach(completionChoice -> {
                answer.set(answer.get() + completionChoice.getText() + "<br><br>");
                logg.info(completionChoice.toString());
            });
        } catch (Exception e) {
            logg.error(e.getMessage());
            e.printStackTrace();
        }

        logg.info("Creating image...");
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(prompt + ", hd gddr 1800x720 HQ HD nvidia lightning")
                .n(4)
                .build();

        logg.info("Image is located at:");
        answer.set(answer.get() + "Image is located at:<br><br>");
        try {
            service.createImage(request).getData().forEach(data->{
                logg.info(data.getUrl());

                answer.set(answer.get() + "<a href=\"" + data.getUrl() + "\">" + data.getUrl() + "</a><br><br>");
            });
        } catch (Exception e) {
            logg.error(e.getMessage());
            e.printStackTrace();
        }

        return "hello i'm new neuro by lad1slavv<br><br>" + answer + "<br><br>end of session...";
    }
}
