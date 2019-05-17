package be.ac.umons.stratego.model.player;

import java.util.concurrent.ThreadLocalRandom;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

/**
 * <h1>FirstAI</h1>
 * 
 * <p> Classe permettant de repr�senter la premi�re intelligence artificielle, elle
 * navigue dans la liste des pions vivants, et d�s qu'un pion pouvant se
 * d�placer au moins d'une case est trouv�, il est aussit�t s�lectionn�. On
 * g�n�re un nombre entre 0 <= taille de la liste de ses mouvements et on prend
 * le n-i�me d�placement de la liste. </p>
 */

public class FirstAI extends Player {

	private static final long serialVersionUID = 8096414510502001186L;
	private GameProcess game;

	public FirstAI(Grid grid, GameProcess game) {
		super(grid);
		this.game = game;
	}

	public Couple getNextMove() {
		Pawn pawn = null;
		
		
		// Partie o� on s�lectionne un pion.
		
		int size = 0;
		for (Pawn c : game.getAI().getAlivePawns()) {
			if (!new PawnInteraction(9 - c.getSquare().getRow(), c.getSquare().getColumn(), getGrid())
					.availableMovement().isEmpty())
			size = new PawnInteraction(9 - c.getSquare().getRow(), c.getSquare().getColumn(), getGrid())
					.availableMovement().size();
			if (size > 0) {
				pawn = c;
				break;
			}
		}
		
		// Partie o� on g�n�re un nombre compris entre 1 <= nombre <= size-1 (car le dernier �l�ment est exclus)

		int random = ThreadLocalRandom.current().nextInt(0, size);
		PawnInteraction movement = new PawnInteraction(pawn.getSquare().getRow(), pawn.getSquare().getColumn(),
				getGrid());
		Couple coord = movement.availableMovement().get(random);
		Square initialSquare = pawn.getSquare();
		Square destinationSquare = getGrid().getSquare(initialSquare.getRow() + coord.getX(),
				initialSquare.getColumn() + coord.getY());

		return new Couple(initialSquare, destinationSquare);

	}

}
