# 3D Tic Tac Toe Project

![3D Tic Tac Toe](tictactoe.png)

Hi there! This is my 3D Tic Tac Toe game built with LibGDX. I started with a basic flying objects demo and completely transformed it into a fun Tic Tac Toe game using a dedicated grid class.

## Project Overview

I created a 3×3 board where each cell shows a 3D model – either an X, an O, or an empty cube. I built a special `TicTacToeGrid` class that keeps track of the board, handles placing marks, and even checks for wins or ties.

For the 3D scene, I use a perspective camera to give an overhead view of the board, and ambient lighting to make everything look nice. A ModelBatch handles all the 3D rendering.

I’m using three 3D model files:
- **X** is represented by my hash model.
- **O** is represented by my sphere model.
- Empty cells are shown as cubes.

All the models and assets are included with the project.

## How to Play

1. **Moving Around:**  
   Use the WASD keys (W- up, S - down, A - left, D -right) to move the cursor around the board. The selected cell is highlighted in red.

2. **Placing Your Mark:**  
    SPACE-BAR places the current player’s mark (X or O) in that cell. If the cell is already taken, nothing happens.

3. **Game Rules:**  
   After each move, the game checks the rows, columns, and diagonals for a win. If someone wins, a message is logged and the board resets. If all cells are filled with no winner, it’s a tie and the board resets. Turns alternate between X and O, with X always starting first.

## Build and Run

To build and run the game, I use Gradle. You just need to run the appropriate Gradle tasks from your terminal or IDE. I’ve set everything up so that you can build, run, and test the project easily.

