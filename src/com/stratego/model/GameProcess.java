package com.stratego.model;

import com.stratego.model.grid.Grid;
import com.stratego.model.player.Player;
import com.stratego.model.state.GameStateManager;

/**
 * <h1>GameProcess</h1>
 * 
 * <p>
 * Classe permettant de mod�liser le d�roulement d'une partie, elle contient
 * plusieurs m�thodes visant � d�crire l'�tat actuelle de la partie en cours.
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
	 * M�thode permettant de v�rifier si la partie est termin�e, utilis�e lors de
	 * chaque d�but de tour.
	 * 
	 * @return Retourne vrai si la partie est termin�e, non sinon.
	 * 
	 * @see Player#checkWin()
	 */

	public boolean checkWin() {
		return user.checkWin(ai) || ai.checkWin(user);

	}

	/**
	 * M�thode permettant de retourner le tour de la personne qui doit jouer.
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
			// D�termination du joueur qui doit jouer.
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
	 * M�thode permettant de placer les pions en d�but de partie
	 */

	public void pawnPlacement(Player p1) {
		p1.pawnPlacement();

	}

}
