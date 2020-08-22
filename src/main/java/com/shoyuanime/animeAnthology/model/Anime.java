package com.shoyuanime.animeAnthology.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
    @ElementCollection
    @JoinColumn(name = "anime_id")
    private List<Long> series; // The other season IDs of the main anime.
//    @ElementCollection
    // This field is for the case where the user asks for recommended anime series but doesn't give us
    private ArrayList<Long> related = new ArrayList<>(); // The related anime IDs.
}
