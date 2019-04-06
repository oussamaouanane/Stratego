package com.stratego.model.grid;
import com.stratego.model.grid.Square;
import com.stratego.model.pawn.Pawn;
import com.stratego.model.pawn.PawnInteractions;

/**
 * <h1>Grid</h1>
 * 
 * <p>
 * Classe permettant de repr�senter une grille de 100 instances de Square. Elle
 * poss�de plusieurs m�thodes visant � d�placer les pions, sa repr�sentation
 * formelle est un tableau � 2 dimensions (matrice carr�)
 * </p>
 * 
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
 *   0 1 2 3 4 5 6 7 8 9
 * 
 * @author O.S
 */

public class Grid {
	
	protected int currentPawns = 0;

	private final int GRID_SIZE = 10;
	private Square[][] grid = new Square[GRID_SIZE][GRID_SIZE];

	/**
	 * Constructeur par d�faut permettant d'initialiser la grille avec 100 instances
	 * de Square vierges et fait une restriction d'acc�s pour les cases repr�sentant
	 * le lac.
	 * 
	 * @see Square
	 * @see Grid#createGrid()
	 */

	public Grid() {

		createGrid();
	}

	/**
	 * M�thode permettant d'initialiser la grille avec 100 instances de Square
	 * vierges et fait une restriction d'acc�s pour les cases repr�sentant le lac.
	 */

	public void createGrid() {

		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				// Restriction d'acc�s du lac.
				if (((i == 4 || i == 5) && ((j >= 2 && j <= 3) || (j >= 6 && j <= 7)))) {
					grid[i][j] = new Square(i, j, null, false);
				} else {
					grid[i][j] = new Square(i, j, null, true);
				}
			}
		}
	}

	/**
	 * M�thode permettant de d�placer un pion d'une instance de Square d�part � une
	 * instance de Square final.
	 *
	 * @param initialDestination Instance de Square de d�part
	 * @param finalDestination   Instance de Square final
	 * @see Square
	 * @see Pawn
	 */

	public void movePawn(Square initialDestination, Square finalDestination) {

		PawnInteractions couple = new PawnInteractions(initialDestination, finalDestination);

		if (couple.canMove())
			if (finalDestination.getAccess())
				finalDestination.setPawn(initialDestination.getPawn());
			else if (finalDestination.getPawn() != null)
				couple.doFighting();

	}

	/**
	 * M�thode permettant de surligner toutes les possibilit�s de d�placement pour
	 * un pion dans une instance Square donn�e.
	 * 
	 * @param initialDestination Instance de Square de d�part
	 * @see Square
	 * @see Pawn
	 */

	public void highlightAvailableMove(Square initialDestination) {

	}

	/**
	 * Quelques accesseurs (getters) et mutateurs (setters)
	 */

	public Square getSquare(int i, int j) {
		return grid[i][j];
	}
	
	public Square[][] getGrid() {
		return grid;
	}
	
	public void setPawn(Pawn pawn, int i, int j) {
		getSquare(i, i).setPawn(pawn);
	}

}