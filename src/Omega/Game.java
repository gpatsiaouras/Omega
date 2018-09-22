package Omega;

import java.util.Scanner;

public class Game {

	private Board board;
	private Player player1;
	private Player player2;

	public Game() {
		this.board = new Board();
		this.board.generateHexagonFromSize(3);
		this.player1 = new Player();
		this.player2 = new Player();
		start();
	}

	private void start() {
		boolean gameIsOver = false;
		Scanner reader = new Scanner(System.in);
		boolean playerOneIsNext = true;
		while (!gameIsOver) {

			board.printOnTerminal();

			System.out.print("Player " + (playerOneIsNext ? 1 : 2) + " put WHITE stone: ");
			String[] whiteStone = reader.next().split(",");
			System.out.print("Player " + (playerOneIsNext ? 1 : 2) + " put BLACK stone: ");
			String[] blackStone = reader.next().split(",");

			Move move = new Move();
			move.setWhiteHexagon(board.getHexagonFromCoordinates(Integer.parseInt(whiteStone[0]), Integer.parseInt(whiteStone[1]), Integer.parseInt(whiteStone[2])));
			move.setBlackHexagon(board.getHexagonFromCoordinates(Integer.parseInt(blackStone[0]), Integer.parseInt(blackStone[1]), Integer.parseInt(blackStone[2])));
			move.setPlayer(playerOneIsNext ? player1 : player2);
			board.addMoveAndMarkHexagon(move);

			if (playerOneIsNext)
				playerOneIsNext = false;
			else
				playerOneIsNext = true;

			if (board.isFull()) {
				gameIsOver = true;
			}
		}
		reader.close();
		System.out.println("Game Over!");
	}
}
