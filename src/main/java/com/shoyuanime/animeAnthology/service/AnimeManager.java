package com.shoyuanime.animeAnthology.service;

import com.shoyuanime.animeAnthology.dto.AnimeDTO;
import com.shoyuanime.animeAnthology.mapper.AnimeMapper;
import com.shoyuanime.animeAnthology.model.Anime;
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
        return Optional.of(mapper.map(animeRepository.save(newAnime)));
    }

    public Optional<AnimeDTO> updateAnime(@Valid AnimeDTO animeDTO) {
        Optional<Anime> existingAnime = animeRepository.findById(animeDTO.getId());
        if (existingAnime.isPresent()) {
            Long levelsId = existingAnime.get().getLevels().getId();
            Anime a = mapper.map(animeDTO, existingAnime.get());
            a.getLevels().setId(levelsId);
            Anime updatedAnime = animeRepository.save(a);
            return Optional.ofNullable(mapper.map(updatedAnime));
        } else {
            return Optional.empty();
        }
    }
}
