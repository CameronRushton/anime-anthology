package com.shoyuanime.animeAnthology.repository;

import com.shoyuanime.animeAnthology.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    boolean existsByUsername(String username);
}
