package com.vmsd.sodeepai.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Cache")
@Data
@Builder
@AllArgsConstructor
public class Cache implements Serializable {
    public enum RecordType {
        MESSAGE, IMAGE
    }

    private String id;
    private String prompt;
    private RecordType type;
    private String context;
}
