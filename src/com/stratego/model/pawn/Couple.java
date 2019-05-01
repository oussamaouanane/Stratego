package be.ac.umons.stratego.model.pawn;

import be.ac.umons.stratego.model.grid.Square;

/**
 * <h1>Couple</h1>
 * 
 * <p>
 * Classe permettant en la representation d'un couple de deux objects de manière
 * formelle. Il y a 2 constructeurs, un qui permet en la création d'un couple de
 * Integer et un autre qui permet de créer un couple de Square
 * </p>
 * 
 * @see Square
 * @see Integer
 * @author O.S
 */
public class Couple {

	private int x;
	private int y;

	private Square squareA;
	private Square squareB;

	// Constructeur de couple d'Integer
	public Couple(int i, int j) {

		this.x = i;
		this.y = j;
	}

	// Constructeur de couple de Square
	public Couple(Square squareA, Square squareB) {

		this.squareA = squareA;
		this.squareB = squareB;
	}

	/**
	 * Quelques accesseurs (getters) et mutateurs (setters)
	 */

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Square getSquareA() {
		return squareA;
	}

	public Square getSquareB() {
		return squareB;

	}

}

