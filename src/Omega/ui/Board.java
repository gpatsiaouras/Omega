package Omega.ui;

import Omega.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

	private List<Hexagon> hexagons;
	private List<Move> moveHistory;
	private int totalAvailableHexagons;
	private int boardSize;
	private int totalHexagons;
	private Map<String, Hexagon> hexagonMap;

	public Board(int boardSize) {
		this.boardSize = boardSize;
		this.hexagons = new ArrayList<>();
		this.moveHistory = new ArrayList<>();
		hexagonMap = new HashMap<>();
	}

	public void generateHexagonsGrid() {

		int midOfAxes = 0;
		int startOfAxis = -boardSize / 2;
		int endOfAxis = boardSize / 2;

		int offsetForFirstPart = 0;
		int offsetForSecondPart = 0;

		for (int z = startOfAxis; z <= endOfAxis; z++) {
			if (z <= midOfAxes) {
				int howManyToPutInTheRow = (boardSize / 2) + 1 + offsetForFirstPart;
				for (int incr = 0; incr < howManyToPutInTheRow; incr++) {
					int y = endOfAxis - incr;
					int x = -z - y;
					addHexagonToList(x, y, z);
				}

				offsetForFirstPart = offsetForFirstPart + 1;
			} else {
				int howManyToPutInTheRow = boardSize - 1 + offsetForSecondPart;
				for (int incr = 0; incr < howManyToPutInTheRow; incr++) {
					int x = startOfAxis + incr;
					int y = -z - x;
					addHexagonToList(x, y, z);
				}
				offsetForSecondPart--;
			}
		}
	}

	private void addHexagonToList(int x, int y, int z) {
		Hexagon hexagon = new Hexagon();
		hexagon.setX(x);
		hexagon.setY(z);
		hexagon.setZ(y);
		hexagons.add(hexagon);
		totalAvailableHexagons++;
		totalHexagons++;
		hexagonMap.put(hexagon.getX() + "," + hexagon.getY() + "," + hexagon.getZ(), hexagon);
	}

	public boolean isFull() {
		return totalAvailableHexagons < 4;
	}

	public void addMoveToBoard(Move move) {
		moveHistory.add(move);
		totalAvailableHexagons = totalAvailableHexagons - 2;
	}

	public void removeMoveFromBoard(Move move) {
		move.getWhiteHexagon().uncover();
		move.getBlackHexagon().uncover();
		moveHistory.remove(move);
		totalAvailableHexagons = totalAvailableHexagons + 2;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public int getTotalHexagons() {
		return totalHexagons;
	}

	public List<Hexagon> getHexagons() {
		return hexagons;
	}

	public Map<String, Hexagon> getHexagonMap() {
		return hexagonMap;
	}

	public List<Move> getMoveHistory() {
		return moveHistory;
	}

	public void setMoveHistory(List<Move> moveHistory) {
		this.moveHistory = moveHistory;
	}

	//TODO Implement this method
	public List<Move> getNextAvailableMoves() {
		return null;
	}
}
