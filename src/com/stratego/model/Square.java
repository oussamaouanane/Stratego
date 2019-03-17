package com.stratego.model;

/**
 * <h1>Square</h1>
 * 
 * <p>
 * Classe qui permettant de représenter un carré (une case), chaque carré a la
 * possiblité de contenir un UNIQUE pion (instance de Pawn) - Les pions se
 * déplacent UNIQUEMENT à travers les carrés. Un carré est accessible s'il n'est
 * pas restreint (e.g lac). Son accessiblité est gérée à l'aide d'un attribut
 * booléen nommé access.
 * </p>
 * 
 * @author O.S
 * @see Pawn
 */

public class Square {

	private Pawn pawn;
	private boolean access;
	private int row;
	private int column;

	/**
	 * Constructeur par défaut,
	 * 
	 * @param pawn   A Pawn object.
	 * @param access Says whenever the Square object is accessible
	 * 
	 *               pawn =/= null => access = false
	 */
	public Square(int row, int column, Pawn pawn, boolean access) {

		this.row = row;
		this.column = column;
		this.setPawn(pawn);

		if (pawn != null)
			this.access = access;

	}

	/**
	 * Quelques accesseurs (getters) et mutateurs (setters)
	 */

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
		if (this.pawn != null)
			removePawn();
		this.pawn = pawn;
		// access = false;
	}

	public void setAccess(boolean access) {
		this.access = access;
	}

	public void removePawn() {
		pawn = null;
		access = true;
	}
}
