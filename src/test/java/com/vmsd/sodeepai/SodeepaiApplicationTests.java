package com.vmsd.sodeepai;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
class SodeepaiApplicationTests {
    private static final Logger logg = LoggerFactory.getLogger(SodeepaiApplicationTests.class);

    @Value("${OPENAI_TOKEN}")
    String token;

    @Test
    public void testImageGeneration() {
        OpenAiService service = new OpenAiService(token, Duration.ofMinutes(2));

        logg.info("Creating image...");
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt("merlin from shrek 2 in the magic laboratory from the cartoon \"Shrek 2\" making magic"
                         + ", hd gddr 1800x720 HQ HD nvidia lightning")
                .user("test")
                .build();

        logg.info("Image is located at:");
        logg.info(service.createImage(request).getData().get(0).getUrl());
        assert true;
    }
}
