package be.ac.umons.stratego.model;

import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.player.FirstAI;
import be.ac.umons.stratego.model.player.Player;
import be.ac.umons.stratego.model.player.SecondAI;
import be.ac.umons.stratego.model.state.GameState;
import be.ac.umons.stratego.model.state.GameStateManager;

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

		grid = new Grid();
		user = new Player(grid);
		// Configurer ai
		switch(ai) {
		case 1: this.ai = new FirstAI(grid);
		case 2: this.ai = new SecondAI(grid);
		}
	}
	
	public void endSettingUp() {
		state.setState(GameState.GAMESTATE);
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

	public void endGame() {
		if (checkWin())
			state.setState(GameState.ENDGAMESTATE);
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

	public Player getUser() {
		return user;
	}

	public Player getAI() {
		return ai;
	}

	public GameStateManager getGameStateManager() {
		return state;
	}
	
	public void play() {
		
		if (state.getState() == GameState.GAMESTATE) {
			// Changement de tour, changement de joueur
			index = (index++) % 2 ;
			
		}
	}

}
