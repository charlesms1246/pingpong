var stompClient = null;
var canvas = document.getElementById("gameCanvas");
var ctx = canvas.getContext("2d");

// Game Objects
var paddleHeight = 100;
var paddleWidth = 10;
var ballSize = 10;

var gameState = {
    paddle1Y: 150,
    paddle2Y: 150,
    ballX: 300,
    ballY: 200,
    score1: 0,
    score2: 0
};

// Connect to WebSocket
function connect() {
    var socket = new SockJS('/pingpong-ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null; // Disable debug logs
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // Subscribe to game state updates
        stompClient.subscribe('/topic/game', function (message) {
            gameState = JSON.parse(message.body);
            draw();
        });
        
        // Hide controls after connecting
        document.getElementById("controls").style.display = "none";
        canvas.focus();
    });
}

// Send Movement
function sendMove(direction) {
    if(stompClient) {
        var player = document.getElementById("playerSelect").value;
        stompClient.send("/app/move", {}, JSON.stringify({
            'player': player,
            'direction': direction
        }));
    }
}

// Handle Keys
document.addEventListener("keydown", function(event) {
    var player = document.getElementById("playerSelect").value;
    
    // Player 1 controls (W/S)
    if (player === "player1") {
        if (event.key === "w" || event.key === "W") sendMove("UP");
        if (event.key === "s" || event.key === "S") sendMove("DOWN");
    }
    
    // Player 2 controls (Arrows)
    if (player === "player2") {
        if (event.key === "ArrowUp") sendMove("UP");
        if (event.key === "ArrowDown") sendMove("DOWN");
    }
});

// Render Game
function draw() {
    // Clear Canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Draw Net
    ctx.strokeStyle = "#FFF";
    ctx.setLineDash([10, 15]);
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2, 0);
    ctx.lineTo(canvas.width / 2, canvas.height);
    ctx.stroke();

    // Draw Paddles
    ctx.fillStyle = "#FFF";
    ctx.fillRect(10, gameState.paddle1Y, paddleWidth, paddleHeight); // Left
    ctx.fillRect(canvas.width - 20, gameState.paddle2Y, paddleWidth, paddleHeight); // Right

    // Draw Ball
    ctx.beginPath();
    ctx.arc(gameState.ballX, gameState.ballY, ballSize, 0, Math.PI * 2);
    ctx.fill();

    // Update Scores
    document.getElementById("score1").innerText = gameState.score1;
    document.getElementById("score2").innerText = gameState.score2;
}

// Initial draw
draw();