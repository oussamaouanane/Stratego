package be.ac.umons.stratego.controller;

import java.util.ArrayList;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.PawnInteraction;
import be.ac.umons.stratego.view.GameView;
import be.ac.umons.stratego.view.PawnView;

/**
 * <h1>GridController</h1>
 * 
 * <p>
 * Classe permettant de coordonner le plateau de la partie logique et la partie
 * graphique du jeu.
 * </p>
 * 
 * @see Grid
 * @see InGameState
 */

public class GridController {

	private GameView gameView;
	private GameProcess game;

	public GridController(GameView gameV, GameProcess game) {
		gameView = gameV;
		this.game = game;
	}

	/**
	 * @param initialSquare     Instance Square de d�part
	 * @param destinationSquare Instance Square finale
	 * @see PawnInteraction#isMovePossible()
	 */

	public boolean isMovePossible(Square initialSquare, Square destinationSquare) {

		PawnInteraction squareCouple = new PawnInteraction(initialSquare, destinationSquare, game.getGrid());
		return squareCouple.isMovePossible();
	}

	/**
	 * M�thode permettant de retourner une ArrayList de Couple repr�sentant
	 * l'ensemble des cases accessibles depuis le pion dans le Square en param�tre.
	 * 
	 * @param square Instance Square donc il faut surligner les mouvements possibles
	 *               du pion le contenant.
	 * @return Repr�sentation sous forme de couple de coordonn�es l'ensemble des
	 *         cases � surligner.
	 */

	public ArrayList<Couple> SquareToHighlight(Square square) {

		int squareRow = square.getRow();
		int squareColumn = square.getColumn();
		PawnInteraction couple = new PawnInteraction(squareRow, squareColumn, game.getGrid());
		ArrayList<Couple> squareCouple = new ArrayList<Couple>();

		if (!couple.availableMovement().isEmpty()) {
			for (Couple c : couple.availableMovement()) {
				Couple squareCoupleAdd = new Couple(c.getX(), c.getY());
				squareCouple.add(squareCoupleAdd);
			}
		}
		return squareCouple;
	}

	/**
	 * M�thode permettant de g�rer le combat entre deux pions situ�s respectivement
	 * dans les deux Square en param�tre.
	 * 
	 * @param initialDestination
	 * @param finalDestination
	 */

	public void doFighting(Square initialDestination, Square finalDestination) {

		PawnInteraction couple = new PawnInteraction(initialDestination, finalDestination, game.getGrid());
		couple.doFighting();
		switch (couple.evaluateFighting()) {
		// Pion dans case initial perd
		case -1:
			// On supprime le pion de initialSquare
			removePawnView(initialDestination);
			game.setScore(finalDestination.getPawn().getPlayer());
			break;
		// Egalite, les deux pions se font supprimer
		case 0:
			removePawnView(initialDestination);
			removePawnView(finalDestination);
			break;
		// Pion dans case initial gagne
		case 1:
			// On supprime le pion dans destination
			removePawnView(finalDestination);
			// On met le pion de initial dans final
			setPawnView(gameView.getSquareView(initialDestination).getPawnView(), finalDestination);
			game.setScore(finalDestination.getPawn().getPlayer());
			break;
		}
	}
	
	/**
	 * M�thode permettant de repr�senter 
	 */

	public void AITurn() {
		
		Couple couple = game.getAI().getNextMove();
		Square initialSquare = couple.getSquareA();
		Square destinationSquare = couple.getSquareB();

		// Gestion si aucun pion ne se trouve dans destinationSquare
		if (destinationSquare.getPawn() == null) {
			// Gestion partie logique
			destinationSquare.setPawn(initialSquare.getPawn());
			destinationSquare.getPawn().setSquare(destinationSquare);
			initialSquare.setPawn(null);
			// Gestion partie graphique
			PawnView pawn = gameView.getSquareView(initialSquare).getPawnView();
			gameView.getSquareView(destinationSquare).setPawnView(pawn);
			pawn.setSquare(gameView.getSquareView(destinationSquare));
			//gameView.pawnSetCoordinates(pawn, gameView.getSquareView(destinationSquare));
			gameView.getSquareView(initialSquare).setPawnView(null);
		}
		
		//Gestion si un pion ennemi se trouve dans destination Square
		else if ((destinationSquare.getPawn() != null) && (destinationSquare.getPawn().getPlayer() == 1))
			doFighting(initialSquare, destinationSquare);
		
		gameView.turnPlayed();
	}

	public void setPawnView(PawnView pawn, Square square) {
		pawn.setSquare(gameView.getSquareView(square));
		gameView.getSquareView(square);
		pawn.setX(gameView.getSquareView(square).getX());
		pawn.setY(gameView.getSquareView(square).getY());
	}

	public void removePawnView(Square square) {
		gameView.getInGame().getChildren().remove(gameView.getSquareView(square).getPawnView());
	}

}
