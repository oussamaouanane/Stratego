package be.ac.umons.stratego.controller;

import java.util.ArrayList;

import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.PawnInteraction;
import be.ac.umons.stratego.view.GameView;
import be.ac.umons.stratego.view.PawnView;
import javafx.scene.input.MouseEvent;

/**
 * <h1>GridController</h1>
 * 
 * <p>
 * Classe permettant de coordonner le plateau de la partie logique et la partie
 * graphique du jeu.
 * </p>
 * 
 * @see Grid
 * @see InGameState
 */

public class GridController {

	private Grid grid;

	public GridController(Grid grid) {
		this.grid = grid;
	}

	public boolean isMovePossible(Square initialSquare, Square destinationSquare) {

		PawnInteraction couple = new PawnInteraction(initialSquare, destinationSquare, grid);
		return couple.isMovePossible();
	}

	public void movePawn(Square initialSquare, Square destinationSquare) {

		// Couple (Square, Square)
		PawnInteraction couple = new PawnInteraction(initialSquare, destinationSquare, grid);
		// Condition: Si le carré de destination contient un pion ET que le pion est de
		// l'équpe adverse alors duel.
		if ((destinationSquare.getPawn() != null)
				&& (destinationSquare.getPawn().getPlayer() != initialSquare.getPawn().getPlayer())) {
			couple.doFighting();
			// Sinon si le carré est vide déplacer le pion
		} else if (destinationSquare.getPawn() == null) {
			destinationSquare.setPawn(initialSquare.getPawn());
		}
		// Sinon le pion présent dans la case finale est un allié donc ne rien faire.
	}

	public ArrayList<Integer> evaluation(Square lol) {
		return new PawnInteraction(lol.getRow(), lol.getColumn(), grid).availableMovement();
	}

	public ArrayList<Couple> highlightAvailableMove(Square initialSquare) {

		int row = initialSquare.getRow();
		int column = initialSquare.getColumn();
		ArrayList<Couple> squareToHighlight = new ArrayList<Couple>();

		// Couple (int, int)
		PawnInteraction couple = new PawnInteraction(row, column, grid);
		ArrayList<Integer> availableMovements;
		// if (initialSquare.getPawn().getRank() == 2)
		// availableMovements = couple.availableMovementScout();
		// else
		availableMovements = couple.availableMovement();

		for (int i : availableMovements) {
			for (int j = 0; j <= i; j++) {
				if (i == 0 || i == 3)
					squareToHighlight.add(new Couple(initialSquare.getRow() + j, initialSquare.getColumn()));
				else
					squareToHighlight.add(new Couple(initialSquare.getRow(), initialSquare.getColumn() + j));
			}
		}

		return squareToHighlight;

	}

}
