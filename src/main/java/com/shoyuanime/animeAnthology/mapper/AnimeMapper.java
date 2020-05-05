package com.shoyuanime.animeAnthology.mapper;

import com.shoyuanime.animeAnthology.dto.AnimeDTO;
import com.shoyuanime.animeAnthology.model.Anime;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AnimeMapper {

    @Mapping(source = "animeId", target = "id")
    AnimeDTO map(Anime myAnime);

    @InheritInverseConfiguration
    Anime map(AnimeDTO myAnimeDTO);

    @InheritInverseConfiguration
    Anime map(AnimeDTO myAnimeDTO, @MappingTarget Anime myAnime);
}
