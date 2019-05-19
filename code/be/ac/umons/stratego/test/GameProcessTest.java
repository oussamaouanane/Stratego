package be.ac.umons.stratego.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.pawn.Pawn;


/**
 * Classe permettant d'effectuer les tests unitaires de Player et GameProcess.
 */

public class GameProcessTest {
	
	private GameProcess game = new GameProcess(1);
	private Grid grid;

	
	@Test
	public void testCheckFlag() {
		// Ici player n'a pas de drapeau.
		ArrayList<Pawn> alivePawn1 = new ArrayList<Pawn>();
		alivePawn1.add(new Pawn(2, 1));
		ArrayList<Pawn> alivePawn2 = new ArrayList<Pawn>();
		alivePawn2.add(new Pawn(2, 1));
		alivePawn2.add(new Pawn(11, 2));
		game.getUser().setAlivePawns(alivePawn1);
		game.getAI().setAlivePawns(alivePawn2);
		assertTrue("player a encore son drapeau.", !game.getUser().hasFlag());
		assertTrue("player n'a pas son drapeau.", game.getAI().hasFlag());
	}
}
