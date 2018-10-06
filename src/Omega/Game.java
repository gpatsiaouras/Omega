package Omega;

import Omega.player.AIPlayer;
import Omega.player.HumanPlayer;
import Omega.player.Player;
import Omega.ui.Board;
import Omega.ui.Grid;
import Omega.ui.Hexagon;
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
	private Player currentPlayer;

	private Move currentMove;
	private Evaluator evaluator;

	public Game(Stage primaryStage) {
		this.board = new Board(7);
		this.board.generateHexagonsGrid();

		this.player1 = new HumanPlayer(1, "Juanita");
		this.player2 = new AIPlayer(2, "Fernando");
		currentPlayer = player1;

		this.evaluator = new Evaluator(this.getBoard());
		startUI(primaryStage);
		continueGame();
	}

	private void startUI(Stage primaryStage) {
		Grid grid = new Grid();
		VBox vBox = new VBox();

		Button undoButton = new Button();
		undoButton.setText("Undo last move");
		undoButton.setOnAction(event -> undoLastMove());

		vBox.getChildren().addAll(grid.getGridOfHexagonsInPosition(this), undoButton);
		primaryStage.setTitle("Omega Board Game");
		primaryStage.setScene(new Scene(vBox, board.getBoardSize() * 60, board.getBoardSize() * 60));
		primaryStage.show();
	}

	private void continueGame() {
		if (!board.isFull() && currentPlayer instanceof AIPlayer) {
			playMove(currentPlayer.makeMove(board));
		} else if (board.isFull()) {
			gameOver();
		}
	}

	private void playMove(Move currentMove) {
		System.out.println(currentPlayer.getType() + " " + currentPlayer.getName() + " " + currentPlayer.getNumber()
				+ " selected " + currentMove.getWhiteHexagon().getX() + "," + currentMove.getWhiteHexagon().getY() + " as white"
				+ " selected " + currentMove.getBlackHexagon().getX() + "," + currentMove.getBlackHexagon().getY() + " as black");

		board.addMoveToBoard(currentMove);
		evaluator.evaluateBoard(currentMove);
		swapPlayersTurn();

		continueGame();
	}

	private void undoLastMove() {
		//Do not allow undo if a player is in the middle of a move
		if (currentMove == null) {
			Move lastMove = board.getMoveHistory().get(board.getMoveHistory().size() - 1);
			board.removeMoveFromBoard(lastMove);
			System.out.println("Undid last move");
			swapPlayersTurn();
			continueGame();
		}
	}

	private void swapPlayersTurn() {
		currentPlayer = (currentPlayer == player1 ? player2 : player1);
	}

	@Override
	public void handle(MouseEvent event) {
		Hexagon hexagon = (Hexagon) event.getTarget();

		if (!hexagon.isCovered() && !board.isFull()) {
			if (currentMove == null) {
				hexagon.coverWithWhite();
				currentMove = new Move();
				currentMove.setWhiteHexagon(hexagon);
				currentMove.setPlayer(currentPlayer);
			} else {
				hexagon.coverWithBlack();
				currentMove.setBlackHexagon(hexagon);
				playMove(currentMove);
				currentMove = null;
			}
		}
	}

	private void gameOver() {
		int[] scores = evaluator.calculateScore();
		System.out.println("Number of groups on board: " + evaluator.getGroups());
	}

	public Board getBoard() {
		return board;
	}
}
