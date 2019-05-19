package be.ac.umons.stratego.model.player;

import java.util.ArrayList;
import java.util.Collections;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

public class SecondAI extends Player {

	/**
	 * <h1>SecondAI</h1>
	 * 
	 * <p>
	 * Classe permettant de representer la deuxieme intelligence artificielle, on
	 * cherche d'abord a fixer la position des deux drapeaux (allie et ennemi). Si
	 * l'ecart de score est de plus de 5, alors on ramene les pions les plus proches
	 * vers le drapeau pour defendre, dans le cas contraire, on envoie les pions
	 * vers le drapeau ennemi.
	 * </p>
	 */

	private static final long serialVersionUID = 5541423007596275754L;
	private GameProcess game;
	private Square lastInitialSquare = null;

	public SecondAI(Grid grid, GameProcess game) {
		super(grid);
		this.game = game;
	}

	public Couple getNextMove() {

		// Choix du drapeau vers lequel se diriger.
		Pawn flag = null;
		Square flagSquare = null;

		// Si l'ecart entre les deux joueurs est > 5, alors on cherche a rabattre les
		// pions vers le drapeau de l'ai car le joueur 1 prend l'avantage.
		if (game.getScore(1) - game.getScore(2) > 5) {
			for (Pawn pawn : game.getAlivePawn(2)) {
				if (pawn.isPawnA(11)) {
					flag = pawn;
					flagSquare = flag.getSquare();
				}
			}
		}

		// Sinon vers leur drapeau.
		else {
			for (Pawn pawn : game.getAlivePawn(1)) {
				if (pawn.isPawnA(11)) {
					flag = pawn;
					flagSquare = flag.getSquare();
				}
			}
		}

		// On cherche le point de depart
		ArrayList<Pawn> alivePawns = game.getAlivePawn(2);
		Collections.shuffle(alivePawns);

		Pawn selectedPawn = null;
		// Ratio (maximale)
		int ratio = 9;
		// Calcul du ratio le plus proche du drapeau
		for (Pawn pawn : alivePawns) {
			Square pawnSquare = pawn.getSquare();
			if ((pawn.getRange() == 1)
					&& (!(new PawnInteraction(pawnSquare.getRow(), pawnSquare.getColumn(), game.getGrid()))
							.availableMovement().isEmpty())) {
				// Distance entre la case du pion et le drapeau
				int rowDistance = Math.abs(pawnSquare.getRow() - flagSquare.getRow());
				int columnDistance = Math.abs(pawnSquare.getColumn() - flagSquare.getColumn());
				// Ratio du mouvement
				int tempRatio = 0;

				if ((columnDistance != 0) && (rowDistance != 0))
					tempRatio = rowDistance / columnDistance;
				else
					tempRatio = 2;

				if ((tempRatio < ratio))
					ratio = tempRatio;
				selectedPawn = pawn;
			}
		}

		Square initialSquare = selectedPawn.getSquare();

		// On cherche le point d'arrivee
		Couple selectedCouple = null;
		// Ratio (maximale)
		ratio = 9;
		// Calcul du ratio le plus proche du drapeau
		PawnInteraction couple = new PawnInteraction(initialSquare.getRow(), initialSquare.getColumn(), game.getGrid());
		for (Couple c : couple.availableMovement()) {
			// Distance entre la case de depart + mouvement et le drapeau
			int rowDistance = Math.abs(c.getX() - flagSquare.getRow());
			int columnDistance = Math.abs(c.getY() - flagSquare.getColumn());
			int tempRatio = 0;

			if ((columnDistance != 0) && (rowDistance != 0))
				tempRatio = rowDistance / columnDistance;
			else
				tempRatio = 2;

			if ((tempRatio < ratio)) {
				// Ne pas revenir en arriere
				if ((lastInitialSquare != null)
						&& ((lastInitialSquare.getRow() != c.getX()) && (lastInitialSquare.getColumn() != c.getY()))) {
					ratio = tempRatio;
					selectedCouple = c;
				// Premier mouvement
				} else if (lastInitialSquare == null) {
					ratio = tempRatio;
					selectedCouple = c;
				// Reviens en arriere si pas autre choix
				} else if ((lastInitialSquare != null)
						&& ((lastInitialSquare.getRow() == c.getX()) && (lastInitialSquare.getColumn() == c.getY()))
						&& couple.availableMovement().size() == 1) {
					ratio = tempRatio;
					selectedCouple = c;
				}
			}
		}

		Square destinationSquare = game.getGrid().getSquare(selectedCouple.getX(), selectedCouple.getY());
		lastInitialSquare = initialSquare;
		return new Couple(initialSquare, destinationSquare);

	}

}
