package Omega.player;

import Omega.Game;
import Omega.Move;
import Omega.ui.Board;

public class HumanPlayer extends Player {

	public HumanPlayer(int number, String name) {
		super(number, name, "Human");
	}

	@Override
	public Move makeMove(Game game) {
		return null;
	}
}
