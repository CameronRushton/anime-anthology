package com.shoyuanime.animeAnthology.controller;

import com.shoyuanime.animeAnthology.mapper.AnimeMapper;
import com.shoyuanime.animeAnthology.model.Anime;
import com.shoyuanime.animeAnthology.model.Level;
import com.shoyuanime.animeAnthology.repository.AnimeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.shoyuanime.animeAnthology.util.JsonMapper.toJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AnimeMapper animeMapper;

    @MockBean
    private AnimeRepository animeRepository;

    @Test
    public void testGetAnimeById() throws Exception {
        Anime anime = Anime.builder().animeId(1L).build();
        when(animeRepository.findById(anime.getAnimeId())).thenReturn(Optional.of(anime));
        mockMvc.perform(get("/api/v0/anime/{id}", anime.getAnimeId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAnimeByIdNotFound() throws Exception {
        Anime anime = Anime.builder().animeId(1L).build();
        when(animeRepository.findById(anime.getAnimeId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v0/anime/{id}", anime.getAnimeId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAnime() throws Exception {
        Anime anime = Anime.builder().animeId(1L).levels(new Level()).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(false);
        when(animeRepository.save(anime)).thenReturn(anime);
        mockMvc.perform(post("/api/v0/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateAnimeAlreadyExists() throws Exception {
        Anime anime = Anime.builder().animeId(1L).levels(new Level()).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(true);
        mockMvc.perform(post("/api/v0/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateAnime() throws Exception {
        Anime anime = Anime.builder().animeId(1L).levels(Level.builder().id(1L).beginner(10L).build()).build();
        Anime updatedAnime = Anime.builder().animeId(1L).levels(Level.builder().beginner(50L).build()).build();
        when(animeRepository.findById(anime.getAnimeId())).thenReturn(Optional.of(anime));
        when(animeRepository.save(anime)).thenReturn(updatedAnime);
        mockMvc.perform(put("/api/v0/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.levels.beginner").value(50L));
    }

    @Test
    public void testUpdateAnimeDoesNotExist() throws Exception {
        Anime anime = Anime.builder().animeId(1L).levels(Level.builder().id(1L).beginner(10L).build()).build();
        when(animeRepository.findById(anime.getAnimeId())).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/v0/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAnime() throws Exception {
        Anime anime = Anime.builder().animeId(1L).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(true);
        mockMvc.perform(delete("/api/v0/anime/{id}", anime.getAnimeId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAnimeNotFound() throws Exception {
        Anime anime = Anime.builder().animeId(1L).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(false);
        mockMvc.perform(delete("/api/v0/anime/{id}", anime.getAnimeId()))
                .andExpect(status().isNotFound());
    }
}
