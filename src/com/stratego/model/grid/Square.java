package com.stratego.model.grid;

import com.stratego.model.pawn.Pawn;

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
	 * Constructeur par d�faut permettant de d�finir une instance de Square qui est
	 * compos�e d'une rang�e, une colonne, une instance Pion (ou null), et un acc�s.
	 * 
	 * @param row	 Rang�e dans Grid
	 * @param column Colonne dans Grid
	 * @param pawn   Une instance de pions ou null.
	 * @param access Acc�s
	 * 
	 * @see Grid
	 * @see Pawn
	 * 
	 */
	public Square(Pawn pawn, boolean access) {
		
		this.setPawn(pawn);
		this.access = access;
	}
	
	public Square(int row, int column, Pawn pawn, boolean access) {
		this.row = row;
		this.column = column;
		this.setPawn(pawn);
		this.access = access;
	}
	
	public boolean checkWin() {
		return false;
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
}
