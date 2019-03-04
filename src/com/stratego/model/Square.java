package com.stratego.model;

/**
 * Square --- Class that defines a Square object, a Square object contains a UNIQUE Pawn Object - Pawns can only move through Square objects. 
 * A Square object is accessible only if no Pawn is contained in it. (empty, Pawn object == null)
 * @author
 */


public class Square {

	private Pawn pawn;
	private boolean access;
	
	
	/**
	 * Default constructor 
	 * @param pawn A Pawn object.
	 * @param access Says whenever the Square object is accessible
	 * 
	 * pawn =/= null => access = false
	 */
	public Square(Pawn pawn, boolean access) {
		
		this.pawn = pawn;
		if (pawn != null)
				this.access = access;
		
	}
	
	/**
	 * Couple of accessors
	 * @return pawn Pawn object 
	 * @return access Return a boolean
	 */
	
	public Pawn getPawn() {
		return pawn;
	}
	
	public boolean isAccessible() {
		return access;
	}
	
	//Add a Pawn object in a Square object
	public void addPawnToSquare(Pawn pawn) {
		this.pawn = pawn;
	}
	
	//Remove a Pawn object from a Square object
	public void removePawnFromSquare() {
		pawn = null;
		access = true;
	}
}

