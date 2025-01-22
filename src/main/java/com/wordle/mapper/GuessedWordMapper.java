package com.wordle.mapper;

import com.wordle.dto.GuessedWordDto;
import com.wordle.model.GuessedWord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GuessedWordMapper {
    @Mapping(target = "guessedWord", source = "guessedWord.stringOfWord")
    @Mapping(target = "characters", source = "characters")
    GuessedWordDto guessedWordToGuessedWordDto(GuessedWord guessedWord);
}
