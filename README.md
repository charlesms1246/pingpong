# Ping Pong Game

A real-time multiplayer Ping Pong game built with Spring Boot and WebSockets. Two players can compete against each other in a classic arcade-style ping pong match with server-side game physics and live score tracking.

## Features

- **Real-time Multiplayer**: Connect two players simultaneously using WebSocket (STOMP protocol)
- **Server-side Game Loop**: Game physics runs at ~33 FPS on the server for fair gameplay
- **Live Score Tracking**: Automatic score updates when a player misses the ball
- **Responsive Controls**: Smooth paddle movement with keyboard controls
- **Canvas-based UI**: Clean, simple HTML5 canvas-based game interface

## Technology Stack

- **Backend**:
  - Spring Boot 4.0.3
  - Java 21
  - WebSocket (STOMP over SockJS)
  - Spring Scheduling for game loop
  
- **Frontend**:
  - HTML5 Canvas
  - Vanilla JavaScript
  - SockJS Client
  - STOMP.js

## Prerequisites

- Java 21 or higher
- Maven 3.6+

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/charlesms1246/pingpong.git
cd pingpong
```

### 2. Build the Project

```bash
./mvnw clean package
```

Or on Windows:
```cmd
mvnw.cmd clean package
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

Or on Windows:
```cmd
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8081`

### 4. Play the Game

1. Open your browser and navigate to `http://localhost:8081`
2. Open a second browser window (or tab) to the same URL
3. In the first window, select "Player 1 (Left)" and click "Connect to Game"
4. In the second window, select "Player 2 (Right)" and click "Connect to Game"
5. Start playing!

## Controls

- **Player 1 (Left Paddle)**: 
  - `W` - Move up
  - `S` - Move down

- **Player 2 (Right Paddle)**:
  - `↑` (Arrow Up) - Move up
  - `↓` (Arrow Down) - Move down

## Game Rules

- The ball bounces off the top and bottom walls
- Each player controls a paddle on their side of the court
- Score a point when your opponent misses the ball
- The ball direction reverses after each point

## Project Structure

```
src/
├── main/
│   ├── java/com/example/pingpong/
│   │   ├── PingPongApplication.java    # Main application entry point
│   │   ├── GameController.java         # WebSocket message handler
│   │   ├── GameService.java            # Game logic and physics
│   │   ├── GameState.java              # Game state model
│   │   ├── InputMessage.java           # Player input model
│   │   └── WebSocketConfig.java        # WebSocket configuration
│   └── resources/
│       ├── static/
│       │   ├── index.html              # Game UI
│       │   ├── main.css                # Styles
│       │   └── main.js                 # Client-side game logic
│       └── application.properties       # App configuration
└── test/
    └── java/com/example/pingpong/
        └── PingpongApplicationTests.java
```

## Configuration

Server port and application name can be configured in `application.properties`:

```properties
spring.application.name=pingpong
server.port=8081
```

## How It Works

1. **WebSocket Connection**: Players connect via WebSocket using STOMP protocol
2. **Game Loop**: Server runs a scheduled task every 30ms to update game state
3. **Ball Physics**: Server calculates ball position, collision detection, and scoring
4. **State Broadcasting**: Updated game state is broadcast to all connected clients
5. **Player Input**: Clients send paddle movement commands to the server

## Building for Production

```bash
./mvnw clean package
java -jar target/pingpong-0.0.1-SNAPSHOT.jar
```

## Development

To run in development mode with auto-reload:

```bash
./mvnw spring-boot:run
```

Spring DevTools is included for hot reload during development.

## Testing

Run the test suite:

```bash
./mvnw test
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is a demo application for educational purposes.

## Acknowledgments

- Built with Spring Boot
- Uses SockJS and STOMP for WebSocket communication
- Classic Pong game mechanics

---

**Enjoy the game! 🏓**
