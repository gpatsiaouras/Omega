package Omega;

import Omega.ui.Board;
import Omega.ui.Hexagon;

import java.util.*;

import static Omega.Evaluator.NEIGHBORS;
import static Omega.ui.Hexagon.BLACK;
import static Omega.ui.Hexagon.NOT_COVERED;
import static Omega.ui.Hexagon.WHITE;

//TODO Union find should be implemented only once. Make it more abstract
public class CheapBoard {

	private int[] board;
	public Map<Hexagon, Integer> hexagonMap;
	public Map<Integer, Hexagon> indexToObjectHexagons;
	private int[] parent;
	private int[] size;
	private int[][] neighbors;

	public CheapBoard(CheapBoard cheapBoard) {
		this.board = cheapBoard.board.clone();
		this.neighbors = cheapBoard.neighbors.clone();
		this.parent = new int[board.length];
		this.size = new int[board.length];
		this.hexagonMap = cheapBoard.hexagonMap;
		this.indexToObjectHexagons = cheapBoard.indexToObjectHexagons;
	}

	public CheapBoard(Board board) {
		this.board = new int[board.getTotalHexagons()];
		this.neighbors = new int[board.getTotalHexagons()][6];
		this.hexagonMap = new HashMap<>();
		this.indexToObjectHexagons = new HashMap<>();
		this.parent = new int[board.getTotalHexagons()];
		this.size = new int[board.getTotalHexagons()];

		int index = 0;
		for (Hexagon hexagon : board.getHexagons()) {
			this.board[index] = hexagon.getCover();
			this.parent[index] = index;
			this.size[index] = 1;
			this.hexagonMap.put(hexagon, index);
			this.indexToObjectHexagons.put(index, hexagon);
			index++;
		}

		for (int i = 0; i < board.getHexagons().size(); i++) {
			for (int nei = 0; nei < NEIGHBORS.length; nei++) {
				String searchKey = (board.getHexagons().get(i).getX() + NEIGHBORS[nei][0]) + "," + (board.getHexagons().get(i).getY() + NEIGHBORS[nei][1]) + "," + (board.getHexagons().get(i).getZ() + NEIGHBORS[nei][2]);
				if (board.getHexagonMap().containsKey(searchKey)) {
					this.neighbors[i][nei] = this.hexagonMap.get(board.getHexagonMap().get(searchKey));
				} else {
					this.neighbors[i][nei] = -1;
				}
			}
		}
	}

	public boolean isEmpty() {
		return Arrays.stream(board).noneMatch(hex -> hex == Hexagon.NOT_COVERED);
	}

	public double evaluate() {
		resetUnionFind();
		//Replay the board and do unions
		for (int i = 0; i < board.length; i++) {
			if (board[i] != NOT_COVERED) {
				for (int nei : neighbors[i]) {
					if (nei != -1 && board[i] == board[ nei] && !connected(i, nei)) {
						union(i, nei);
					}
				}
			}
		}

		long whiteScore = 1;
		double whiteGroups = 0;
		double whiteGroupsSum = 0;
		long blackScore = 1;
		double blackGroups = 0;
		double blackGroupsSum = 0;

		for (int index = 0; index < parent.length; index++) {
			if (parent[index] == index) {
				if (board[index] == WHITE) {
					whiteScore *= size[index];
					whiteGroupsSum += size[index];
					whiteGroups++;
				} else if (board[index] == BLACK) {
					blackScore *= size[index];
					blackGroupsSum += size[index];
					blackGroups++;
				}
			}
		}

		double blackAverage = blackGroupsSum / blackGroups;
		double whiteAverage= whiteGroupsSum / whiteGroups;

		return 0.2*blackScore + 0.4*blackAverage + 0.4*blackGroups - 0.2*whiteScore - 0.4*whiteAverage - 0.4*whiteGroups;
	}

	private void resetUnionFind() {
		for (int index = 0; index < board.length; index++) {
			this.parent[index] = index;
			this.size[index] = 1;
		}
	}

	private void union(int i, int j) {
		int root1 = find(i);
		int root2 = find(j);

		if (root2 == root1) return;

		if (size[root2] > size[root1]) {
			parent[root1] = root2;
			size[root2] += size[root1];
		} else {
			parent[root2] = root1;
			size[root1] += size[root2];
		}
	}

	private int find(int i) {
		int p = parent[i];
		if (i == p) {
			return i;
		}

		return parent[i] = find(p);
	}

	private boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public void printBoard() {
		System.out.print("[");
		for (int i = 0; i < board.length; i++) {
			System.out.print(board[i]);
			if (i != board.length -1) System.out.print(",\t");

		}
		System.out.println("]");
	}

	//Each string inside the list will contain the white and black move(index) seperated by comma (,) .e.g 4-1
	public List<String> getSuccessors() {
		List<String> successors = new ArrayList<>();
		for (int whiteIndex = 0; whiteIndex < board.length; whiteIndex++) {
			for (int blackIndex = 0; blackIndex < board.length; blackIndex++) {
				if (whiteIndex != blackIndex && board[whiteIndex] == Hexagon.NOT_COVERED && board[blackIndex] == Hexagon.NOT_COVERED) {
					successors.add(whiteIndex+","+blackIndex);
				}
			}
		}
		//This is the magic. Shuffle the "braches" before returning
		Collections.shuffle(successors);
		
		return successors;
	}

	public CheapBoard successor(String child) {
		String[] indexes = child.split(",");
		board[Integer.parseInt(indexes[0])] = WHITE;
		board[Integer.parseInt(indexes[1])] = BLACK;

		return this;
	}
}
