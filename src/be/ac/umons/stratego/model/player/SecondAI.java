package be.ac.umons.stratego.model.player;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

public class SecondAI extends Player {

	private GameProcess game;

	public SecondAI(Grid grid, GameProcess game) {
		super(grid);
		this.game = game;
	}

	public Couple getNextMove() {

		Pawn flag;
		Square flagSquare = null;

		// Si l'écart entre les deux joueurs est de 5. Alors on cherchera à rabattre les pions vers 
		if (game.getScore(1) - game.getScore(2) > 5) {
			for (Pawn pawn : getAlivePawns()) {
				if (pawn.isPawnA(11)) {
					flag = pawn;
					flagSquare = flag.getSquare();
				}
			}
		}

		else {
			for (Pawn pawn : game.getUser().getAlivePawns()) {
				if (pawn.isPawnA(11)) {
					flag = pawn;
					flagSquare = flag.getSquare();
				}
			}
		}

		// CETTE PARTIE SERT A CHERCHER LA CASE DE DEPART, QUEL PION DE QUEL CASE ON VA
		// CHOISIR
		Pawn selectedPawn = null;
		int ratio = 9;
		// Calcul du ratio, le pion le plus proche du drapeau
		for (Pawn pawn : getAlivePawns()) {
			if (!pawn.isPawnA(11)) {
				PawnInteraction couple = new PawnInteraction(pawn.getSquare().getRow(), pawn.getSquare().getColumn(),
						game.getGrid());
				int rowDistance = Math.abs(pawn.getSquare().getRow() - flagSquare.getRow());
				int columnDistance = Math.abs(pawn.getSquare().getColumn() - flagSquare.getColumn());
				int tempRatio = rowDistance / columnDistance;
				if ((tempRatio < ratio) && !(rowDistance == 0 || columnDistance == 0)
						&& couple.availableMovement().size() > 0) {
					ratio = tempRatio;
					selectedPawn = pawn;
				}
			}
		}

		// CETTE PARTIE SERT A CHERCHER LA CASE OU VA ALLER LE PION
		Couple selectedCouple = null;
		ratio = 9;
		Square initialSquare = selectedPawn.getSquare();
		PawnInteraction couple = new PawnInteraction(initialSquare.getRow(), initialSquare.getColumn(), game.getGrid());
		for (Couple c : couple.availableMovement()) {
			int rowDistance = Math.abs(initialSquare.getRow() + c.getX() - flagSquare.getRow());
			int columnDistance = Math.abs(initialSquare.getColumn() + c.getY() - flagSquare.getColumn());
			int tempRatio = rowDistance / columnDistance;
			if ((tempRatio < ratio) && !(rowDistance == 0 || columnDistance == 0)) {
				ratio = tempRatio;
				selectedCouple = couple;
			}
		}

		Square destinationSquare = game.getGrid().getSquare(initialSquare.getRow() + selectedCouple.getX(),
				initialSquare.getColumn() + selectedCouple.getY());

		return new Couple(initialSquare, destinationSquare);

	}

}
