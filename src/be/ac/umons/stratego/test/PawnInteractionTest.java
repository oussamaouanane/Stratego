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
	private Pawn pawnD = new Pawn(1, 1);
	private Pawn pawnE = new Pawn(2, 1);
	private Pawn pawnF = new Pawn(10, 1);

	private Square squareA = new Square(pawnA, true);
	private Square squareB = new Square(pawnB, true);
	private Square squareC = new Square(pawnC, true);
	private Square squareD = new Square(pawnD, true);
	private Square squareE = new Square(pawnE, true);
	private Square squareF = new Square(pawnF, true);
	private Square squareG = new Square(null, false);
	

	/**
	 * ATTENTION QUAND DEUX RANGS SONT EGAUX, NE FONCTIONNE PAS!
	 */

	@Test
	public void testEvaluateFighting() {
		// Ici, un rang 9 contre un rang 8, le 9 doit être le gagnant.
		PawnInteraction firstFight = new PawnInteraction(squareA, squareB, grid);
		assertTrue("Le pion au rang 9 n'a pas gagné le combat.", firstFight.evaluateFighting() == 1);

		// Ici, un rang 0 contre un rang 8, le 8 doit être le gagnant.
		PawnInteraction secondFight = new PawnInteraction(squareC, squareB, grid);
		assertTrue("Le pion au rang 8 n'a pas gagné le combat.", secondFight.evaluateFighting() == -1);

		// Ici, un rang 0 contre un rang 9, le 0 doit être le gagnant.
		PawnInteraction thirdFight = new PawnInteraction(squareC, squareA, grid);
		assertTrue("Le pion au rang 0 n'a pas gagné le combat.", thirdFight.evaluateFighting() == 1);

		// Ici, un rang 0 contre un rang 9, le 0 doit être le gagnant.
		PawnInteraction fourthFight = new PawnInteraction(squareC, squareC, grid);
		assertTrue("Ce n'est pas une égalité.", fourthFight.evaluateFighting() == 0);

		// Ici, un rang 1 contre un rang 10, le 1 doit être le gagnant.
		PawnInteraction fifthFight = new PawnInteraction(squareE, squareF, grid);
		assertTrue("Le pion au rang 1 n'a gagné le combat.", fifthFight.evaluateFighting() == 1);

		// Ici, un rang 1 contre pas de pion, personne ne devrait être gagnant car
		// incomparables.
		PawnInteraction sixthFight = new PawnInteraction(squareE, squareG, grid);
		assertTrue("Le pion au rang 1 n'a pas gagné le combat", sixthFight.evaluateFighting() == -2);
	}

	@Test
	public void testDoFighting() {
		// Ici, un rang 9 contre un rang 8, donc 9 prend la place de 8
		PawnInteraction firstFight = new PawnInteraction(squareA, squareB, grid);
		firstFight.doFighting();
		assertTrue("Le pion au rang 9 n'a pas gagné le combat",
				(squareA.getPawn() == null) && (squareB.getPawn() == pawnA));

		// Ici, un rang 0 contre un rang 8, le 8 doit être le gagnant.
		PawnInteraction secondFight = new PawnInteraction(squareC, squareB, grid);
		secondFight.doFighting();
		assertTrue("Le pion au rang 8 n'a pas gagné le combat.", (squareB.getPawn() == pawnC) && (squareC.getPawn() == null));

		// Ici, un rang 0 contre un rang 9, le 0 doit être le gagnant.
		PawnInteraction thirdFight = new PawnInteraction(squareC, squareA, grid);
		thirdFight.doFighting();
		assertTrue("Le pion au rang 0 n'a pas gagné le combat.", (squareC.getPawn() == null) && (squareA.getPawn() == null));
	}
	
	@Test
	public void testIsMovePossible() {
		squareA.setRow(2);
		squareA.setColumn(7);
		PawnInteraction movementA = new PawnInteraction(2, 7, grid);
		movementA.availableMovement();
		assertTrue("Il n'y pas 3 mouvements possibles", movementA.availableMovement().size() > 0);
	}

}
