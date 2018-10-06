package Omega;

import java.util.ArrayList;
import java.util.List;

public class Score {
	private List<Integer> whiteScoreHistory;
	private List<Integer> blackScoreHistory;
	private long whiteScore;
	private long blackScore;

	public Score() {
		this.whiteScoreHistory = new ArrayList<>();
		this.blackScoreHistory = new ArrayList<>();
	}

	public long getWhiteScore() {
		return whiteScore;
	}

	public void setWhiteScore(long whiteScore) {
		this.whiteScore = whiteScore;
	}

	public long getBlackScore() {
		return blackScore;
	}

	public void setBlackScore(long blackScore) {
		this.blackScore = blackScore;
	}

	public List<Integer> getWhiteScoreHistory() {
		return whiteScoreHistory;
	}

	public void setWhiteScoreHistory(List<Integer> whiteScoreHistory) {
		this.whiteScoreHistory = whiteScoreHistory;
	}

	public List<Integer> getBlackScoreHistory() {
		return blackScoreHistory;
	}

	public void setBlackScoreHistory(List<Integer> blackScoreHistory) {
		this.blackScoreHistory = blackScoreHistory;
	}

	public void printScores() {
		System.out.println("Scores: ");
		System.out.print("White: ");
		for (int score : whiteScoreHistory) {
			System.out.print(score + "*");
		}
		System.out.println(" = " + whiteScore);
		System.out.print("Black: ");
		for (int score : blackScoreHistory) {
			System.out.print(score + "*");
		}
		System.out.println(" = " + blackScore);
	}
}
