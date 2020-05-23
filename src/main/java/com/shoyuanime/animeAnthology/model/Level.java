package com.shoyuanime.animeAnthology.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Level {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Min(value = 0, message = "Number of votes must be greater or equal to zero.")
    private Long beginner = 0L;
    @Min(value = 0, message = "Number of votes must be greater or equal to zero.")
    private Long novice = 0L;
    @Min(value = 0, message = "Number of votes must be greater or equal to zero.")
    private Long intermediate = 0L;
    @Min(value = 0, message = "Number of votes must be greater or equal to zero.")
    private Long advanced = 0L;
    @Min(value = 0, message = "Number of votes must be greater or equal to zero.")
    private Long expert = 0L;
}
