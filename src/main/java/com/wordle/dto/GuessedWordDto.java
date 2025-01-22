package com.wordle.dto;

import com.wordle.enumeration.CharacterValue;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class GuessedWordDto {
    private String guessedWord;
    
    private Map<Integer, CharacterValue> characters;
}
