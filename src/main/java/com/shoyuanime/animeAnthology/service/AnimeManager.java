package com.shoyuanime.animeAnthology.service;

import com.shoyuanime.animeAnthology.dto.AnimeDTO;
import com.shoyuanime.animeAnthology.mapper.AnimeMapper;
import com.shoyuanime.animeAnthology.model.Anime;
import com.shoyuanime.animeAnthology.model.Levels;
import com.shoyuanime.animeAnthology.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
public class AnimeManager {

    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeMapper mapper;

    public Optional<AnimeDTO> createAnime(@Valid AnimeDTO animeDTO) {
        if (animeDTO.getId() != null && animeRepository.existsById(animeDTO.getId())) {
            return Optional.empty();
        }
        Anime newAnime = mapper.map(animeDTO);
        if (newAnime.getLevels() == null) {
            newAnime.setLevels(new Levels());
        }
        return Optional.of(mapper.map(animeRepository.save(newAnime)));
    }

    public Optional<AnimeDTO> updateAnime(@Valid AnimeDTO animeDTO) {
        Optional<Anime> existingAnime = animeRepository.findById(animeDTO.getId());
        if (existingAnime.isPresent()) {
            // levels.id isn't mapped, so we need to store it early
            Long levelsId = existingAnime.get().getLevels().getId();
            Anime a = mapper.map(animeDTO, existingAnime.get());
            a.getLevels().setId(levelsId);
            Anime updatedAnime = animeRepository.save(a);
            return Optional.of(mapper.map(updatedAnime));
        } else {
            return Optional.empty();
        }
    }

    public Optional<AnimeDTO> getAnime(Long id) {
        Optional<Anime> foundAnime = animeRepository.findById(id);
        if (foundAnime.isPresent()) {
            return Optional.of(mapper.map(foundAnime.get()));
        }
        return Optional.empty();
    }

    public String deleteAnime(Long id) {
        if (animeRepository.existsById(id)) {
            animeRepository.deleteById(id);
            return "Anime with id " + id + " deleted.";
        }
        return "";
    }
}
