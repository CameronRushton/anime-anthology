package com.shoyuanime.animeAnthology.dto;

import com.shoyuanime.animeAnthology.model.Levels;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
public class AnimeDTO {
    @Id
    @NotNull
    private Long id;
    private Levels levels;
}
