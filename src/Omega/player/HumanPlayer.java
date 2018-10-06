package Omega.player;

import Omega.ui.Board;
import Omega.ui.Hexagon;

public class HumanPlayer extends Player {

	public HumanPlayer(int number, String name) {
		super(number, name, "Human");
	}

	@Override
	public Hexagon makeMove(Board board) {
		return null;
	}
}
