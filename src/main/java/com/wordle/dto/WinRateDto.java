package com.wordle.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WinRateDto {
    private Double winRate;

    private long numberOfWins;

    private long numberOfLoses;
}
