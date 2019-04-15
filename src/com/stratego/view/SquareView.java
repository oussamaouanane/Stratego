package com.stratego.view;

import javafx.scene.shape.Rectangle;

public class SquareView extends Rectangle {

	private int row;
	private int column;
	private PawnView pawn;
	
	public SquareView(int i, int j, int k, int l) {
		// TODO Auto-generated constructor stub
		super(i, j, k, l);
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
