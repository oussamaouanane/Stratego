/**
 * 
 */
package be.ac.umons.stratego.test;

import static org.junit.Assert.*;
import org.junit.Test;

import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

public class PawnInteractionTest {
	
	private Grid grid;
	
	private Pawn pawnA = new Pawn(9, 1);
	private Pawn pawnB = new Pawn(8, 1);
	private Pawn pawnC = new Pawn(0, 1);
	private Pawn pawnD = new Pawn(11, 1);
	
	private Square squareA = new Square(pawnA, true);
	private Square squareB = new Square(pawnB, true);
	private Square squareC = new Square(pawnC, true);
	private Square squareD = new Square(pawnD, true);
	private Square squareE = new Square(null, false);

	/**
	 * ATTENTION QUAND DEUX RANGS SONT EGAUX, NE FONCTIONNE PAS!
	 */
	
	@Test
	public void testEvaluateFighting() {
		// Ici, un rang 9 contre un rang 8, le 9 doit être le gagnant.
		PawnInteraction firstFight = new PawnInteraction(squareA, squareB, grid);
		assertTrue("Le pion au rang 9 a gagné le combat.", firstFight.evaluateFighting() == 1);
		
		// Ici, un rang 0 contre un rang 8, le 8 doit être le gagnant.
		PawnInteraction secondFight = new PawnInteraction(squareC, squareB, grid);
		assertTrue("Le pion au rang 8 a gagné le combat.", secondFight.evaluateFighting() == -1);
		
		// Ici, un rang 0 contre un rang 9, le 0 doit être le gagnant.
		PawnInteraction thirdFight = new PawnInteraction(squareC, squareA, grid);
		assertTrue("Le pion au rang 0 a gagné le combat.", thirdFight.evaluateFighting() == 1);
		
		// Ici, un rang 0 contre un rang 9, le 0 doit être le gagnant.
		PawnInteraction FourthFight = new PawnInteraction(squareC, squareD, grid);
		assertTrue("C'est une égalité.", thirdFight.evaluateFighting() == 1);
		
	}

}
