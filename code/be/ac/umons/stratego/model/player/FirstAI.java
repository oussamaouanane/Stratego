package be.ac.umons.stratego.model.player;

import java.util.ArrayList;
import java.util.Collections;
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
 * <p>
 * Classe permettant de representer la premiere intelligence artificielle, elle
 * navigue dans la liste des pions vivants, et des qu'un pion pouvant se
 * deplacer au moins d'une case est trouve, il est aussitot selectionne. On
 * genere un nombre entre 0 <= taille de la liste de ses mouvements et on prend
 * le n-ieme deplacement de la liste.
 * </p>
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
		// Partie ou on selectionne un pion.
		int size = 0;

		ArrayList<Pawn> alivePawns = game.getAI().getAlivePawns();
		Collections.shuffle(alivePawns);

		for (Pawn p : alivePawns) {
			PawnInteraction couple = new PawnInteraction(p.getSquare().getRow(), p.getSquare().getColumn(), getGrid());
			if ((p.getRange() >= 1) && ((couple.availableMovement() != null)
					&& (!couple.availableMovement().isEmpty()))) {
				size = new PawnInteraction(p.getSquare().getRow(), p.getSquare().getColumn(), getGrid())
						.availableMovement().size();
				pawn = p;
			}
		}

		int random = ThreadLocalRandom.current().nextInt(size);
		Couple move = new PawnInteraction(pawn.getSquare().getRow(), pawn.getSquare().getColumn(), getGrid())
				.availableMovement().get(random);

		Square initialSquare = pawn.getSquare();
		Square destinationSquare = getGrid().getSquare(move.getX(), move.getY());

		return new Couple(initialSquare, destinationSquare);

	}
}