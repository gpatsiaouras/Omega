package Omega.player;

import Omega.ui.Board;
import Omega.ui.Hexagon;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AIPlayer extends Player {

	public AIPlayer(int number, String name) {
		super(number, name, "AI");
	}

	@Override
	public Hexagon makeMove(Board board) {
		//TODO THIS WILL BE SMART SOME DAY
		List<Hexagon> freeHexagons = board.getHexagons().stream()
				.filter(hexagon -> !hexagon.isCovered())
				.collect(Collectors.toList());

		Random random = new Random();
		return freeHexagons.get(random.nextInt(freeHexagons.size() - 1));
	}
}
