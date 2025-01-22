package com.wordle.dto;

import com.wordle.enumeration.GameStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public interface GameResponse {
    /**
     * The status of the game.
     */
    @Enumerated(EnumType.STRING)
    GameStatus getGameStatus();

    int getNumberOfTries();

    UUID getPlayerId();

    List<GuessedWordDto> getGuessedWordsDto();
}