package com.stratego.view;

import javafx.scene.shape.Rectangle;

public class SquareView extends Rectangle {

	private int row;
	private int column;
	private PawnView pawn;
	
	/**
	 * Constructeur hérité de Rectangle.
	 * 
	 * @param x Position horizontale
	 * @param y Position verticale
	 * @param width Largeur
	 * @param height Longueur
	 */
		
	public SquareView(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public PawnView getPawn() {
		return pawn;
	}
	
	public void setPawn(PawnView pawn) {
		this.pawn = pawn;
	}

}
