package com.example.pingpong.service;

import com.example.pingpong.model.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameState gameState = new GameState();
    
    // Physics constants
    private double ballDX = 4;
    private double ballDY = 4;
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 400;
    private final int PADDLE_HEIGHT = 100;
    private final int BALL_SIZE = 10;

    @Autowired
    private SimpMessagingTemplate template;

    // Game loop running every 30ms (~33 FPS)
    @Scheduled(fixedRate = 30)
    public void gameLoop() {
        // Update Ball Position
        gameState.setBallX((int) (gameState.getBallX() + ballDX));
        gameState.setBallY((int) (gameState.getBallY() + ballDY));

        // Wall Collision (Top/Bottom)
        if (gameState.getBallY() <= 0 || gameState.getBallY() >= CANVAS_HEIGHT - BALL_SIZE) {
            ballDY = -ballDY;
        }

        // Paddle Collision (Simple AABB)
        // Left Paddle
        if (gameState.getBallX() <= 20 && 
            gameState.getBallY() + BALL_SIZE >= gameState.getPaddle1Y() && 
            gameState.getBallY() <= gameState.getPaddle1Y() + PADDLE_HEIGHT) {
            ballDX = Math.abs(ballDX); // Bounce right
        }
        
        // Right Paddle
        if (gameState.getBallX() >= CANVAS_WIDTH - 30 && 
            gameState.getBallY() + BALL_SIZE >= gameState.getPaddle2Y() && 
            gameState.getBallY() <= gameState.getPaddle2Y() + PADDLE_HEIGHT) {
            ballDX = -Math.abs(ballDX); // Bounce left
        }

        // Scoring / Reset
        if (gameState.getBallX() < 0) {
            gameState.setScore2(gameState.getScore2() + 1);
            resetBall();
        } else if (gameState.getBallX() > CANVAS_WIDTH) {
            gameState.setScore1(gameState.getScore1() + 1);
            resetBall();
        }

        // Broadcast state to all subscribers
        template.convertAndSend("/topic/game", gameState);
    }

    private void resetBall() {
        gameState.setBallX(CANVAS_WIDTH / 2);
        gameState.setBallY(CANVAS_HEIGHT / 2);
        // Flip direction
        ballDX = -ballDX; 
    }

    public void movePaddle(String player, String direction) {
        int speed = 15;
        if ("player1".equals(player)) {
            if ("UP".equals(direction)) gameState.setPaddle1Y(Math.max(0, gameState.getPaddle1Y() - speed));
            if ("DOWN".equals(direction)) gameState.setPaddle1Y(Math.min(CANVAS_HEIGHT - PADDLE_HEIGHT, gameState.getPaddle1Y() + speed));
        } else if ("player2".equals(player)) {
            if ("UP".equals(direction)) gameState.setPaddle2Y(Math.max(0, gameState.getPaddle2Y() - speed));
            if ("DOWN".equals(direction)) gameState.setPaddle2Y(Math.min(CANVAS_HEIGHT - PADDLE_HEIGHT, gameState.getPaddle2Y() + speed));
        }
    }
}