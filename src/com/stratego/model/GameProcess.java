package com.stratego.model;

import com.stratego.model.grid.Grid;
import com.stratego.model.player.Player;
import com.stratego.model.state.GameStateManager;

/**
 * <h1>GameProcess</h1>
 * 
 * <p>
 * Classe permettant de modéliser le déroulement d'une partie, elle contient
 * plusieurs méthodes visant à décrire l'état actuelle de la partie en cours.
 * </p>
 * 
 * @author O.S
 */

public class GameProcess {

	private Player user;
	private Player ai;
	private int[] playerTurn = { 1, 2 };
	private int index = 0;
	private Grid grid;

	GameStateManager state = new GameStateManager();

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

	public GameProcess(int ai) {

		grid = new Grid(2);
		user = new Player(false, grid);
		// Configurer ai
		switch (ai) {
		case 1: this.ai = new Player(true, grid);
		case 2: this.ai = new Player(true, grid);


		}
	}

	/**
	 * Méthode permettant de vérifier si la partie est terminée, utilisée lors de
	 * chaque début de tour.
	 * 
	 * @return Retourne vrai si la partie est terminée, non sinon.
	 * 
	 * @see Player#checkWin()
	 */

	public boolean checkWin() {
		return user.checkWin(ai) || ai.checkWin(user);

	}

	/**
	 * Méthode permettant de retourner le tour de la personne qui doit jouer.
	 * 
	 * @return Retourne 1 si c'est au tour du joueur Client, 2 sinon.
	 */

	public int getTurn() {
		return playerTurn[index];

	}
	
	public Grid getGrid() {
		return grid;
	}

	public void runningGame() {

		pawnPlacement(ai);

		while (!(state.getState() == 3)) {
			// Détermination du joueur qui doit jouer.
			Player currentPlayer = null;

			switch (index) {
			case 0:
				currentPlayer = user;
				break;
			case 1:
				currentPlayer = ai;

			}
			currentPlayer.play();
			index++;
			// Changement du tour
			index = playerTurn.length % playerTurn[index];

		}

	}

	/**
	 * Méthode permettant de placer les pions en début de partie
	 */

	public void pawnPlacement(Player p1) {
		p1.pawnPlacement();

	}

}
