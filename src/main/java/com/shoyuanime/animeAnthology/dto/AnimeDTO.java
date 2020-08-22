package com.shoyuanime.animeAnthology.dto;

import com.shoyuanime.animeAnthology.model.Level;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@ToString
public class AnimeDTO {
    @Id
    @NotNull
    private Long id;
    private Level levels;
    private List<Long> series; // The other season IDs of the main anime.
    private List<Long> related; // The related anime IDs.
}
