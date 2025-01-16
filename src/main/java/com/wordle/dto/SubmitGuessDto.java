package com.wordle.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class SubmitGuessDto {

    private UUID playerId;

    private String guessedWord;
}
