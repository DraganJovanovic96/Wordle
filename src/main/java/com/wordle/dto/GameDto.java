package com.wordle.dto;

import com.wordle.enumeration.CharacterValue;
import com.wordle.enumeration.GameStatus;
import com.wordle.model.GuessedWord;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class GameDto implements GameResponse {
    /**
     * The status of the game.
     */
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    private int numberOfTries;

    private UUID playerId;

    private List<GuessedWordDto> guessedWordsDto;
}
