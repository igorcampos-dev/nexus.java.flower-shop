package com.flowershop.back.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class ServerLink {

    @Value("${api.link.address}")
    private String link;

}
