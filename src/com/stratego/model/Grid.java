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
 * 0 - - - - - - - - -
 *   0 1 2 3 4 5 6 7 8 9
 * 
 * @author
 */

public class Grid {
	
	static final byte GRID_SIZE = 10;
	
	private Square[][] grid = new Square[GRID_SIZE][GRID_SIZE];
	public enum state {
		
		TURN_J1,
		TURN_J2,
		WIN_J1,
		WIN_J2,
	}
	
	
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
	 * 0 - - - - - - - - -
	 *   0 1 2 3 4 5 6 7 8 9
	 *  
	 *  (4,2);(4,3);(4,6);(4,7)
	 *  (5,2);(5,3);(5,6);(5,7)
	 */
	
	// Default constructor - Create the grid.
	public Grid() {
		
		createGrid();
	}
	
	public void createGrid() {
		
		for(int i = 0; i < GRID_SIZE; i++) {
			for(int j = 0; j < GRID_SIZE; j++) {
				//Setting up the unaccessible zone
				if (((i == 4 || i == 5) && j == 2) || ((i == 4 || i == 5) && j == 3) || ((i == 4 || i == 5) && j == 6) || ((i == 4 || i == 5) && j == 7)) {
					grid[i][j] = new Square(null, false);
				}
				else {
					grid[i][j] = new Square(null, true);
				}
			}
		}
	}
	
	
}
