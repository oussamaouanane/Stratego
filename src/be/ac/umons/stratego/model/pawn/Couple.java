package be.ac.umons.stratego.model.pawn;

import be.ac.umons.stratego.model.grid.Square;

/**
 * <h1>Couple</h1>
 * 
 * <p>
 * Classe permettant en la representation d'un couple de deux objects de mani�re
 * formelle. Il y a 2 constructeurs, un qui permet en la cr�ation d'un couple de
 * Integer et un autre qui permet de cr�er un couple de Square
 * </p>
 * 
 * @see Square
 * @see Integer
 * @author O.S
 */
public class Couple {

	private int x;
	private int y;
	private int direction;

	private Square squareA;
	private Square squareB;

	// Constructeur de couple d'Integer
	public Couple(int x, int y) {

		this.x = x;
		this.y = y;
	}

	// Constructeur de couple de Square
	public Couple(Square squareA, Square squareB) {

		this.squareA = squareA;
		this.squareB = squareB;
	}
	
	// Constructeur pour couple d'Integer + direction
	// la direction va de 0 � 3 et suit la direction horaire. 
	public Couple(int x, int y, int direction) {
		
		this.x = x;
		this.y = y;
		this.direction = direction;
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
	
	public int getDirection() {
		return direction;
	}

	public Square getSquareA() {
		return squareA;
	}

	public Square getSquareB() {
		return squareB;

	}

}
