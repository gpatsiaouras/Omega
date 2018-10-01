package Omega;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

	private static final int[][] NEIGHBORS = {
			{-1,0,1},{1,0,-1},{-1,1,0},{0,1,-1},{1,-1,0},{0,-1,1}
	};

	private List<Hexagon> alreadyChecked;

	private Board board;

	public Evaluator(Board board) {
		this.board = board;
	}

	public void evaluateBoard() {
		//Reset the List
		alreadyChecked = new ArrayList<>();
		List<Integer> whiteGroup = new ArrayList<>();
		List<Integer> blackGroup = new ArrayList<>();
		//TODO Trying to distinquise the groups
		for (Hexagon hexagon : board.getHexagons()) {

			List<Hexagon> neighbors = getNeighbors(hexagon);
			for (Hexagon neighbor : neighbors) {
				if (hexagon.getCover() == neighbor.getCover() && neighbor.isCovered()) {

//					if (neighbor.isCoveredWithBlack())
//						blackGroup.add(ne)
//					else
//						whiteGroup.add(neighbor);

					neighbor.highlight();
					alreadyChecked.add(neighbor);
				}
			}
			alreadyChecked.add(hexagon);
		}

		System.out.println(alreadyChecked.size());
	}

	private List<Hexagon> getNeighbors(Hexagon current) {
		List<Hexagon> neighbors = new ArrayList<>();

		for (int[] neighbor : NEIGHBORS) {
			String searchKey = (current.getX()+ neighbor[0]) + "," + (current.getY() + neighbor[1]) + "," + (current.getZ() + neighbor[2]);
			if (hexagonExists(searchKey)) {
				neighbors.add(board.getHexagonMap().get(searchKey));
			}
		}

		return neighbors;
	}

	private boolean hexagonExists(String searchKey) {
		return board.getHexagonMap().containsKey(searchKey);
	}
}
