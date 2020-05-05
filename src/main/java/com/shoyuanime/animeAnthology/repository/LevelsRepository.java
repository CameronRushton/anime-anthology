package com.shoyuanime.animeAnthology.repository;

import com.shoyuanime.animeAnthology.model.Anime;
import org.springframework.data.repository.CrudRepository;

public interface LevelsRepository extends CrudRepository<Anime, Long> {
}
