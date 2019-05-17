package be.ac.umons.stratego.model.grid;

import java.io.Serializable;

import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

public class Grid implements Serializable {
	
	/**
	 * <h1>Grid</h1>
	 * 
	 * <p>
	 * Classe permettant de représenter une grille de 100 instances de Square. Elle
	 * possède plusieurs méthodes visant à déplacer les pions, sa représentation
	 * formelle est un tableau à 2 dimensions (matrice carré)
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
	 * 	 0 1 2 3 4 5 6 7 8 9
	 * 
	 */
	
	private static final long serialVersionUID = -4243751527990819566L;

	private final int GRID_SIZE = 10;
	private Square[][] grid = new Square[GRID_SIZE][GRID_SIZE];

	/**
	 * Constructeur par défaut permettant d'initialiser la grille avec 100 instances
	 * de Square vierges et fait une restriction d'accès pour les cases représentant
	 * le lac.
	 * 
	 * @see Square
	 * @see Grid#createGrid()
	 */

	public Grid() {
		createGrid();
	}

	/**
	 * Méthode permettant d'initialiser la grille avec 100 instances de Square
	 * vierges et fait une restriction d'accès pour les cases représentant le lac.
	 */

	public void createGrid() {

		for (int i = 9; i >= 0; i--) {
			for (int j = 0; j < GRID_SIZE; j++) {
				// Restriction d'accès du lac.
				if (((i == 4 || i == 5) && ((j >= 2 && j <= 3) || (j >= 6 && j <= 7)))) {
					grid[i][j] = new Square(9-i, j, null, false);
				} else {
					grid[i][j] = new Square(9-i, j, null, true);
				}
			}
		}
	}

	/**
	 * Méthode permettant de déplacer un pion d'une instance de Square départ à une
	 * instance de Square final.
	 *
	 * @param initialDestination Instance de Square de départ
	 * @param finalDestination   Instance de Square final
	 * @see Square
	 * @see Pawn
	 */

	public void movePawn(Square initialDestination, Square finalDestination) {

		PawnInteraction couple = new PawnInteraction(initialDestination, finalDestination, this);

		if (couple.isMovePossible())
			if (finalDestination.getAccess())
				finalDestination.setPawn(initialDestination.getPawn());
			else if ((finalDestination.getPawn() != null) && (finalDestination.getPawn().getPlayer() == 2))
				couple.doFighting();

		finalDestination.getPawn().setSquare(finalDestination);

	}

	public Square getSquare(int i, int j) {
		return grid[9-i][j];
	}

	public Square[][] getGrid() {
		return grid;
	}

	public void setPawn(Pawn pawn, int i, int j) {
		getSquare(i, i).setPawn(pawn);
	}

}
