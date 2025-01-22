package com.wordle.controller;

import com.wordle.dto.PlayerIdDto;
import com.wordle.dto.WinRateDto;
import com.wordle.service.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/win-rate")
@RequiredArgsConstructor
public class WinRateController {
    private final GameService gameService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get player's win-rate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Player's win-rate successfully fetched.", response = WinRateDto.class)
    })
    public ResponseEntity<WinRateDto> getWinRate(@RequestBody PlayerIdDto playerIdDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(gameService.winRate(playerIdDto.getPlayerId()));
    }
}
