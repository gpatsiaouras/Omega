package Omega;

import Omega.player.AIPlayer;
import Omega.player.HumanPlayer;
import Omega.player.Player;
import Omega.ui.Board;
import Omega.ui.Grid;
import Omega.ui.Hexagon;
import javafx.application.Application;
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

	public Game(Stage primaryStage, Application.Parameters parameters) {
		int boardSize;
		if (parameters.getRaw().size() > 0) {
			boardSize = Integer.parseInt(parameters.getRaw().get(0));
		} else {
			boardSize = 11;
		}
		this.board = new Board(boardSize);
		this.board.generateHexagonsGrid();

		this.player1 = new AIPlayer(1, "Juanita");
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
			playMove(currentPlayer.makeMove(this));
		} else if (board.isFull()) {
			gameOver();
		}
	}

	public void playMove(Move currentMove) {
		board.incrementIterations();
		System.out.println(board.getIterations() + ":\t" + currentPlayer.getType() + " " + currentPlayer.getName() + "\t" + currentPlayer.getNumber()
				+ " selected White:" + currentMove.getWhiteHexagon().getX() + "," + currentMove.getWhiteHexagon().getY()
				+ " \tBlack: " + currentMove.getBlackHexagon().getX() + "," + currentMove.getBlackHexagon().getY());

		board.addMoveToBoard(currentMove);
		evaluator.evaluateBoard(currentMove);
		swapPlayersTurn();

		continueGame();
	}

	public void undoLastMove() {
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
		Score score =  evaluator.calculateScore();
		score.printScores();
		System.out.println("Number of groups on board: " + evaluator.getGroups());
	}

	public Board getBoard() {
		return board;
	}
}
