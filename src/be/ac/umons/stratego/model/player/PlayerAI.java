package be.ac.umons.stratego.model.player;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.pawn.Couple;

public class PlayerAI extends Player {
	
	private int ai;
	private FirstAI player1;
	private SecondAI player2;

	public PlayerAI(Grid grid, int ai, GameProcess game) {
		super(grid);
		this.ai = ai;
		if (ai == 1)
			player1 = new FirstAI(grid);
		else
			player2 = new SecondAI(grid, game);
	}
	
	public Couple getNextMove() {
		Couple couple;
		
		if (ai == 1)
			couple = player1.getNextMove();
		else
			couple = player2.getNextMove();
		return couple;
		
	}

}
