let stompClient = null;
let game = null;
let player = null;


const sendMessage = (message) => {
    stompClient.send(`/app/${message.type}`, {}, JSON.stringify(message));
}


const makeMove = (move) => {
    sendMessage({
        type: "game.move",
        move: move,
        turn: game.turn,
        sender: player,
        gameId: game.gameId
    });
}


const messagesTypes = {
    "game.join": (message) => {
        updateGame(message);
    },
    "game.gameOver": (message) => {
        updateGame(message);
        if (message.gameState === 'TIE') toastr.success(`Game over! It's a tie!`);
        else showWinner(message.winner);
    },
    "game.joined": (message) => {
        if (game !== null && game.gameId !== message.gameId) return;
        const playerName = localStorage.getItem("playerName");
        player = playerName
        updateGame(message);

        const socket = new SockJS('https://'+ipAddress+'/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe(`/topic/game.${message.gameId}`, function (message) {
                handleMessage(JSON.parse(message.body));
            });
        });
    },
    "game.move": (message) => {
        updateGame(message);
    },
    "game.left": (message) => {
        updateGame(message);
        if (message.winner) showWinner(message.winner);
    },
    "error": (message) => {
        toastr.error(message.content);
    }
}


const handleMessage = (message) => {
    if (messagesTypes[message.type])
        messagesTypes[message.type](message);
}


const messageToGame = (message) => {
    return {
        gameId: message.gameId,
        board: message.board,
        turn: message.turn,
        player1: message.player1,
        player2: message.player2,
        gameState: message.gameState,
        winner: message.winner
    }
}


const showWinner = (winner) => {
    toastr.success(`The winner is ${winner}!`);
    const winningPositions = getWinnerPositions(game.board);
    if (winningPositions && winningPositions.length === 3) {
        winningPositions.forEach(position => {
            const row = Math.floor(position / 3);
            const cell = position % 3;
            let cellElement = document.querySelector(`.row-${row} .cell-${cell} span`);
            cellElement.style.backgroundColor = '#b3e6ff';
        });
    }
}


const joinGame = async () => {
    try {
        // Fetch the username from the /user endpoint
        const response = await fetch('/user');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const playerName = await response.text();
        console.log("PLAYER: " + playerName)
        // Store the username in localStorage
        localStorage.setItem("playerName", playerName);

        // Send a message to join the game
        sendMessage({
            type: "game.join",
            player: playerName
        });
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
};




const connect = () => {
    const socket = new SockJS('https://'+ipAddress+'/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/game.state', function (message) {
            handleMessage(JSON.parse(message.body));
        });
        loadGame();
    });
}

const loadGame = () => {
    const playerName = localStorage.getItem("playerName");
    if (playerName) {
        sendMessage({
            type: "game.join",
            player: playerName
        });
    } else {
        joinGame();
    }
}


const updateGame = (message) => {
    game = messageToGame(message);
    updateBoard(message.board);
    document.getElementById("player1").innerHTML = game.player1;
    document.getElementById("player2").innerHTML = game.player2 || (game.winner ? '-' : 'Waiting for player 2...');
    document.getElementById("turn").innerHTML = game.turn;
    document.getElementById("winner").innerHTML = game.winner || '-';

    const player1Image = document.getElementById("player1Image");
    const player2Image = document.getElementById("player2Image");

    if (game.player1) {
        player1Image.src = `https://${s3Bucket}.s3.amazonaws.com/${window.hashString(game.player1)}.jpg`;
    }
    if (game.player2) {
        player2Image.src = `https://${s3Bucket}.s3.amazonaws.com/${window.hashString(game.player2)}.jpg`;
    }
}


const updateBoard = (board) => {
    let counter = 0;
    board.forEach((row, rowIndex) => {
        row.forEach((cell, cellIndex) => {
            const cellElement = document.querySelector(`.row-${rowIndex} .cell-${cellIndex}`);
            cellElement.innerHTML = cell === ' ' ? '<button onclick="makeMove(' + counter + ')"> </button>' : `<span class="cell-item">${cell}</span>`;
            counter++;
        });
    });
}


const getWinnerPositions = (board) => {
    const winnerPositions = [];

    for (let i = 0; i < 3; i++) {
        if (board[i][0] === board[i][1] && board[i][1] === board[i][2] && board[i][0] !== ' ') {
            winnerPositions.push(i * 3);
            winnerPositions.push(i * 3 + 1);
            winnerPositions.push(i * 3 + 2);
        }
    }

    for (let i = 0; i < 3; i++) {
        if (board[0][i] === board[1][i] && board[1][i] === board[2][i] && board[0][i] !== ' ') {
            winnerPositions.push(i);
            winnerPositions.push(i + 3);
            winnerPositions.push(i + 6);
        }
    }

    if (board[0][0] === board[1][1] && board[1][1] === board[2][2] && board[0][0] !== ' ') {
        winnerPositions.push(0);
        winnerPositions.push(4);
        winnerPositions.push(8);
    }

    return winnerPositions;
}

window.onload = function () {
    connect();
}
