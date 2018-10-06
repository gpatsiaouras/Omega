package Omega.player;

import Omega.Move;
import Omega.ui.Board;
import Omega.ui.Hexagon;

public abstract class Player {
	private int number;
	private String name;
	private String type;

	public Player() {
	}

	public Player(int number, String name, String type) {
		this.number = number;
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public abstract Move makeMove(Board board);
}
