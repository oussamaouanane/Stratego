package com.stratego.model;

import com.stratego.model.Square;

/**
 * Grid --- Class that defines a Grid object, it contains 100 Square object
 * REPRESENTATION:
 * 
 * 9 - - - - - - - - - -
 * 8 - - - - - - - - - - 
 * 7 - - - - - - - - - - 
 * 6 - - - - - - - - - - 
 * 5 - - + + - - + + - -
 * 4 - - + + - - + + - - 
 * 3 - - - - - - - - - - 
 * 2 - - - - - - - - - - 
 * 1 - - - - - - - - - - 
 * 0 - - - - - - - - - -
 * 0 1 2 3 4 5 6 7 8 9
 * 
 * @author O.S
 */

public class Grid {

	private final byte GRID_SIZE = 10;

	private Square[][] grid = new Square[GRID_SIZE][GRID_SIZE];
	

	/**
	 * Initialize the grid and setting up the unaccessible zone 
	 * 9 - - - - - - - - - -
	 * 8 - - - - - - - - - - 
	 * 7 - - - - - - - - - - 
	 * 6 - - - - - - - - - - 
	 * 5 - - + + - - + + - - 
	 * 4 - - + + - - + + - - 
	 * 3 - - - - - - - - - - 
	 * 2 - - - - - - - - - -
	 * 1 - - - - - - - - - - 
	 * 0 - - - - - - - - - - 
	 * 	 0 1 2 3 4 5 6 7 8 9
	 * 
	 * (4,2);(4,3);(4,6);(4,7) (5,2);(5,3);(5,6);(5,7)
	 * 
	 * Movements:
	 * 
	 * UP = j++ DOWN = j-- RIGHT = i++ LEFT = i--
	 */

	// Default constructor - Create the grid.
	public Grid() {

		createGrid();
	}

	public void createGrid() {

		for (byte i = 0; i < GRID_SIZE; i++) {
			for (byte j = 0; j < GRID_SIZE; j++) {
				// Setting up the unaccessible zone
				if (((i == 4 || i == 5) && ((j >= 2 && j <= 3) || (j >= 6 && j <= 7)))) {
					grid[i][j] = new Square(i, j, null, false);
				} else {
					grid[i][j] = new Square(i, j, null, true);
				}
			}
		}
	}

	public void movePawn(Square initialDestination, Square finalDestination) {
		
		PawnInteractions couple = new PawnInteractions(initialDestination, finalDestination);
		
		if (finalDestination.getAccess())
			finalDestination.setPawn(initialDestination.getPawn());
		else if (finalDestination.getPawn() != null)
			couple.doFighting();

			
	}

	public Square getSquare(byte i, byte j) {

		return grid[i][j];
	}

}