package com.wordle.mapper;

import com.wordle.dto.GameOverDto;
import com.wordle.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = GuessedWordMapper.class)
public interface GameOverMapper {
    /**
     * Maps a Game object to a GameOverDto object.
     *
     * @param game the Game object to be mapped to a GameOverDto object
     * @return a GameOverDto object containing the game's information
     */
    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "correctWord.stringOfWord", target = "correctWord")
    @Mapping(source = "guessedWords", target = "guessedWordsDto")
    GameOverDto gameToGameOverDto(Game game);
}
