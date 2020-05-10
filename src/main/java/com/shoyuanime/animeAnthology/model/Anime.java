package com.shoyuanime.animeAnthology.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Anime {
    @Id
    @Column(name = "anime_id")
    private Long animeId;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Levels levels;
}
