package be.ac.umons.stratego.model.player;

import java.io.Serializable;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.pawn.Couple;

/**
 * <h1>PlayerAI</h1>
 * 
 * <p>
 * Classe permettant de representer une intelligence artificielle.
 * </p>
 * 
 * @see FirstAI
 * @see SecondAI
 */

public class PlayerAI extends Player implements Serializable {

	private static final long serialVersionUID = -889282606704364819L;
	private int ai;
	private FirstAI player1;
	private SecondAI player2;

	public PlayerAI(Grid grid, int ai, GameProcess game) {
		super(grid);
		this.ai = ai;

		if (ai == 1)
			player1 = new FirstAI(grid, game);
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
