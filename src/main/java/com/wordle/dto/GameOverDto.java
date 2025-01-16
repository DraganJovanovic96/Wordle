package com.wordle.dto;

import com.wordle.enumeration.GameStatus;
import com.wordle.model.GuessedWord;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class GameOverDto implements GameResponse {
    /**
     * The status of the game.
     */
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    private String correctWord;

    private int numberOfTries;

    private UUID playerId;

    private List<GuessedWordDto> guessedWordsDto;
}