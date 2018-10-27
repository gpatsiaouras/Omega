package Omega;

import Omega.player.AIPlayer;
import Omega.player.HumanPlayer;
import Omega.player.Player;
import Omega.player.RandomPlayer;
import Omega.ui.Board;
import Omega.ui.Grid;
import Omega.ui.Hexagon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class Game implements EventHandler<MouseEvent> {

	private Board board;

	private Player player1;
	private Player player2;
	private Player currentPlayer;

	private ListView<Move> moveList;
	private Move currentMove;
	private Evaluator evaluator;

	private ObservableList<Move> observableList;

	public Game(Stage primaryStage) {
		newGame();
		startUI(primaryStage);
	}

	private void newGame() {
		this.board = new Board(9);//Size is the middle horizontal line of an omega board. Size 9 is a 5x5x5 board
		this.board.generateHexagonsGrid();

		//Change HumanPlayer to RandomPlayer or AIPlayer
		this.player1 = new HumanPlayer(1, "Juanita");
		this.player2 = new AIPlayer(2, "Fernando");
		currentPlayer = player1;

		this.evaluator = new Evaluator(this.getBoard());
	}

	private void resetGame() {
		this.board.resetBoard();
		this.currentPlayer = player1;
		this.currentMove = null;
		this.moveList.getItems().clear();

		this.evaluator = new Evaluator(this.getBoard());
	}

	private void startUI(Stage primaryStage) {
		Grid grid = new Grid();

		VBox rootBox = new VBox();
		HBox buttonsBox = new HBox();

		MenuBar menuBar = getMenuBar();

		Button undoButton = new Button();
		undoButton.setText("Undo last move");
		undoButton.setOnAction(event -> undoLastMove());

		Button startButton = new Button();
		startButton.setText("Start Game");
		startButton.setOnAction(event -> continueGame());

		Separator separator = new Separator(Orientation.HORIZONTAL);
		Separator separator2 = new Separator(Orientation.HORIZONTAL);

		buttonsBox.getChildren().addAll(undoButton, startButton);

		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER);
		stackPane.getChildren().add(grid.getGridOfHexagonsInPosition(this));

		moveList = new ListView<>();
		observableList = FXCollections.observableArrayList();
		moveList.setItems(observableList);
		moveList.setEditable(false);

		Text copyright = new Text();
		copyright.setText("All rights Reserved 2018. Giorgos Patsiaouras");

		rootBox.getChildren().addAll(menuBar, buttonsBox, separator, stackPane, separator2, moveList, copyright);
		rootBox.setSpacing(20);

		primaryStage.setTitle("Omega Board Game");
		primaryStage.setScene(new Scene(rootBox, board.getBoardSize() * 120, board.getBoardSize() * 100));
		primaryStage.show();
	}

	private void continueGame() {
		if (!board.isFull() && (currentPlayer instanceof AIPlayer || currentPlayer instanceof RandomPlayer)) {
			//Platform.runLater Needed for the ui to be updated correctly during the game.
			Platform.runLater(
					() -> {
						playMove(currentPlayer.makeMove(this));
					}
			);
		} else if (board.isFull()) {
			gameOver();
		}
	}

	public void playMove(Move currentMove) {
		board.incrementIterations();
		//Print information about who played the move and which move
		System.out.println(board.getIterations() + ":\t" + currentMove.toString());
		//Add move to the list on the ui under the board
		observableList.add(currentMove);
		//Add move to the board moves history
		board.addMoveToBoard(currentMove);
		//Evaluate the board
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
			observableList.remove(observableList.size() - 1);
			swapPlayersTurn();
			continueGame();
		}
	}

	private void swapPlayersTurn() {
		currentPlayer = (currentPlayer == player1 ? player2 : player1);
	}

	@Override
	public void handle(MouseEvent event) {
		Hexagon hexagonClicked = (Hexagon) event.getTarget();
		if (currentPlayer instanceof AIPlayer) return;
		if (!hexagonClicked.isCovered() && !board.isFull()) {
			//If we are not in the middle of a move. Then this hexagon is the first of the move a.k.a white
			if (currentMove == null) {
				hexagonClicked.coverWithWhite();
				currentMove = new Move();
				currentMove.setWhiteHexagon(hexagonClicked);
				currentMove.setPlayer(currentPlayer);
			} else {
				hexagonClicked.coverWithBlack();
				currentMove.setBlackHexagon(hexagonClicked);
				playMove(currentMove);
				currentMove = null;
			}
		}
	}

	private void gameOver() {
		Score score =  evaluator.calculateScore();
		score.printScores();

		Stage stage = new Stage();
		stage.setTitle("GAME OVER");

		Text text = new Text();
		text.setText(
				player1.getName() + " had a score of " + score.getWhiteScore() + "\n" +
				player2.getName() + " had a score of " + score.getBlackScore() + "\n\n" +
						(score.getWhiteScore() > score.getBlackScore() ? player1.getName() : player2.getName()) + "\tWINS"
		);
		text.setWrappingWidth(300.0);
		text.setTextAlignment(TextAlignment.CENTER);

		Button closeButton = new Button();
		closeButton.setText("Close");
		closeButton.setOnAction(b -> stage.close());

		VBox vbox = new VBox();
		vbox.getChildren().addAll(text, closeButton);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(30.0);
		stage.setScene(new Scene(vbox, 300, 200));
		stage.showAndWait();
	}

	public Board getBoard() {
		return board;
	}

	private MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem newGame = new MenuItem("New Game");
		newGame.setOnAction(event -> resetGame());
		MenuItem exitGame = new MenuItem("Exit");
		exitGame.setOnAction(event -> exit());
		menuFile.getItems().addAll(newGame, exitGame);
		Menu menuHelp = new Menu("Help");
		MenuItem aboutItem = new MenuItem();
		aboutItem.setText("About");
		aboutItem.setOnAction(event -> {
			Stage stage = new Stage();
			stage.setTitle("About this Game");
			Text text = new Text();
			text.setText("This game is made by Giorgos. It's not the best. But it's not the worst ;)");
			text.setWrappingWidth(300.0);
			StackPane stackPane = new StackPane();
			stackPane.getChildren().add(text);
			stage.setScene(new Scene(stackPane, 400, 200));
			stage.showAndWait();
		});

		menuHelp.getItems().add(aboutItem);

		menuBar.getMenus().addAll(menuFile, menuHelp);
		return menuBar;
	}
}
