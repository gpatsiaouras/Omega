package Omega.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {

	public static final int BLACK = 2;
	public static final int WHITE = 1;
	public static final int NOT_COVERED = -1;
	private static final double DEFAULT_STROKE_WIDTH = 3D;

	private int x;
	private int y;
	private int z;
	private int cover;
	private int parentId;
	private int groupId;

	public Hexagon() {
		this.setStroke(Color.GRAY);
		this.setStrokeWidth(DEFAULT_STROKE_WIDTH);
		this.cover = NOT_COVERED;
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
		return this.cover != NOT_COVERED;
	}

	public boolean isCoveredWithWhite() {
		return this.cover == WHITE;
	}

	public boolean isCoveredWithBlack() {
		return this.cover == BLACK;
	}

	public int getCover() {
		return this.cover;
	}

	public void coverWithWhite() {
		this.cover = WHITE;
		this.setFill(Color.WHITESMOKE);
	}

	public void coverWithBlack() {
		this.cover = BLACK;
		this.setFill(Color.BLACK);
	}

	public boolean hasSameCoverWith(Hexagon hexagon) {
		return this.getCover() == hexagon.getCover();
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}