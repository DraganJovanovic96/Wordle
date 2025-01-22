package com.wordle.service.impl;

import com.wordle.dto.*;
import com.wordle.enumeration.CharacterValue;
import com.wordle.enumeration.GameStatus;
import com.wordle.mapper.GameMapper;
import com.wordle.mapper.GameOverMapper;
import com.wordle.model.Game;
import com.wordle.model.GuessedWord;
import com.wordle.model.Player;
import com.wordle.model.SecondaryWord;
import com.wordle.repository.*;
import com.wordle.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final PlayerRepository playerRepository;

    private final PrimaryWordRepository primaryWordRepository;

    private final SecondaryWordRepository secondaryWordRepository;

    private final GuessedWordRepository guessedWordRepository;

    private final GameMapper gameMapper;

    private final GameOverMapper gameOverMapper;

    @Override
    public GameDto createGame(PlayerIdDto playerIdDto) {
        Player player;

        if (!Objects.nonNull(playerIdDto.getPlayerId())) {
            player = new Player();
            playerRepository.save(player);

            playerIdDto.setPlayerId(player.getId());
        } else {
            player = playerRepository.findOneById(playerIdDto.getPlayerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player with this ID doesn't exist"));
        }

        Optional<Game> gameOptional = gameRepository.findOneByPlayerIdAndGameStatus(playerIdDto.getPlayerId(), GameStatus.IN_PROGRESS);

        if (gameOptional.isPresent()) {

            return gameMapper.gameToGameDto(gameOptional.get());
        } else {
            Game game = new Game();
            game.setGameStatus(GameStatus.IN_PROGRESS);
            game.setPlayer(player);
            game.setNumberOfTries(0);
            game.setCorrectWord(primaryWordRepository.findRandomWord());
            gameRepository.save(game);

            return gameMapper.gameToGameDto(game);
        }
    }

    @Override
    public GameResponse submitGuess(SubmitGuessDto submitGuessDto) {

        if (submitGuessDto.getGuessedWord().length() != 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Word is not 5 characters long"));
        }

        if (!Objects.nonNull(submitGuessDto.getPlayerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("User can not be null"));
        }

        playerRepository.findById(submitGuessDto.getPlayerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Player not found ")));

        Game game = gameRepository.findOneByPlayerIdAndGameStatus(submitGuessDto.getPlayerId(), GameStatus.IN_PROGRESS)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Game not found ")));

        SecondaryWord secondaryWord = secondaryWordRepository.findByStringOfWordIgnoreCase(submitGuessDto.getGuessedWord())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Word not found"));

        if (game.getGuessedWords().stream().anyMatch(gw -> gw.getGuessedWord().equals(secondaryWord))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This word was already submitted");
        }

        Map<Integer, CharacterValue> characterValueMap = checkCharacterValue(game.getCorrectWord().getStringOfWord(), submitGuessDto.getGuessedWord());

        if (checkGameStatus(game, submitGuessDto.getGuessedWord(), secondaryWord, characterValueMap)) {
            return gameOverMapper.gameToGameOverDto(game);
        }

        game.setNumberOfTries(game.getNumberOfTries() + 1);

        setGuessedWords(game, secondaryWord, characterValueMap);

        return gameMapper.gameToGameDto(game);
    }

    @Override
    public Map<Integer, CharacterValue> checkCharacterValue(String primaryWord, String secondaryWord) {
        Map<Integer, CharacterValue> positionToCharacterValueMap = new HashMap<>();
        Map<Character, Integer> correctWordCharacterFrequencyMap = new LinkedHashMap<>();
        Set<Integer> processedPositions = new HashSet<>(); // Track positions already processed

        primaryWord = primaryWord.toLowerCase();
        secondaryWord = secondaryWord.toLowerCase();

        // Build frequency map for characters in the primary word
        for (int i = 0; i < primaryWord.length(); i++) {
            correctWordCharacterFrequencyMap.put(primaryWord.charAt(i),
                    correctWordCharacterFrequencyMap.getOrDefault(primaryWord.charAt(i), 0) + 1);
        }

        // First pass: Check for CORRECT matches
        for (int i = 0; i < secondaryWord.length(); i++) {
            if (secondaryWord.charAt(i) == primaryWord.charAt(i)) {
                positionToCharacterValueMap.put(i, CharacterValue.CORRECT);
                processedPositions.add(i); // Mark position as processed
                correctWordCharacterFrequencyMap.put(primaryWord.charAt(i),
                        correctWordCharacterFrequencyMap.get(primaryWord.charAt(i)) - 1);
            }
        }

        // Second pass: Check for PRESENT_BUT_MISPLACED matches
        for (int i = 0; i < secondaryWord.length(); i++) {
            if (processedPositions.contains(i)) {
                continue; // Skip already processed positions
            }

            char currentChar = secondaryWord.charAt(i);
            if (primaryWord.contains(String.valueOf(currentChar)) &&
                    correctWordCharacterFrequencyMap.getOrDefault(currentChar, 0) > 0) {
                positionToCharacterValueMap.put(i, CharacterValue.PRESENT_BUT_MISPLACED);
                correctWordCharacterFrequencyMap.put(currentChar,
                        correctWordCharacterFrequencyMap.get(currentChar) - 1);
            } else {
                positionToCharacterValueMap.put(i, CharacterValue.NOT_PRESENT);
            }
        }

        return positionToCharacterValueMap;
    }

    public WinRateDto winRate(UUID playerId) {
        long wins = gameRepository.countByPlayerIdAndGameStatus(playerId, GameStatus.WIN);
        long losses = gameRepository.countByPlayerIdAndGameStatus(playerId, GameStatus.LOSE);
        long totalGames = wins + losses;

        double winRate = (totalGames == 0) ? 0.0 : ((double) wins / totalGames) * 100;

        DecimalFormat df = new DecimalFormat("#.##");
        winRate = Double.parseDouble(df.format(winRate));

        WinRateDto winRateDto = new WinRateDto();
        winRateDto.setNumberOfWins(wins);
        winRateDto.setNumberOfLoses(losses);
        winRateDto.setWinRate(winRate);

        return winRateDto;
    }

    public Boolean checkGameStatus(Game game, String guessedWord, SecondaryWord secondaryWord, Map<Integer, CharacterValue> characterValueMap) {
        if (game.getNumberOfTries() == 5 && !game.getCorrectWord().getStringOfWord().equalsIgnoreCase(guessedWord)) {
            game.setGameStatus(GameStatus.LOSE);
            game.setNumberOfTries(6);
            setGuessedWords(game, secondaryWord, characterValueMap);
            gameRepository.save(game);

            return true;
        }

        if (game.getGameStatus().equals(GameStatus.IN_PROGRESS) && game.getCorrectWord().getStringOfWord().equalsIgnoreCase(guessedWord)) {
            game.setGameStatus(GameStatus.WIN);
            game.setNumberOfTries(game.getNumberOfTries() + 1);
            setGuessedWords(game, secondaryWord, characterValueMap);
            gameRepository.save(game);

            return true;
        }

        return false;
    }

    private void setGuessedWords(Game game, SecondaryWord secondaryWord, Map<Integer, CharacterValue> characterValueMap) {
        GuessedWord guessedWord = new GuessedWord();
        guessedWord.setGame(game);
        guessedWord.setGuessedWord(secondaryWord);
        guessedWord.setCharacters(characterValueMap);

        game.getGuessedWords().add(guessedWord);

        guessedWordRepository.save(guessedWord);
    }

}
