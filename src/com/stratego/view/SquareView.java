package be.ac.umons.stratego.view;

import javafx.scene.shape.Rectangle;

/**
 * <h1>SquareView</h1>
 * 
 * <p>
 * Classe permettant de repr�senter de mani�re graphique Square, elle h�rite des
 * composants de sa classe m�re Rectangle.
 * </p>
 * 
 * @see Rectangle
 * 
 * @author O.S
 */

public class SquareView extends Rectangle {
	
	private int row;
	private int column;
	private PawnView pawnView;
	
	/**
	 * Constructeur h�rit� de Rectangle.
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
	
	public PawnView getPawnView() {
		return pawnView;
	}
	
	public void setPawnView(PawnView pawn) {
		pawnView = pawn;
		//pawnView.setSquareView(this);
	}

}
