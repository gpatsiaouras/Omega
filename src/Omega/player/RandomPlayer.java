package Omega.player;

import Omega.Game;
import Omega.Move;
import Omega.ui.Hexagon;

public class RandomPlayer extends Player {

	public RandomPlayer(int number, String name) {
		super(number, name, "Random");
	}

	@Override
	public Move makeMove(Game game) {
		Move move = new Move();
		move.setPlayer(this);
		for (Hexagon current : game.getBoard().getHexagons()) {
			if (move.getWhiteHexagon() != null && move.getBlackHexagon() != null) break;
			if (!current.isCovered()) {
				if (move.getWhiteHexagon() == null) {
					current.coverWithWhite();
					move.setWhiteHexagon(current);
				} else if (move.getWhiteHexagon() != null) {
					current.coverWithBlack();
					move.setBlackHexagon(current);
				}
			}
		}

		return move;
	}
}
