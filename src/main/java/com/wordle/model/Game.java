package com.wordle.model;

import com.wordle.enumeration.CharacterValue;
import com.wordle.enumeration.GameStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the Game entity.
 * It extends the {@link BaseEntity} class, which contains fields for creation
 * and update timestamps as well as a boolean flag for deletion status.
 *
 * @author Dragan Jovanovic
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    /**
     * The status of the game.
     */
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToOne
    private PrimaryWord correctWord;

    @Column
    private int numberOfTries;

    @ManyToOne
    private Player player;

    /**
     * The list of previous guesses made by the user in the game.
     */
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @OrderBy("createdAt ASC")
    private List<GuessedWord> guessedWords = new LinkedList<>();
}
