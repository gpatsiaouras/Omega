package Omega.player;

import Omega.CheapBoard;
import Omega.Game;
import Omega.Move;

public class AIPlayer extends Player {

	private Move move;
	private long prunings;
	private long searches;

	public AIPlayer(int number, String name) {
		super(number, name, "AI");
	}

	@Override
	public Move makeMove(Game game) {
		prunings = 0;
		searches = 0;

		move = new Move();
		move.setPlayer(this);
		CheapBoard cheapBoard = new CheapBoard(game.getBoard());
		long startTime = System.currentTimeMillis();
		System.out.print("Thinking...");
		//An odd number maximizes BLACK and even number maximizes WHITE
		negaMax(cheapBoard, 3, -Double.MAX_VALUE, Double.MAX_VALUE);
		System.out.print("done. Took " + (System.currentTimeMillis() - startTime ) + "ms.");
		move.getWhiteHexagon().coverWithWhite();
		move.getBlackHexagon().coverWithBlack();
		System.out.print(" Prunings "+prunings+".");
		System.out.println(" Searches "+searches);
		return move;
	}

	private double negaMax(CheapBoard cheapBoard, int depth, double alpha, double beta) {
		searches++;

		if (cheapBoard.isEmpty() || depth == 0) {
			return cheapBoard.evaluate();
		}
		double score = -Long.MAX_VALUE;

		for (String whiteBlackIndex : cheapBoard.getSuccessors()) {
			CheapBoard otherCheapBoard = new CheapBoard(cheapBoard);
			double value = -negaMax(otherCheapBoard.successor(whiteBlackIndex), depth - 1, -beta, -alpha);

			if (value > score) {
				score = value;
				String[] index = whiteBlackIndex.split(",");
				move.setWhiteHexagon(otherCheapBoard.indexToObjectHexagons.get(Integer.parseInt(index[0])));
				move.setBlackHexagon(otherCheapBoard.indexToObjectHexagons.get(Integer.parseInt(index[1])));
			}

			if (score > alpha) {
				alpha = score;
			}

			if (score >= beta) {
				prunings++;
				break;
			}
		}

		return score;
	}
}
