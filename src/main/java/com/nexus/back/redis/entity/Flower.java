package com.nexus.back.redis.entity;

import com.nexus.validations.NonNullOrBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
@Data
@NoArgsConstructor
@RedisHash("FLower")
public class Flower {
    @NonNullOrBlank
    String id;

    @NonNullOrBlank
    @Indexed
    @Id
    String filename;

    @NonNullOrBlank
    String file;

    public Flower(String id, String filename, String file) {
        this.id = id;
        this.filename = filename;
        this.file = file;
    }
}
