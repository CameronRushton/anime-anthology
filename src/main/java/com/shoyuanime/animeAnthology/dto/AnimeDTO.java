package com.shoyuanime.animeAnthology.dto;

import com.shoyuanime.animeAnthology.model.Level;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@ToString
public class AnimeDTO {
    @Id
    @NotNull
    private String id;
    private Level levels;
    private Set<String> series; // The other season IDs of the main anime.
    private Set<String> related; // The related anime IDs.
}
