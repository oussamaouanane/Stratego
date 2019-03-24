package com.stratego.model;

import com.stratego.model.grid.Grid;
import com.stratego.model.player.Client;
import com.stratego.model.player.Server;

/**
 * <h1>GameProcess</h1>
 * 
 * <p>
 * Classe permettant de décrire le déroulement d'une partie, elle contient
 * plusieurs méthodes visant à décrire l'état actuelle de la partie en cours,
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
	 * Méthode permettant de vérifier la victoire d'un des deux joueurs. Une partie
	 * est gagnée si et seulement si l'état du jeu répond à un de ces critères:
	 * 
	 * - Le joueur perdant n'a plus de drapeau. - Le joueur gagnant a encerclé son
	 * drapeau de bombes et le joueur perdant n'a plus de démineurs. - Le joueur
	 * perdant est dans une situation où il ne peut plus bouger. - Le joueur perdant
	 * est dans une situation où toutes ses pièces sont strictements plus faibles
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
	 * Méthode permettant de vérifier si la partie est terminée, utilisée lors de
	 * chaque début de tour.
	 * 
	 * @return Retourne vrai si la partie est terminée, non sinon.
	 * 
	 * @see Client#checkWin()
	 * @see Server#checkWin()
	 */

	public boolean checkWin() {
		return user.checkWin() || ai.checkWin();

	}
	
	/**
	 * Méthode permettant de retourner le tour de la personne qui doit jouer
	 * @return Retourne 1 si c'est au tour du joueur Client, 2 sinon.
	 */

	public int getTurn() {
		return pawnTurn[index];

	}

	public void runningGame() {

		pawnPlacement();
		// Permet de placer les pions du Client en début de partie, création du
		for (int i = 0; i < 40; i++) {

		}

		while (!checkWin()) {
			int turn = getTurn();
			
			
			// Change the turn
			index = pawnTurn.length % pawnTurn[index];

		}

	}

	/**
	 * Méthode permettant de placer les pions en début de partie
	 */

	public void pawnPlacement() {

	}

}
