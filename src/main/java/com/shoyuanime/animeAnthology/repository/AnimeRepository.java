package com.shoyuanime.animeAnthology.repository;

import com.shoyuanime.animeAnthology.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> { // TODO: Can this be CrudRepository?
    @Query(value = "select a from Anime a JOIN a.series s WHERE s = LOWER(:id)") // TODO: Is LOWER() needed?
    List<Anime> getAllBySeries(@Param("id") Long animeId);
}
