package Omega;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Grid {

	public static final int BASE_SIZE = 10;

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
		return new Double[]{
				BASE_SIZE * (2.0 + (5 * right) + (2.5 * top)), 0.0 + (5 * top * BASE_SIZE),
				BASE_SIZE * (4.0 + (5 * right) + (2.5 * top)), BASE_SIZE * ( 2.0 + (5 * top)),
				BASE_SIZE * (4.0 + (5 * right) + (2.5 * top)), BASE_SIZE * ( 4.0 + (5 * top)),
				BASE_SIZE * (2.0 + (5 * right) + (2.5 * top)), BASE_SIZE * ( 6.0 + (5 * top)),
				0.0 + BASE_SIZE * (5 * right + (2.5 * top)), BASE_SIZE * ( 4.0 + (5 * top)),
				0.0 + BASE_SIZE * (5 * right + (2.5 * top)), BASE_SIZE * ( 2.0 + (5 * top)),
		};
	}
}
