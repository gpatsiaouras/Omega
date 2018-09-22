package Omega;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Game implements EventHandler<MouseEvent> {

	private Board board;
	private Player player1;
	private Player player2;
	private Player playersTurn;
	private Move currentMove;

	public Game(Stage primaryStage) {
		this.board = new Board(3);
		this.board.generateHexagonsGrid();
		this.player1 = new Player(1);
		this.player2 = new Player(2);
		playersTurn = player1;
		startUI(primaryStage);
	}

	private void startUI(Stage primaryStage) {
		Grid grid = new Grid();

		primaryStage.setTitle("Omega Board Game");
		primaryStage.setScene(new Scene(grid.getGridOfHexagonsInPosition(this), 1280, 1000));
		primaryStage.show();

	}

	public Board getBoard() {
		return board;
	}

	@Override
	public void handle(MouseEvent event) {
		Hexagon hexagon = (Hexagon) event.getTarget();

		//TODO do something when the game is over
		if (board.isFull()) {
			board.printMoveHistory();
			return;
		}
		if (hexagon.isCovered()) {
			return;
		}

		if (currentMove == null) {
			currentMove = new Move();
			if (playersTurn == player1) {
				hexagon.coverWithWhite();
				currentMove.setWhiteHexagon(hexagon);
			} else {
				hexagon.coverWithBlack();
				currentMove.setBlackHexagon(hexagon);
			}
			currentMove.setPlayer(playersTurn);
			System.out.println("Player " + playersTurn.getPlayerNumber() + " selected " + hexagon.getX() + "," + hexagon.getY() + " as his white hexagon");
		} else {
			if (playersTurn == player1) {
				hexagon.coverWithBlack();
				currentMove.setBlackHexagon(hexagon);
			} else {
				hexagon.coverWithWhite();
				currentMove.setWhiteHexagon(hexagon);
			}

			System.out.println("Player " + playersTurn.getPlayerNumber() + " selected " + hexagon.getX() + "," + hexagon.getY() + " as his black hexagon");
			playersTurn = (playersTurn == player1 ? player2 : player1);
			board.addMoveToBoard(currentMove);
			currentMove = null;
		}
	}
}
