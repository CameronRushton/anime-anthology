package com.shoyuanime.animeAnthology;

import com.shoyuanime.animeAnthology.controller.AnimeController;
import com.shoyuanime.animeAnthology.dto.AnimeDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@Slf4j
public class AnimeAnthology {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("classpath:data/dbjson.json")
    Resource resourceFile;

    @Autowired
    AnimeController animeController;

    @EventListener(ContextRefreshedEvent.class)
    private void createDatabase() {
        if (false) { // TODO: Populate using sql file
            JSONParser jsonParser = new JSONParser();
            try {
                InputStream dbAsStream = resourceFile.getInputStream();
                JSONArray jsonObject = (JSONArray) jsonParser.parse(new InputStreamReader(dbAsStream, "UTF-8"));
                for (Object o : jsonObject) {
                    JSONObject anime = (JSONObject) o;
                    JSONArray series = (JSONArray) anime.get("series");
                    Set<String> seriesSet = new HashSet<>(series);
                    String animeId = anime.get("id").toString();
                    animeController.createAnime(AnimeDTO.builder().id(animeId).series(seriesSet).build());
                }
                log.debug("Finished populating database.");
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(AnimeAnthology.class)
                .headless(false)
                .run(args);
    }
}
