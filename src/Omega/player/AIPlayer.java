package Omega.player;

import Omega.Move;
import Omega.ui.Board;
import Omega.ui.Hexagon;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AIPlayer extends Player {

	private long alpha = -Long.MAX_VALUE;
	private long beta = Long.MAX_VALUE;

	public AIPlayer(int number, String name) {
		super(number, name, "AI");
	}

	@Override
	public Hexagon makeMove(Board board) {
//		List<Hexagon> freeHexagons = board.getHexagons().stream()
//				.filter(hexagon -> !hexagon.isCovered())
//				.collect(Collectors.toList());
//
//		Random random = new Random();
//		return freeHexagons.get(random.nextInt(freeHexagons.size() - 1));
		int[] moveArray = negaMax(board, 3, alpha, beta);
		return board.getHexagonMap().get(moveArray[0]+","+moveArray[1]+","+moveArray[2]);
	}

	private int[] negaMax(Board board, int depth, long alpha, long beta) {
		List<Move> nextMoves = board.getNextAvailableMoves();

		long score = -Long.MAX_VALUE;

		if (nextMoves.isEmpty() || depth == 0) {
			score = evaluate();
		} else {
			for (Move nextMove : nextMoves) {
				Hexagon value = negaMax(board, depth - 1, -beta, -alpha);

				if (value > score) score = value;
				if (score > alpha) alpha = score;
				if (score >= beta) break;
			}
		}

		return score;
	}

	private int evaluate() {
		return 0;
	}
}
