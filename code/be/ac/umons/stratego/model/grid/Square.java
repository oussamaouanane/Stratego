package be.ac.umons.stratego.model.grid;

import java.io.Serializable;

import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.pawn.Pawn;

/**
 * <h1>Square</h1>
 * 
 * <p>
 * Classe permettant de modeliser un carre (une case de la grille), chaque carre
 * a la possiblite de contenir un UNIQUE pion (instance de Pawn) - Les pions se
 * deplacent UNIQUEMENT a travers les carres. Un carre est accessible s'il n'est
 * pas restreint (e.g lac). Son accessiblite est geree a l'aide d'un attribut
 * booleen nomme access.
 * </p>
 * 
 * @see Pawn
 */

public class Square implements Serializable {

	private static final long serialVersionUID = -2593260130253444047L;
	private Pawn pawn;
	private boolean access;
	private int row;
	private int column;

	// Coordonnees des drapeaux dans la grille
	// @see Grid
	public static int[] flagA = new int[2];
	public static int[] flagB = new int[2];

	/**
	 * Constructeur par defaut.
	 * 
	 * @param row    Rangee dans grid
	 * @param column Colonne dans grid
	 * @param pawn   Une instance de Pawn
	 * @param access Acces
	 * 
	 * @see Grid
	 * @see Pawn
	 */
	
	public Square(int row, int column, Pawn pawn, boolean access) {

		this.row = row;
		this.column = column;
		setPawn(pawn);
		this.access = access;
	}
	
	/**
	 * Constructeur pour les JUnit
	 * 
	 * @param row    Rangee dans Grid
	 * @param column Colonne dans Grid
	 * @param pawn   Une instance de Pawn ou null.
	 * @param access Acces
	 * 
	 * @see Grid
	 * @see Pawn
	 * 
	 */
	
	public Square(Pawn pawn, boolean access) {

		setPawn(pawn);
		this.access = access;
	}

	public boolean checkWin() {
		return false;
	}

	// Quelques accesseurs et mutateurs

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public boolean getAccess() {
		return access;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;

	}

	public void setAccess(boolean access) {
		this.access = access;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void removePawn() {
		pawn = null;
		access = true;
	}

	public static int[] getFlagPosition(int player) {

		int[] flag = null;
		// Retourne la position du drapeau selon le joueur
		switch (player) {
		case 1:
			flag = flagA;
		case 2:
			flag = flagB;
		}
		return flag;
	}
}
