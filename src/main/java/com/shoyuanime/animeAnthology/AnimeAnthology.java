package com.shoyuanime.animeAnthology;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AnimeAnthology {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AnimeAnthology.class)
                .headless(false)
                .run(args);
    }
}
