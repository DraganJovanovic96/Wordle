package com.wordle.repository;

import com.wordle.model.PrimaryWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrimaryWordRepository extends JpaRepository<PrimaryWord, UUID> {

    @Query(value = "SELECT * FROM primary_words OFFSET FLOOR(RANDOM() * (SELECT COUNT(*) FROM primary_words)) LIMIT 1", nativeQuery = true)
    PrimaryWord findRandomWord();
}
