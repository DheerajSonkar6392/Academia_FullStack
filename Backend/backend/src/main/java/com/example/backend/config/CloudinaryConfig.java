package com.example.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dmzb1cnwn",
                "api_key", "189657135919753",
                "api_secret", "p7jkP4mq2Fr8JvLTauOp8P9BYHM",
                "secure", true
        ));
    }
}
