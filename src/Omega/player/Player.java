package Omega.player;

import Omega.Game;
import Omega.Move;

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

	public String getType() {
		return type;
	}

	public abstract Move makeMove(Game game);
}
