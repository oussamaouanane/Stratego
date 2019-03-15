package com.stratego.model;

/**
 * Couple - Class that represents a Couple object, contains two Pawns elements.
 * 
 * @author O.S
 */
public class Couple {

	private Pawn pawnA;
	private Pawn pawnB;

	private Square squareA;
	private Square squareB;

	// Pawn constructor
	public Couple(Pawn pawnA, Pawn pawnB) {

		this.pawnA = pawnA;
		this.pawnB = pawnB;
	}

	// Square constructor
	public Couple(Square squareA, Square squareB) {

		this.squareA = squareA;
		this.squareB = squareB;
	}

	// Getters
	public Pawn getPawnA() {
		return pawnA;
	}

	public Pawn getPawnB() {
		return pawnB;
	}

	public Square getSquareA() {
		return squareA;
	}

	public Square getSquareB() {
		return squareB;

	}

}
