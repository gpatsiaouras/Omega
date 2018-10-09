package Omega;

import Omega.player.Player;
import Omega.ui.Hexagon;

import java.io.Serializable;

public class Move implements Serializable {

	private Hexagon whiteHexagon;
	private Hexagon blackHexagon;
	private Player player;

	public Move() {
	}

	public Hexagon getWhiteHexagon() {
		return whiteHexagon;
	}

	public void setWhiteHexagon(Hexagon whiteHexagon) {
		this.whiteHexagon = whiteHexagon;
	}

	public Hexagon getBlackHexagon() {
		return blackHexagon;
	}

	public void setBlackHexagon(Hexagon blackHexagon) {
		this.blackHexagon = blackHexagon;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return player.getType() + " " + player.getName() +
				": white;" + whiteHexagon.getX() + "," + whiteHexagon.getY() +
				" black;" + blackHexagon.getX() + "," + blackHexagon.getY();
	}
}
