package com.shoyuanime.animeAnthology.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class AuthenticationResponse {
    private final String jwt;
}
