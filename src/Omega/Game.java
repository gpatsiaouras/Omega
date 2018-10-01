package Omega;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game implements EventHandler<MouseEvent> {

	private Board board;
	private Player player1;
	private Player player2;
	private Player playersTurn;
	private Move currentMove;
	private Evaluator evaluator;

	public Game(Stage primaryStage) {
		this.board = new Board(11);
		this.board.generateHexagonsGrid();
		this.player1 = new Player(1);
		this.player2 = new Player(2);
		this.evaluator = new Evaluator(this.getBoard());
		playersTurn = player1;
		startUI(primaryStage);
	}

	private void startUI(Stage primaryStage) {
		Grid grid = new Grid();
		VBox vBox = new VBox();

		Button button = new Button();
		button.setText("Evaluate Board");
		button.setOnAction(event -> evaluator.evaluateBoard());

		vBox.getChildren().addAll(grid.getGridOfHexagonsInPosition(this), button);

		primaryStage.setTitle("Omega Board Game");
		primaryStage.setScene(new Scene(vBox, 800, 800));
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
			hexagon.coverWithWhite();

			currentMove = new Move();
			currentMove.setWhiteHexagon(hexagon);
			currentMove.setPlayer(playersTurn);

			System.out.println("Player " + playersTurn.getPlayerNumber() + " selected " + hexagon.getX() + "," + hexagon.getY() + " as his white hexagon");
		} else {
			hexagon.coverWithBlack();

			currentMove.setBlackHexagon(hexagon);

			System.out.println("Player " + playersTurn.getPlayerNumber() + " selected " + hexagon.getX() + "," + hexagon.getY() + " as his black hexagon");

			playersTurn = (playersTurn == player1 ? player2 : player1);
			board.addMoveToBoard(currentMove);
			currentMove = null;
		}
	}
}
