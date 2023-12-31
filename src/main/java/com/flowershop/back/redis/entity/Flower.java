package com.flowershop.back.redis.entity;

import com.flowershop.back.configuration.annotations.IsValid;
import com.flowershop.back.configuration.annotations.ValidBase64;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
@Data
@NoArgsConstructor
@RedisHash("FLower")
public class Flower {
    @IsValid
    String id;

    @IsValid
    @Indexed
    @Id
    String filename;

    @IsValid
    @ValidBase64
    String file;

    public Flower(String id, String filename, String file) {
        this.id = id;
        this.filename = filename;
        this.file = file;
    }
}
