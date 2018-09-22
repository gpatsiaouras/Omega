package Omega;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Hexagon> hexagons;
	private List<Move> moveHistory;
	private int totalAvailableHexagons;

	public Board() {
		this.hexagons = new ArrayList<>();
		this.moveHistory = new ArrayList<>();
	}

	public void generateHexagonFromSize(int size) {

		int midOfAxes = 0;
		//For z axis - size /2 to + size / 2
		int startOfAxisZ = - size / 2;
		int endOfAxisZ = size / 2;

		int offsetForFirstPart = 0;
		int offsetForSecondPart = 0;

		for (int z = startOfAxisZ; z <= endOfAxisZ; z++) {
			if (z <= midOfAxes) {
				int howManyToPutInTheRow = (size / 2) + 1 + offsetForFirstPart;
				for (int incr = 0; incr < howManyToPutInTheRow; incr++) {
					int y = endOfAxisZ - incr;
					int x = -z -y;
					addHexagonToList(x, y, z);
				}

				offsetForFirstPart = offsetForFirstPart + 1;
			} else {
				int howManyToPutInTheRow = size - 1 + offsetForSecondPart;
				for (int incr = 0; incr < howManyToPutInTheRow; incr++) {
					int x = startOfAxisZ + incr;
					int y = -z - x;
					addHexagonToList(x, y, z);
				}
				offsetForSecondPart--;
			}
		}

	}

	public Hexagon getHexagonFromCoordinates(int x, int y, int z) {
		try {
			return hexagons
					.stream()
					.filter(hexagon -> hexagon.matchesCoordinates(x,y,z))
					.findFirst()
					.get();
		} catch (Exception e) {
			System.out.println(x+y+z);
			return null;
		}
	}

	private void addHexagonToList(int x, int y, int z) {
		Hexagon hexagon = new Hexagon();
		hexagon.setX(x);
		hexagon.setZ(z);
		hexagon.setY(y);
		hexagons.add(hexagon);
		totalAvailableHexagons++;
	}

	public boolean isFull() {
		return totalAvailableHexagons < 2;
	}

	public void addMoveAndMarkHexagon(Move move) {
		moveHistory.add(move);
		move.getWhiteHexagon().coverWithWhite();
		move.getBlackHexagon().coverWithBlack();
		totalAvailableHexagons = totalAvailableHexagons - 2;
	}

	public void printOnTerminal() {
		int previousRow = hexagons.get(0).getZ();

		for (Hexagon hexagon: hexagons) {
			if (hexagon.getZ() != previousRow) {
				System.out.print("\n");
				previousRow = hexagon.getZ();
			}
			if (hexagon.isCoveredWithBlack())
				System.out.print("O");
			else if (hexagon.isCoveredWithWhite())
				System.out.print("X");
			else
				System.out.print("*");
		}

		System.out.print("\n");
	}
}
