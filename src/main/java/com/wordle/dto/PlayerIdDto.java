package com.wordle.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class PlayerIdDto {

    private UUID playerId;
}
