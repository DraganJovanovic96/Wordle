package com.wordle.model;

import com.wordle.enumeration.CharacterValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guessed_words")
public class GuessedWord extends BaseEntity {

    @ManyToOne
    private Game game;

    @ManyToOne
    private SecondaryWord guessedWord;

    @ElementCollection
    private Map<Integer, CharacterValue> characters = new LinkedHashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuessedWord that = (GuessedWord) o;
        return Objects.equals(game, that.game) &&
                Objects.equals(guessedWord, that.guessedWord);
    }
}
