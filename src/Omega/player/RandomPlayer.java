package Omega.player;

import Omega.Game;
import Omega.Move;
import Omega.ui.Hexagon;

import java.util.Random;

public class RandomPlayer extends Player {

	public RandomPlayer(int number, String name) {
		super(number, name, "Random");
	}

	@Override
	public Move makeMove(Game game) {
		Move move = new Move();
		move.setPlayer(this);
		Random random = new Random();
		while (move.getWhiteHexagon() == null || move.getBlackHexagon() == null) {
			Hexagon randomHexagon = game.getBoard().getHexagons().get(random.nextInt(game.getBoard().getHexagons().size()));
			if (!randomHexagon.isCovered()) {
				if (move.getWhiteHexagon() == null) {
					randomHexagon.coverWithWhite();
					move.setWhiteHexagon(randomHexagon);
				} else if (move.getWhiteHexagon() != null) {
					randomHexagon.coverWithBlack();
					move.setBlackHexagon(randomHexagon);
				}
			}
		}

		return move;
	}
}
