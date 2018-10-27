package Omega.player;

import Omega.CheapBoard;
import Omega.Game;
import Omega.Move;

public class AIPlayer extends Player {

	private Move move;
	private long prunings;
	private long numberOfNegaMaxCalls;

	public AIPlayer(int number, String name) {
		super(number, name, "AI");
	}

	@Override
	public Move makeMove(Game game) {
		prunings = 0;
		numberOfNegaMaxCalls = 0;

		move = new Move();
		move.setPlayer(this);

		//Convert current game board to a simple version, "Cheap" in performance
		CheapBoard cheapBoard = new CheapBoard(game.getBoard());

		//Keep track of thinking time for verbosity.
		long startTime = System.currentTimeMillis();
		System.out.print("Thinking...");

		//An odd number maximizes BLACK and an even number maximizes WHITE
		negaMax(cheapBoard, 3, -Double.MAX_VALUE, Double.MAX_VALUE);

		System.out.print("done. Took " + (System.currentTimeMillis() - startTime ) + "ms.");

		//Cover the white and black hexagons that negamax returned so that they can be marked on the board
		move.getWhiteHexagon().coverWithWhite();
		move.getBlackHexagon().coverWithBlack();

		//Print prunings and searches conducted for verbosity
		System.out.print(" Prunings "+prunings+".");
		System.out.println(" Searches "+ numberOfNegaMaxCalls);
		return move;
	}

	/**
	 * Negamax implementation of alphabeta. Receives the current state of the board in a simple representation
	 * without objects, just a primitive type single dimension array.
	 * Also depth, alpha, beta and returns an evaluated score.
	 * @param cheapBoard
	 * @param depth
	 * @param alpha
	 * @param beta
	 * @return
	 */
	private double negaMax(CheapBoard cheapBoard, int depth, double alpha, double beta) {
		numberOfNegaMaxCalls++;

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
