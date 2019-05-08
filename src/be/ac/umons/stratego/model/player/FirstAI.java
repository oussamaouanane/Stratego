package be.ac.umons.stratego.model.player;

import java.util.Random;

import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

public class FirstAI extends Player {

	public FirstAI(Grid grid) {
		super(grid);
	}
	
	public void pawnPlacement() {
		Grid grid = getGrid();

		}
	
	
	public Couple getNextMove() {
		Couple move;
		Pawn pawn = null;
		
		int size = 0;
		for(Pawn c: getAlivePawns()) {
			size = new PawnInteraction(c.getSquare().getRow(), c.getSquare().getColumn(), getGrid()).availableMovement().size();
			if (size > 0) {
				pawn = c;
				break;
			}
		}
		
		Random rand = new Random();
		int random = rand.nextInt(size);
		PawnInteraction movement = new PawnInteraction(pawn.getSquare().getRow(), pawn.getSquare().getColumn(), getGrid());
		move = movement.availableMovement().get(random);
		
		return move;

}
	

}
