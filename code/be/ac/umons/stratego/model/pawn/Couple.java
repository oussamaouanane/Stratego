package be.ac.umons.stratego.model.pawn;

import java.io.Serializable;

import be.ac.umons.stratego.model.grid.Square;

/**
 * <h1>Couple</h1>
 * 
 * <p>
 * Classe permettant en la representation d'un couple de deux objects de maniere
 * formelle. Il y a 2 constructeurs, un qui permet en la creation d'un couple de
 * Integer et un autre qui permet de creer un couple de Square
 * </p>
 * 
 * @see Square
 * @see Integer
 * 
 */

public class Couple implements Serializable {

	private static final long serialVersionUID = 7825296176658063388L;
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
	// la direction va de 0 à 3 et suit la direction horaire. 
	public Couple(int x, int y, int direction) {
		
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	/**
	 * Quelques accessesurs (getters) et mutateurs (setters)
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
