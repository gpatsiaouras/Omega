package Omega;

public class Hexagon {

	private int x;
	private int y;
	private int z;
	// -1 is empty, 0 is white, 1 is black
	private int cover;

	public Hexagon() {
		this.cover = -1;
	}

	public Hexagon(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.cover = -1;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public boolean matchesCoordinates(int regX, int regY, int regZ) {
		return this.x == regX && this.y == regY && this.z == regZ;
	}

	public boolean isCovered() {
		return this.cover != -1;
	}

	public boolean isCoveredWithWhite() {
		return this.cover == 0;
	}

	public boolean isCoveredWithBlack() {
		return this.cover == 1;
	}

	public void cover(int code) {
		this.cover = code;
	}

	public void coverWithWhite() {
		this.cover = 0;
	}

	public void coverWithBlack() {
		this.cover = 1;
	}
}
