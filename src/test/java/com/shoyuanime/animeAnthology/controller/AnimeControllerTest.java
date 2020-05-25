package com.shoyuanime.animeAnthology.controller;

import com.shoyuanime.animeAnthology.mapper.AnimeMapper;
import com.shoyuanime.animeAnthology.model.Anime;
import com.shoyuanime.animeAnthology.model.Cover;
import com.shoyuanime.animeAnthology.model.Level;
import com.shoyuanime.animeAnthology.repository.AnimeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
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
    private AnimeMapper animeMapper;
    @Autowired
    private MockMvc mockMvc;

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
    @WithMockUser(username = "user1", password = "secret", roles = {"ADMIN"})
    public void testCreateAnime() throws Exception {
        Anime anime = Anime.builder().animeId(1L).levels(new Level()).coverUrl(new Cover()).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(false);
        when(animeRepository.getAllBySeries(anime.getAnimeId())).thenReturn(new ArrayList<>());
        when(animeRepository.save(anime)).thenReturn(anime);
        mockMvc.perform(post("/api/v0/admin/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user1", password = "secret", roles = {"ADMIN"})
    public void testCreateAnimeAlreadyExists() throws Exception {
        Anime anime = Anime.builder().animeId(1L).levels(new Level()).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(true);
        mockMvc.perform(post("/api/v0/admin/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "user1", password = "secret", roles = {"ADMIN"})
    public void testCreateAnimeAlreadyExistsInSeries() throws Exception {
        Anime anime = Anime.builder().animeId(1L).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(false);
        List<Anime> animeThatAlreadyExistAsSeries = new ArrayList<>();
        animeThatAlreadyExistAsSeries.add(new Anime());
        when(animeRepository.getAllBySeries(anime.getAnimeId())).thenReturn(animeThatAlreadyExistAsSeries);
        mockMvc.perform(post("/api/v0/admin/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "user1", password = "secret", roles = {"ADMIN"})
    public void testUpdateAnime() throws Exception {
        Anime anime = Anime.builder().animeId(1L).levels(Level.builder().id(1L).beginner(10L).build()).coverUrl(new Cover()).build();
        Anime updatedAnime = Anime.builder().animeId(1L).levels(Level.builder().beginner(50L).build()).coverUrl(new Cover()).build();
        when(animeRepository.findById(anime.getAnimeId())).thenReturn(Optional.of(anime));
        when(animeRepository.save(anime)).thenReturn(updatedAnime);
        mockMvc.perform(put("/api/v0/admin/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.levels.beginner").value(50L));
    }

    @Test
    @WithMockUser(username = "user1", password = "secret", roles = {"ADMIN"})
    public void testUpdateAnimeDoesNotExist() throws Exception {
        Anime anime = Anime.builder().animeId(1L).levels(Level.builder().id(1L).beginner(10L).build()).build();
        when(animeRepository.findById(anime.getAnimeId())).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/v0/admin/anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(animeMapper.map(anime))))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user1", password = "secret", roles = {"ADMIN"})
    public void testDeleteAnime() throws Exception {
        Anime anime = Anime.builder().animeId(1L).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(true);
        mockMvc.perform(delete("/api/v0/admin/anime/{id}", anime.getAnimeId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "secret", roles = {"ADMIN"})
    public void testDeleteAnimeNotFound() throws Exception {
        Anime anime = Anime.builder().animeId(1L).build();
        when(animeRepository.existsById(anime.getAnimeId())).thenReturn(false);
        mockMvc.perform(delete("/api/v0/admin/anime/{id}", anime.getAnimeId()))
                .andExpect(status().isNotFound());
    }
}
