package be.ac.umons.stratego.model.player;

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
	
	private static final long serialVersionUID = 4275672829736663379L;
	private GameProcess game;

	public SecondAI(Grid grid, GameProcess game) {
		super(grid);
		this.game = game;
	}

	public Couple getNextMove() {

		Pawn flag;
		Square flagSquare = null;
		
		// Choix du drapeau vers le quel se diriger:

		// Si l'ecart entre les deux joueurs est de 5. Alors on cherchera a rabattre les
		// pions vers son drapeau.
		if (game.getScore(1) - game.getScore(2) > 5) {
			for (Pawn pawn : game.getAI().getAlivePawns()) {
				if (pawn.isPawnA(11)) {
					flag = pawn;
					flagSquare = flag.getSquare();
				}
			}
		}
		
		// Sinon vers leur drapeau.

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
		for (Pawn pawn : game.getAI().getAlivePawns()) {
			if (!pawn.isPawnA(11) && !pawn.isPawnA(1)) {
				PawnInteraction couple = new PawnInteraction(pawn.getSquare().getRow(), pawn.getSquare().getColumn(),
						game.getGrid());
				int rowDistance = Math.abs(pawn.getSquare().getRow() - flagSquare.getRow());
				int columnDistance = Math.abs(pawn.getSquare().getColumn() - flagSquare.getColumn());
				int tempRatio = 0;
				if (columnDistance != 0)
					tempRatio = rowDistance / columnDistance;
				if ((tempRatio < ratio) && !(rowDistance == 0 || columnDistance == 0)
						&& (!(couple.availableMovement().isEmpty()) && couple.availableMovement().size() > 0)) {
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

		Square destinationSquare = game.getGrid().getSquare(selectedCouple.getX(),
				selectedCouple.getY());

		return new Couple(initialSquare, destinationSquare);

	}

}
