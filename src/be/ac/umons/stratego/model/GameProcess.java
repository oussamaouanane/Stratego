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

	public void endGame() {
		if (checkWin())
			state.setState(GameState.ENDGAMESTATE);
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

	public Player getUser() {
		return user;
	}

	public Player getAI() {
		return ai;
	}

	public GameStateManager getGameStateManager() {
		return state;
	}

	public void runningGame() {

		while (state.getState() == GameState.SETTINGUPSTATE) {

		}

		// Déterminer le joueur qui doit jouer

//		pawnPlacement(ai);
//
//		while (!(state.getState() == GameState.ENDGAMESTATE)) {
//			// Détermination du joueur qui doit jouer.
//			Player currentPlayer = null;
//
//			switch (index) {
//			case 0:
//				currentPlayer = user;
//				break;
//			case 1:
//				currentPlayer = ai;
//
//			}
//			currentPlayer.play();
//			index++;
//			// Changement du tour
//			index = playerTurn.length % playerTurn[index];

		// }

	}

}
