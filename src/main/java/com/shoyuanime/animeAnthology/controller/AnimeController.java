package com.shoyuanime.animeAnthology.controller;

import com.shoyuanime.animeAnthology.dto.AnimeDTO;
import com.shoyuanime.animeAnthology.model.Anime;
import com.shoyuanime.animeAnthology.repository.AnimeRepository;
import com.shoyuanime.animeAnthology.service.AnimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v0/anime")
public class AnimeController {

    private AnimeRepository animeRepository;
    private AnimeManager animeManager;

    @Autowired
    public AnimeController(AnimeRepository repo, AnimeManager manager) {
        animeRepository = repo;
        animeManager = manager;
    }

    /**
     * GET /anime/{id}
     */
    @GetMapping(path="/{id}")
    public ResponseEntity queryAnimeById(@PathVariable("id") Long animeId) {
        Optional<Anime> anime = animeRepository.findById(animeId);
        return ResponseEntity.status(anime.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(anime.orElse(null));
    }

    /**
     * POST /anime
     */
    @PostMapping
    public ResponseEntity createAnime(@RequestBody AnimeDTO dto) {
        Optional<AnimeDTO> response = animeManager.createAnime(dto);
        if (response.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Anime already exists with id " + dto.getId());
    }

    /**
     * DELETE /anime/{id}
     */
    @DeleteMapping(path="/{id}")
    public ResponseEntity deleteAnime(@PathVariable("id") Long animeId) {
        if (!animeRepository.existsById(animeId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        animeRepository.deleteById(animeId);
        return ResponseEntity.status(HttpStatus.OK).body("Anime with id " + animeId + " deleted.");
    }

    /**
     * PUT /anime
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
