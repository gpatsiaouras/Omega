package Omega;

import java.util.List;

public class Evaluator {
	private Board board;

	public Evaluator(Board board) {
		this.board = board;
	}

	public void evaluate(Hexagon hexagonClicked) {
		List<Hexagon> hexagons = board.getHexagons();
//		for (Hexagon hexagon : hexagons) {
		List<Hexagon> neighbors = findNeighbors(hexagonClicked);
//		}
	}

	private List<Hexagon> findNeighbors(Hexagon current) {
		int currentX = current.getX();
		int currentY = current.getY();
		int currentZ = current.getZ();
		//Calculate Left x -1, y + 1, z
		String searchLeftKey = (currentX - 1) + "," + currentY + "," + (currentZ + 1);
		Hexagon hexagonLeft = board.getHexagonMap().get(searchLeftKey);
		hexagonLeft.highlight();
		return null;
	}
}
