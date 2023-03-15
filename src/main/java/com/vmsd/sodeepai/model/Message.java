package com.vmsd.sodeepai.model;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("Cache")
@Data
@Builder
@AllArgsConstructor
public class Message implements Serializable {
    public enum RecordType {
        MESSAGE, IMAGE
    }

    private String id;
    private String prompt;
    private String senderName;
    private RecordType type;
    private String context;
    private LocalDateTime time;
}
