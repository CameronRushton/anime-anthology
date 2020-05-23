package com.shoyuanime.animeAnthology.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Level levels;
    @OneToOne(cascade = {CascadeType.ALL})
    private Cover coverUrl;
    private String bannerUrl = "";
//    @ElementCollection
    @ElementCollection
    @JoinColumn(name = "anime_id")
    private List<Long> series; // The other season IDs of the main anime.
//    @ElementCollection
    private ArrayList<Long> related = new ArrayList<>(); // The related anime IDs.
}
