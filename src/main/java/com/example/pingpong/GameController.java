package com.example.pingpong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.example.pingpong.model.InputMessage;
import com.example.pingpong.service.GameService;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @MessageMapping("/moves")
    public void move(InputMessage message) {
        gameService.movePaddle(message.getPlayer(), message.getDirection());
    }
}