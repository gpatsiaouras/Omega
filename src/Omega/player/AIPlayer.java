package Omega.player;

import Omega.CheapBoard;
import Omega.Game;
import Omega.Move;

import static Omega.ui.Hexagon.*;

public class AIPlayer extends Player {

	private int nextColor;
	private Move move;
	private int turns;

	public AIPlayer(int number, String name) {
		super(number, name, "AI");
		turns = 0;
	}

	@Override
	public Move makeMove(Game game) {
		move = new Move();
		move.setPlayer(this);
		CheapBoard cheapBoard = new CheapBoard(game.getBoard());
		//start from black so it can be swapped to white in first try
		int playerNumber = this.getNumber();
		playerNumber = (playerNumber == 1 ? 2 : 1);
		nextColor = BLACK;
		long startTime = System.currentTimeMillis();
		System.out.print("Thinking...");
		negaMax(playerNumber, cheapBoard, 8, -Long.MAX_VALUE, Long.MAX_VALUE);
		System.out.println("done. Took " + (System.currentTimeMillis() - startTime ) + "ms");
		move.getWhiteHexagon().coverWithWhite();
		move.getBlackHexagon().coverWithBlack();
		turns += 1;
		return move;
	}

	private long negaMax(int playerNumber, CheapBoard cheapBoard, int depth, long alpha, long beta) {
		//Change the player and run negamax for the next move
		nextColor = (nextColor == WHITE ? BLACK : WHITE);

		//Change
		if (nextColor == WHITE) playerNumber = (playerNumber == 1 ? 2 : 1);

		if (cheapBoard.isEmpty() || depth == 0) {
			long score = cheapBoard.evaluate(playerNumber);

			return score;
		}
		long score = -Long.MAX_VALUE;

		for (int indexOfChild : cheapBoard.getSuccessors()) {
			CheapBoard otherCheapBoard = new CheapBoard(cheapBoard);
			long value = -negaMax(playerNumber, otherCheapBoard.successor(indexOfChild, nextColor), depth - 1, -beta, -alpha);

			if (value > score) {
				score = value;
			}
			if (score > alpha) {
				alpha = score;
				if (nextColor == WHITE) move.setWhiteHexagon(cheapBoard.indexToObjectHexagons.get(indexOfChild));
				else move.setBlackHexagon(cheapBoard.indexToObjectHexagons.get(indexOfChild));
			}

			if (score >= beta) break;
		}

		return score;
	}
}
