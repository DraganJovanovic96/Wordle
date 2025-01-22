package com.wordle.service;

import com.wordle.dto.*;
import com.wordle.enumeration.CharacterValue;

import java.util.Map;
import java.util.UUID;

public interface GameService {

    GameDto createGame(PlayerIdDto playerIdDto);

    GameResponse submitGuess(SubmitGuessDto submitGuessDto);

    Map<Integer, CharacterValue> checkCharacterValue(String primaryWord, String secondaryWord);

    WinRateDto winRate(UUID playerId);
}
