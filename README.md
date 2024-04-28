# Tic Tac Toe LAN Game

## Overview
This project implements a classic Tic Tac Toe game designed to be played over a Local Area Network (LAN). It features a Java-based client-server architecture allowing two players to compete in a game of Tic Tac Toe from separate machines within the same network.

## Files
- `GameBoard.java`: Manages the game board's logic and state.
- `Player.java`: Defines player details and manages player actions.
- `TicTacToe.java`: Contains the game logic and initialization routines.
- `TicTacToeLAN.java`: Sets up the network connection and facilitates the LAN gameplay.
- `MyClient.java`: Handles the client-side logic to connect to the server and interact with the game.

## Setup
1. **Clone the Repository**:
   ```
   git clone https://github.com/4n4c0nd4RSA/tic-tac-toe.git
   ```
2. **Compile the Code**:
   Navigate to the downloaded directory and compile the Java files using:
   ```
   javac GameBoard.java Player.java TicTacToe.java TicTacToeLAN.java MyClient.java
   ```

## Usage
To start a game:
1. **Launch the Server and Client**:
   ```
   java TicTacToeLAN
   ```
2. **Local Game**:
   On one player's machine, run:
   ```
   java TicTacToe
   ```
   Ensure each client is configured to connect to the host machine's IP address where `TicTacToeLAN` is running.

## Contributing
Feel free to fork this project and submit pull requests, or open issues if you find any bugs or have suggestions.

## License
This project is open-sourced under the MIT License. See the `LICENSE` file for more details.
