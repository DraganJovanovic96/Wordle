package com.wordle.repository;

import com.wordle.model.GuessedWord;
import com.wordle.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuessedWordRepository extends JpaRepository<GuessedWord, UUID> {
}
