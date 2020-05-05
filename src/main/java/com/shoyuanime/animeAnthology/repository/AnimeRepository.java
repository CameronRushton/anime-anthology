package com.shoyuanime.animeAnthology.repository;

import com.shoyuanime.animeAnthology.model.Anime;
import org.springframework.data.repository.CrudRepository;

public interface AnimeRepository extends CrudRepository<Anime, Long> {
}
