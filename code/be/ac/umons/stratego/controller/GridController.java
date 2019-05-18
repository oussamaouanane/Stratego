package be.ac.umons.stratego.controller;

import java.util.ArrayList;

import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;
import be.ac.umons.stratego.view.GameView;
import be.ac.umons.stratego.view.PawnView;
import be.ac.umons.stratego.view.SquareView;

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
	 * @param initialSquare     Instance Square de depart
	 * @param destinationSquare Instance Square finale
	 * @see PawnInteraction#isMovePossible()
	 */

	public boolean isMovePossible(Square initialSquare, Square destinationSquare) {

		PawnInteraction squareCouple = new PawnInteraction(initialSquare, destinationSquare, game.getGrid());
		if (initialSquare.getPawn().getRank() == 1)
			return squareCouple.isMovePossible() && squareCouple.isMovePossibleScout();
		return squareCouple.isMovePossible();
	}

	/**
	 * Methode permettant de retourner une ArrayList de Couple representant
	 * l'ensemble des cases accessibles depuis le pion dans le Square en parametre.
	 * 
	 * @param square Instance Square donc il faut surligner les mouvements possibles
	 *               du pion le contenant.
	 * @return Representation sous forme de couple de coordonnees l'ensemble des
	 *         cases a surligner.
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
	 * Methode permettant de gerer le combat entre deux pions situes respectivement
	 * dans les deux Square en parametre.
	 * 
	 * @param initialDestination
	 * @param finalDestination
	 */

	public void doFighting(Square initialDestination, Square finalDestination) {

		PawnInteraction couple = new PawnInteraction(initialDestination, finalDestination, game.getGrid());
		
		
		switch (couple.evaluateFighting()) {
		// Pion dans case initial perd
		case -1:
			// Retire le pion des pions vivants du joueur perdant.
			Pawn loserPawn = initialDestination.getPawn();
			game.getAlivePawn(loserPawn.getPlayer()).remove(loserPawn);
			
			// Retire l'image du pion dans la grille graphique.
			removePawnView(initialDestination);

			// Enlever le pion perdant dans la partie graphique.
			getSquareView(initialDestination).getPawnView().setSquareView(null);
			getSquareView(initialDestination).setPawnView(null);

			game.setScore(finalDestination.getPawn().getPlayer());
			break;

		// Personne gagne, egalite
		case 0:
			// Partie logique
			game.getAlivePawn(initialDestination.getPawn().getPlayer()).add(initialDestination.getPawn());
			game.getAlivePawn(finalDestination.getPawn().getPlayer()).add(finalDestination.getPawn());
			
			// Partie graphique
			removePawnView(initialDestination);
			removePawnView(finalDestination);

			getSquareView(initialDestination).getPawnView().setSquareView(null);
			getSquareView(initialDestination).setPawnView(null);
			getSquareView(finalDestination).getPawnView().setSquareView(null);
			getSquareView(finalDestination).setPawnView(null);

			game.setScore(1);
			game.setScore(2);
			break;

		// Pion dans case initial gagne
		case 1:
			// Retire le pion des pions vivants du joueur perdant.
			game.getAlivePawn(finalDestination.getPawn().getPlayer()).remove(finalDestination.getPawn());
			// Retire l'image du pion dans la grille graphique
			removePawnView(finalDestination);

			// Mettre le pion dans la case gagnante dans la partie graphique.
			PawnView winnerPawnView = getSquareView(initialDestination).getPawnView();
			getSquareView(finalDestination).setPawnView(winnerPawnView);
			winnerPawnView.setSquareView(getSquareView(finalDestination));

			// Fixe les coordonnes (x,y)
			winnerPawnView.setX(winnerPawnView.getSquareView().getX());
			winnerPawnView.setY(winnerPawnView.getSquareView().getY());

			// Reinitialise la case de depart.
			getSquareView(initialDestination).setPawnView(null);

			game.setScore(initialDestination.getPawn().getPlayer());
			break;
		}
		couple.doFighting();
	}

	public void removePawnView(Square square) {
		gameView.getInGamePane().getChildren().remove(gameView.getSquareView(square).getPawnView());
	}

	public SquareView getSquareView(Square square) {
		return gameView.getSquareView(square);
	}

}
