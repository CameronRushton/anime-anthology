package com.shoyuanime.animeAnthology.service;

import com.shoyuanime.animeAnthology.dto.AnimeDTO;
import com.shoyuanime.animeAnthology.mapper.AnimeMapper;
import com.shoyuanime.animeAnthology.model.Anime;
import com.shoyuanime.animeAnthology.model.Level;
import com.shoyuanime.animeAnthology.repository.AnimeRepository;
//import com.shoyuanime.animeAnthology.repository.SeriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@Slf4j
public class AnimeManager {

    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeMapper mapper;

    public Optional<AnimeDTO> createAnime(@Valid AnimeDTO animeDTO) {
        if (animeDTO.getId() != null && animeRepository.existsById(animeDTO.getId())) {
            return Optional.empty();
        }
        // Find all anime that already have this show listed as part of their series
        List<Anime> anime = animeRepository.getAllBySeries(animeDTO.getId());
        if (!anime.isEmpty()) {
            // TODO: Return a message with the anime that already have it listed
            return Optional.empty();
        }
        Anime newAnime = mapper.map(animeDTO);
        if (newAnime.getLevels() == null) {
            newAnime.setLevels(new Level());
        }
        log.trace("Creating anime with ID " + animeDTO.getId());
        return Optional.of(mapper.map(animeRepository.save(newAnime)));
    }

    public Optional<AnimeDTO> updateAnime(@Valid AnimeDTO animeDTO) {
        Optional<Anime> existingAnime = animeRepository.findById(animeDTO.getId());
        if (existingAnime.isPresent()) {
            // levels.id isn't mapped, so we need to store it early
            Long levelId = existingAnime.get().getLevels().getId();
            Anime a = mapper.map(animeDTO, new Anime());
            if (a.getLevels() != null) {
                a.getLevels().setId(levelId);
            } else {
                a.setLevels(existingAnime.get().getLevels());
            }
            if (a.getSeries() == null) a.setSeries(existingAnime.get().getSeries());
            if (a.getRelated() == null) a.setRelated(existingAnime.get().getRelated());
            Anime updatedAnime = animeRepository.save(a);
            return Optional.of(mapper.map(updatedAnime));
        } else {
            return Optional.empty();
        }
    }

    public Optional<AnimeDTO> getAnime(String id) {
        Optional<Anime> foundAnime = animeRepository.findById(id);
        if (foundAnime.isPresent()) {
            return Optional.of(mapper.map(foundAnime.get()));
        }
        List<Anime> foundAnimeBySeries = animeRepository.getAllBySeries(id);
        if (foundAnimeBySeries.size() > 1) {
            log.warn("Found multiple anime by series; expected one. IDs: {}", foundAnimeBySeries.stream().map(Anime::getAnimeId).collect(Collectors.toList()));
        }
        if (!foundAnimeBySeries.isEmpty()) {
            return Optional.of(mapper.map(foundAnimeBySeries.get(0)));
        }
        return Optional.empty();
    }

    public String deleteAnime(String id) {
        if (animeRepository.existsById(id)) {
            animeRepository.deleteById(id);
            return "Anime with id " + id + " deleted.";
        }
        return "";
    }

    public static String queryAnilist(String jsonGraphqlRequest) {
        String result = "";
        try {
            URL url = new URL("https://graphql.anilist.co");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonGraphqlRequest.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
