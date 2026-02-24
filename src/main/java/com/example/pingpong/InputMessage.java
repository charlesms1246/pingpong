package com.example.pingpong.model;

import lombok.Data;

@Data
public class InputMessage {
    private String player; // "player1" or "player2"
    private String direction; // "UP" or "DOWN"
}