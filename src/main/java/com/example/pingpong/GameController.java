package com.example.pingpong.controller;

import com.example.pingpong.model.InputMessage;
import com.example.pingpong.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @MessageMapping("/move")
    public void move(InputMessage message) {
        gameService.movePaddle(message.getPlayer(), message.getDirection());
    }
}