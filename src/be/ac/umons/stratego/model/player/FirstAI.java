package be.ac.umons.stratego.model.player;

import java.util.Random;

import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

public class FirstAI extends Player {

	public FirstAI(Grid grid) {
		super(grid);
	}

	public Couple getNextMove() {
		Pawn pawn = null;

		int size = 0;
		for (Pawn c : getAlivePawns()) {
			size = new PawnInteraction(c.getSquare().getRow(), c.getSquare().getColumn(), getGrid()).availableMovement()
					.size();
			if (size > 0) {
				pawn = c;
				break;
			}
		}

		Random rand = new Random();
		int random = rand.nextInt(size);
		PawnInteraction movement = new PawnInteraction(pawn.getSquare().getRow(), pawn.getSquare().getColumn(),
				getGrid());
		Couple coord = movement.availableMovement().get(random);
		Square initialSquare = pawn.getSquare();
		Square destinationSquare = getGrid().getSquare(initialSquare.getRow() + coord.getX(),
				initialSquare.getColumn() + coord.getY());

		return new Couple(initialSquare, destinationSquare);

	}

}
