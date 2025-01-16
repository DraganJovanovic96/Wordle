package com.wordle.controller;

import com.wordle.dto.PlayerIdDto;
import com.wordle.dto.GameDto;
import com.wordle.dto.GameResponse;
import com.wordle.dto.SubmitGuessDto;
import com.wordle.service.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping(value = "/start",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Start a game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game successfully started.", response = GameDto.class)
    })
    public ResponseEntity<GameDto> startGame(@RequestBody(required = false) PlayerIdDto playerIdDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(gameService.createGame(playerIdDto));
    }

    @PutMapping(value = "/submit-guess", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Submit a guess")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully submitted a guess.", response = GameDto.class),
            @ApiResponse(code = 400, message = "User not found"),
            @ApiResponse(code = 400, message = "Word is not 5 letters long"),
            @ApiResponse(code = 404, message = "That word isn't in our database."),
            @ApiResponse(code = 409, message = "Word is already used")
    })
    public ResponseEntity<GameResponse> submitGuess(@Valid @RequestBody SubmitGuessDto submitGuessDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(gameService.submitGuess(submitGuessDto));
    }
}
