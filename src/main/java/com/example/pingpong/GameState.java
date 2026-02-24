package com.example.pingpong.model;

import lombok.Data;

@Data
public class GameState {
    private int paddle1Y = 150;
    private int paddle2Y = 150;
    private int ballX = 300;
    private int ballY = 200;
    private int score1 = 0;
    private int score2 = 0;
}