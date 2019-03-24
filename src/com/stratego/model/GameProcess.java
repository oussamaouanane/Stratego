package com.stratego.model;

import com.stratego.model.grid.Grid;
import com.stratego.model.player.Client;
import com.stratego.model.player.Server;

/**
 * <h1>GameProcess</h1>
 * 
 * <p>
 * Classe permettant de d�crire le d�roulement d'une partie, elle contient
 * plusieurs m�thodes visant � d�crire l'�tat actuelle de la partie en cours,
 * </p>
 * 
 * @author O.S
 */

public class GameProcess {

	Client user;
	Server ai;
	int[] pawnTurn = { 1, 2 };
	int index = 0;

	/**
	 * M�thode permettant de v�rifier la victoire d'un des deux joueurs. Une partie
	 * est gagn�e si et seulement si l'�tat du jeu r�pond � un de ces crit�res:
	 * 
	 * - Le joueur perdant n'a plus de drapeau. - Le joueur gagnant a encercl� son
	 * drapeau de bombes et le joueur perdant n'a plus de d�mineurs. - Le joueur
	 * perdant est dans une situation o� il ne peut plus bouger. - Le joueur perdant
	 * est dans une situation o� toutes ses pi�ces sont strictements plus faibles
	 * que celles du joueur gagnant
	 * 
	 * @return
	 */

	public GameProcess() {

		Grid grid = new Grid();
		user = new Client();
		ai = new Server();
	}

	/**
	 * M�thode permettant de v�rifier si la partie est termin�e, utilis�e lors de
	 * chaque d�but de tour.
	 * 
	 * @return Retourne vrai si la partie est termin�e, non sinon.
	 * 
	 * @see Client#checkWin()
	 * @see Server#checkWin()
	 */

	public boolean checkWin() {
		return user.checkWin() || ai.checkWin();

	}
	
	/**
	 * M�thode permettant de retourner le tour de la personne qui doit jouer
	 * @return Retourne 1 si c'est au tour du joueur Client, 2 sinon.
	 */

	public int getTurn() {
		return pawnTurn[index];

	}

	public void runningGame() {

		pawnPlacement();
		// Permet de placer les pions du Client en d�but de partie, cr�ation du
		for (int i = 0; i < 40; i++) {

		}

		while (!checkWin()) {
			int turn = getTurn();
			
			
			// Change the turn
			index = pawnTurn.length % pawnTurn[index];

		}

	}

	/**
	 * M�thode permettant de placer les pions en d�but de partie
	 */

	public void pawnPlacement() {

	}

}
