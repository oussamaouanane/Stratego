package com.stratego.model;

/**
 * Square --- Class that defines a Square object, a Square object contains a
 * UNIQUE Pawn Object - Pawns can only move through Square objects. A Square
 * object is accessible only if no Pawn is contained in it. (empty, Pawn object
 * == null)
 * 
 * @author O.S
 */

public class Square {

	private Pawn pawn;
	private boolean access;
	private byte row;
	private byte column;

	/**
	 * Default constructor
	 * 
	 * @param pawn   A Pawn object.
	 * @param access Says whenever the Square object is accessible
	 * 
	 *               pawn =/= null => access = false
	 */
	public Square(byte row, byte column, Pawn pawn, boolean access) {

		this.row = row;
		this.column = column;
		this.setPawn(pawn);
		
		if (pawn != null)
			this.access = access;

	}

	/**
	 * Couple of getters and setters.
	 */
	
	// @return row The row of the Square object
	public byte getRow() {
		return row;
	}
	
	// @return row The row of the Square object
	public byte getColumn() {
		return column;
	}

	// @return pawn A Pawn Object.
	public Pawn getPawn() {
		return pawn;
	}

	// @return access A boolean that says whether the Square object is accessible or
	// not.
	public boolean getAccess() {
		return access;
	}

	// Add a Pawn object in a Square object.
	public void setPawn(Pawn pawn) {
		if(this.pawn != null)
			removePawn();
		this.pawn = pawn;
		// access = false;
	}

	// Set the access of the Square object
	public void setAccess(boolean access) {
		this.access = access;
	}

	// Remove a Pawn object from a Square object.
	public void removePawn() {
		pawn = null;
		access = true;
	}
}
