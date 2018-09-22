package Omega;

public class Move {

	private Hexagon whiteHexagon;
	private Hexagon blackHexagon;
	private Player player;

	public Move() {
	}

	public Move(Hexagon whiteHexagon, Hexagon blackHexagon, Player player) {
		this.whiteHexagon = whiteHexagon;
		this.blackHexagon = blackHexagon;
		this.player = player;
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
}
