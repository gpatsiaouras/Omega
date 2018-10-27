package Omega.ui;

import Omega.Game;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Grid {

	public static final int BASE_SIZE = 10;

	/**
	 * Returns the UI JAVA FX Component containing the representation of the board
	 * @param game
	 * @return
	 */
	public Group getGridOfHexagonsInPosition(Game game) {
		Group hexagonGroup = new Group();
		int boardSize = game.getBoard().getBoardSize();
		int middleX = boardSize / 2;
		int middleY = boardSize / 2;

		for (Hexagon hexagon : game.getBoard().getHexagons()) {
			int positionX = hexagon.getX() + middleX;
			int positionY = hexagon.getY() + middleY;

			hexagon.getPoints().addAll(getSmallHexagonInPosition(positionX, positionY));
			hexagon.setFill(Color.GREEN);

			hexagon.setOnMouseClicked(game);

			hexagonGroup.getChildren().add(hexagon);

		}

		return hexagonGroup;
	}

	/**
	 * Prints the hexagon as polygons with specific coordinates so that the whole grid
	 * looks like a big hexagon
	 * @param right
	 * @param top
	 * @return
	 */
	private Double[] getSmallHexagonInPosition(int right, int top) {
		return new Double[]{
				BASE_SIZE * (2.0 + (5 * right) + (2.5 * top)), 0.0 + (5 * top * BASE_SIZE),
				BASE_SIZE * (4.0 + (5 * right) + (2.5 * top)), BASE_SIZE * (2.0 + (5 * top)),
				BASE_SIZE * (4.0 + (5 * right) + (2.5 * top)), BASE_SIZE * (4.0 + (5 * top)),
				BASE_SIZE * (2.0 + (5 * right) + (2.5 * top)), BASE_SIZE * (6.0 + (5 * top)),
				0.0 + BASE_SIZE * (5 * right + (2.5 * top)), BASE_SIZE * (4.0 + (5 * top)),
				0.0 + BASE_SIZE * (5 * right + (2.5 * top)), BASE_SIZE * (2.0 + (5 * top)),
		};
	}
}
