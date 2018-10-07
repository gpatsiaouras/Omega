package Omega.player;

import Omega.Game;
import Omega.Move;
import Omega.ui.Board;
import Omega.ui.Hexagon;

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
	public Move makeMove(Game game) {
		long bestScoreAchieved = negaMax(game, 10, alpha, beta);
		return getDummyMove(game);
//		return move;
	}

	private Move getDummyMove(Game game) {
		List<Hexagon> freeHexagons = game.getBoard().getHexagons().stream()
				.filter(hexagon -> !hexagon.isCovered())
				.collect(Collectors.toList());

		Random random = new Random();
		Move move = new Move();
		move.setPlayer(this);
		Hexagon whiteHexagon = freeHexagons.get(0);
		Hexagon blackHexagon = freeHexagons.get(1);
		move.setWhiteHexagon(whiteHexagon);
		move.setBlackHexagon(blackHexagon);
		whiteHexagon.coverWithWhite();
		blackHexagon.coverWithBlack();

		return move;
	}

	//TODO CHANGE TO WORK WITH LIST OF MOVES INSTEAD 
	private long negaMax(Game game, int depth, long alpha, long beta) {
		List<Move> nextMoves = game.getBoard().getNextAvailableMoves();

		long score = -Long.MAX_VALUE;

		if (nextMoves.isEmpty() || depth == 0) {
			score = evaluate();
		} else {
			for (Move nextMove : nextMoves) {
//				game.playMove(nextMove);

				long value = negaMax(game, depth - 1, -beta, -alpha);

				if (value > score) score = value;
				if (score > alpha) alpha = score;
				if (score >= beta) break;

				game.undoLastMove();
			}
		}

		return score;
	}

	private int evaluate() {
		return new Random().nextInt(6);
	}
}
