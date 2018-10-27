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
	private int iterations;
	//Hashmap containing the hexagon objects accessible by a key of x,y,z
	//where x,y,z the coordinates of the hexagon.
	private Map<String, Hexagon> hexagonMap;

	public Board(int boardSize) {
		this.boardSize = boardSize;
		this.hexagons = new ArrayList<>();
		this.moveHistory = new ArrayList<>();
		hexagonMap = new HashMap<>();
	}

	/**
	 * Generate a new Grid for the board in the UI,
	 * Consider as "size" the middle horizontal line of a hexagon structure.
	 */
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

	/**
	 * Add a new Hexagon object, increment available and total hexagons
	 * update the hashmap with this hexagon
	 * @param x
	 * @param y
	 * @param z
	 */
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

	/**
	 * Returns true if board is full and moves cannot be played anymore.
	 * @return boolean
	 */
	public boolean isFull() {
		int mod = totalHexagons % 4;
		return totalAvailableHexagons <= mod;
	}

	/**
	 * Adds a move to the moves history
	 * and reduces the number of available Hexagons
	 * @param move
	 */
	public void addMoveToBoard(Move move) {
		moveHistory.add(move);
		totalAvailableHexagons = totalAvailableHexagons - 2;
	}

	/**
	 * Removes a move from moves history,
	 * Uncovers the hexagons of this move so
	 * that they are available again
	 * @param move
	 */
	public void removeMoveFromBoard(Move move) {
		move.getWhiteHexagon().uncover();
		move.getBlackHexagon().uncover();
		moveHistory.remove(move);
		totalAvailableHexagons = totalAvailableHexagons + 2;
	}

	/**
	 * Resets the board to initial state
	 * to restart the game. Uncovers all hexagons,
	 * resets number of free hexagons and initiates a new
	 * clean moves list.
	 */
	public void resetBoard() {
		for (Hexagon hexagon : getHexagons()) {
			hexagon.uncover();
		}
		this.totalAvailableHexagons = this.totalHexagons;
		this.moveHistory = new ArrayList<>();
		this.iterations = 0;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public int getIterations() {
		return iterations;
	}

	public void incrementIterations() {
		this.iterations++;
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
}
