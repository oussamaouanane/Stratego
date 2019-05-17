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
 * <p> Classe permettant de représenter la première intelligence artificielle, elle
 * navigue dans la liste des pions vivants, et dès qu'un pion pouvant se
 * déplacer au moins d'une case est trouvé, il est aussitôt sélectionné. On
 * génère un nombre entre 0 <= taille de la liste de ses mouvements et on prend
 * le n-ième déplacement de la liste. </p>
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
		
		
		// Partie où on sélectionne un pion.
		
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
		
		// Partie où on génère un nombre compris entre 1 <= nombre <= size-1 (car le dernier élément est exclus)

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
