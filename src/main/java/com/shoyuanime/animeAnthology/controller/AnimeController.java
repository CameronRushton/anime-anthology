package com.shoyuanime.animeAnthology.controller;

import com.shoyuanime.animeAnthology.dto.AnimeDTO;
import com.shoyuanime.animeAnthology.service.AnimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AnimeController {

    private AnimeManager animeManager;

    @Autowired
    public AnimeController(AnimeManager manager) {
        animeManager = manager;
    }

    /**
     * GET api/v0/anime/{id}
     */
    @GetMapping(path="api/v0/anime/{id}")
    public ResponseEntity queryAnimeById(@PathVariable("id") Long animeId) {
        Optional<AnimeDTO> anime = animeManager.getAnime(animeId);
        return ResponseEntity.status(anime.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(anime.orElse(null));
    }

    /**
     * POST api/v0/anime
     */
    @PostMapping(path="api/v0/admin/anime")
    public ResponseEntity createAnime(@RequestBody AnimeDTO dto) {
        Optional<AnimeDTO> response = animeManager.createAnime(dto);
        if (response.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Anime already exists with id " + dto.getId());
    }

    /**
     * DELETE api/v0/anime/{id}
     */
    @DeleteMapping(path="api/v0/admin/anime/{id}")
    public ResponseEntity deleteAnime(@PathVariable("id") Long animeId) {
        String message = animeManager.deleteAnime(animeId);
        if (message.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * PUT api/v0/admin/anime
     */
    @PutMapping
    public ResponseEntity updateAnime(@RequestBody AnimeDTO dto) {
        Optional<AnimeDTO> response = animeManager.updateAnime(dto);
        if (response.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
