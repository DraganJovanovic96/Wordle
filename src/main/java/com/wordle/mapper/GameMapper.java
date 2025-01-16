package com.wordle.mapper;

import com.wordle.dto.GameDto;
import com.wordle.model.Game;
import com.wordle.model.GuessedWord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GameMapper is a mapper interface that defines mapping methods between {@link Game} and{@link GameDto}
 * classes using MapStruct library. It also enables list to list mapping.
 *
 * @author Dragan Jovanovic
 * @version 1.0
 * @since 1.0
 */
@Mapper(uses = GuessedWordMapper.class)
public interface GameMapper {
    /**
     * Maps a Game object to a GameDto object.
     *
     * @param game the Game object to be mapped to a GameDto object
     * @return a GameDto object containing the game's information
     */
    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "guessedWords", target = "guessedWordsDto")
    GameDto gameToGameDto(Game game);
}

