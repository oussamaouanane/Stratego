package be.ac.umons.stratego.controller;

import java.util.ArrayList;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

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

	GameProcess game;

	public GridController(GameProcess game) {
		this.game = game;
	}

	public boolean isMovePossible(Square initialSquare, Square destinationSquare) {

		PawnInteraction squareCouple = new PawnInteraction(initialSquare, destinationSquare, game.getGrid());
		return squareCouple.isMovePossible();
	}

	public ArrayList<Couple> SquareToHighlight(Square square) {

		int squareRow = square.getRow();
		int squareColumn = square.getColumn();
		PawnInteraction couple = new PawnInteraction(squareRow, squareColumn, game.getGrid());
		ArrayList<Couple> squareCouple = new ArrayList<Couple>();

		//for (Couple c : couple.availableMovement()) {
			//Couple squareCoupleAdd = new Couple(c.getX(), c.getY());
			//squareCouple.add(squareCoupleAdd);
		int cnt = 0;
		for (int i: couple.availableMovementScout()) {
			if (cnt == 0 || cnt == 2)
				squareCouple.add(new Couple(i, 0));
			else if (cnt == 1 || cnt == 3)
				squareCouple.add(new Couple(0, i));
			cnt++;
		}
		return squareCouple;
	}

}
