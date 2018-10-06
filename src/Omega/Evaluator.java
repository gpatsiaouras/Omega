package Omega;

import Omega.ui.Board;
import Omega.ui.Hexagon;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

	private static final int[][] NEIGHBORS = {
			{-1, 0, 1}, {1, 0, -1}, {-1, 1, 0}, {0, 1, -1}, {1, -1, 0}, {0, -1, 1}
	};

	private Board board;
	private int[] parent;
	private int[] size;
	private Hexagon[] hexagons;
	private int groups;

	public Evaluator(Board board) {
		this.board = board;

		groups = board.getTotalHexagons();
		parent = new int[board.getTotalHexagons()];
		size = new int[board.getTotalHexagons()];
		hexagons = new Hexagon[board.getTotalHexagons()];

		for (int i = 0; i < board.getHexagons().size(); i++) {
			hexagons[i] = board.getHexagons().get(i);
			parent[i] = i;
			size[i] = 1;
		}

		System.out.println();
	}

	private int find(int i) {
		int p = parent[i];
		if (i == p) {
			return i;
		}

		return parent[i] = find(p);
	}

	private void union(int i, int j) {
		int root1 = find(i);
		int root2 = find(j);

		if (root2 == root1) return;

		//TODO Remove one if is useless
		if (size[root1] > size[root2]) {
			parent[root2] = root1;
			size[root1] += size[root2];
		} else if (size[root2] > size[root1]) {
			parent[root1] = root2;
			size[root2] += size[root1];
		} else {
			parent[root2] = root1;
			size[root1] += size[root2];
		}

		groups--;
	}

	private boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public void evaluateBoard(Hexagon current) {
		int positionCurrent = getPositionOfHexagon(current);
		for (Hexagon neighbor : getNeighbors(current)) {
			int positionNeighbor = getPositionOfHexagon(neighbor);
			if (neighbor.hasSameCoverWith(current) && !connected(positionCurrent, positionNeighbor)) {
				union(positionCurrent, positionNeighbor);
			}
		}
	}

	private List<Hexagon> getNeighbors(Hexagon current) {
		List<Hexagon> neighbors = new ArrayList<>();
		for (int[] neighbor : NEIGHBORS) {
			String searchKey = (current.getX() + neighbor[0]) + "," + (current.getY() + neighbor[1]) + "," + (current.getZ() + neighbor[2]);
			if (hexagonExists(searchKey)) {
				neighbors.add(board.getHexagonMap().get(searchKey));
			}
		}

		return neighbors;
	}

	private boolean hexagonExists(String searchKey) {
		return board.getHexagonMap().containsKey(searchKey);
	}

	private int getPositionOfHexagon(Hexagon hexagon) {
		for (int i = 0; i < hexagons.length; i++) {
			if (hexagon == hexagons[i]) return i;
		}
		return -1;
	}

	public int getGroups() {
		return (groups - 1);
	}

	public int[] calculateScore() {
		int[] scores = new int[2];

		int scoreWhite = 1;
		int scoreBlack = 1;

		System.out.print("White Score: ");
		for (int index = 0; index < parent.length; index++) {
			if (parent[index] == index && hexagons[index].isCoveredWithWhite()) {
				System.out.print(size[index] + "*");
				scoreWhite = scoreWhite * size[index];
			}
		}
		System.out.println(" = " + scoreWhite);
		System.out.print("Black Score: ");
		for (int index = 0; index < parent.length; index++) {
			if (parent[index] == index && hexagons[index].isCoveredWithBlack()) {
				System.out.print(size[index] + "*");
				scoreBlack = scoreBlack * size[index];
			}
		}
		System.out.println("Score Black: " + scoreBlack);

		scores[0] = scoreWhite;
		scores[1] = scoreBlack;

		return scores;
	}
}
