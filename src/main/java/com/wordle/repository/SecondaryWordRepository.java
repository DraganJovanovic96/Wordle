package com.wordle.repository;

import com.wordle.model.SecondaryWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecondaryWordRepository extends JpaRepository<SecondaryWord, UUID> {

    Optional<SecondaryWord> findByStringOfWord(String stringOfWord);
}
