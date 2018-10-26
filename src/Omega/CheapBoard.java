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
	private int lastColor;
	private int lastPlayer;

	public CheapBoard(CheapBoard cheapBoard) {
		this.board = cheapBoard.board.clone();
		this.neighbors = cheapBoard.neighbors.clone();
		this.parent = new int[board.length];
		this.size = new int[board.length];
		this.hexagonMap = cheapBoard.hexagonMap;
		this.indexToObjectHexagons = cheapBoard.indexToObjectHexagons;
		this.lastColor = cheapBoard.lastColor;
		this.lastPlayer = cheapBoard.lastPlayer;
	}

	public CheapBoard(Board board) {
		this.board = new int[board.getTotalHexagons()];
		this.neighbors = new int[board.getTotalHexagons()][6];
		this.hexagonMap = new HashMap<>();
		this.indexToObjectHexagons = new HashMap<>();
		this.parent = new int[board.getTotalHexagons()];
		this.size = new int[board.getTotalHexagons()];
		this.lastColor = BLACK;
		this.lastPlayer = 1;

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

	public long evaluate() {
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
		long blackScore = 1;

		for (int index = 0; index < parent.length; index++) {
			if (parent[index] == index) {
				if (board[index] == WHITE) {
					whiteScore *= size[index];
				} else if (board[index] == BLACK) {
					blackScore *= size[index];
				}
			}
		}

		return whiteScore - blackScore;
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

	public List<Integer> getSuccessors() {
		List<Integer> successors = new ArrayList<>();
		for (int index = 0; index < board.length; index++) {
			if (board[index] == Hexagon.NOT_COVERED) successors.add(index);
		}

		return successors;
	}

	public CheapBoard successor(int child) {
		lastColor  = getNextColor();
		board[child] = lastColor;

		return this;
	}

	private int getNextColor() {
		return lastColor == 1 ? 2 : 1;
	}

	public int getLastColor() {
		return lastColor;
	}
}
