package com.stratego.model;

/**
 * <h1>Square</h1>
 * 
 * <p>
 * Classe qui permettant de repr�senter un carr� (une case), chaque carr� a la
 * possiblit� de contenir un UNIQUE pion (instance de Pawn) - Les pions se
 * d�placent UNIQUEMENT � travers les carr�s. Un carr� est accessible s'il n'est
 * pas restreint (e.g lac). Son accessiblit� est g�r�e � l'aide d'un attribut
 * bool�en nomm� access.
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
	 * Constructeur par d�faut,
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
