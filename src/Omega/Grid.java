package Omega;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Grid {

	public Group getGridOfHexagonsInPosition(Game game) {
		Group hexagonGroup = new Group();
		int boardSize = game.getBoard().getBoardSize();
		int middleX = boardSize / 2;
		int middleY = boardSize / 2;

		for (Omega.Hexagon hexagon : game.getBoard().getHexagons()) {
			int positionX = hexagon.getX() + middleX;
			int positionY = hexagon.getY() + middleY;

			hexagon.getPoints().addAll(getSmallHexagonInPosition(positionX, positionY));
			hexagon.setFill(Color.GREEN);

			hexagon.setOnMouseClicked(game);
			hexagonGroup.getChildren().add(hexagon);

		}

		return hexagonGroup;
	}

	private Double[] getSmallHexagonInPosition(int right, int top) {
		int baseSize = 10;
		return new Double[]{
				(2.0 * baseSize) + (50 * right) + (25 * top), 0.0 + (50 * top),
				(4.0 * baseSize) + (50 * right) + (25 * top), (2.0 * baseSize) + (50 * top),
				(4.0 * baseSize) + (50 * right) + (25 * top), (4.0 * baseSize) + (50 * top),
				(2.0 * baseSize) + (50 * right) + (25 * top), (6.0 * baseSize) + (50 * top),
				0.0 + (50 * right) + (25 * top), (4.0 * baseSize) + (50 * top),
				0.0 + (50 * right) + (25 * top), (2.0 * baseSize) + (50 * top),
		};
	}
}
