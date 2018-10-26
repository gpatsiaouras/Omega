package Omega.player;

import Omega.CheapBoard;
import Omega.Game;
import Omega.Move;

import static Omega.ui.Hexagon.*;

public class AIPlayer extends Player {

	private Move move;
	private int prunings;

	public AIPlayer(int number, String name) {
		super(number, name, "AI");
	}

	@Override
	public Move makeMove(Game game) {
		prunings = 0;
		move = new Move();
		move.setPlayer(this);
		CheapBoard cheapBoard = new CheapBoard(game.getBoard());
		long startTime = System.currentTimeMillis();
		System.out.print("Thinking...");
		negaMax(cheapBoard, 6, -Long.MAX_VALUE, Long.MAX_VALUE);
		System.out.print("done. Took " + (System.currentTimeMillis() - startTime ) + "ms");
		move.getWhiteHexagon().coverWithWhite();
		move.getBlackHexagon().coverWithBlack();
		System.out.println(" Prunings "+prunings);
		return move;
	}

	private long negaMax(CheapBoard cheapBoard, int depth, long alpha, long beta) {

		if (cheapBoard.isEmpty() || depth == 0) {
			return cheapBoard.evaluate();
		}
		long score = -Long.MAX_VALUE;

		for (int indexOfChild : cheapBoard.getSuccessors()) {
			CheapBoard otherCheapBoard = new CheapBoard(cheapBoard);
			int multiplier = (otherCheapBoard.getLastColor() == WHITE ? -1 : 1);
			long value = multiplier * negaMax(otherCheapBoard.successor(indexOfChild), depth - 1, -beta, -alpha);

			if (value > score) {
				score = value;
			}

			if (score > alpha) {
				alpha = score;
				if (otherCheapBoard.getLastColor() == WHITE) move.setWhiteHexagon(otherCheapBoard.indexToObjectHexagons.get(indexOfChild));
				else move.setBlackHexagon(otherCheapBoard.indexToObjectHexagons.get(indexOfChild));
			}

			if (score >= beta) {
				prunings++;
				break;
			}
		}

		return score;
	}
}
