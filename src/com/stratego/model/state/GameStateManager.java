package com.stratego.model.state;

/**
 * <h1>GameStateManager</h1>
 * 
 * <p>
 * Classe permettant de gérer les différents des états du jeu, il existe quatre
 * états: MenuState, comme le nom l'indique, il s'agit du menu principal lors du
 * lancement du jeu - GameState, il représente une partie - PauseState, un menu
 * de pause et enfin - EndGameState, il représente la fin d'une partie.
 * </p>
 * 
 * @see GameState#MENUSTATE
 * @see GameState#GAMESTATE
 * @see GameState#PAUSESTATE
 * @see GameState#ENDGAMESTATE
 * 
 * @author O.S
 */

public class GameStateManager {

	// Nombre entier représentant l'état de la partie, pour plus d'informations sur
	// les attributions des entiers:
	// @see GameState
	private int currentState;

	public GameStateManager() {
		// Initialisation de l'état de la partie à 0.
		// @see GameState#MENUSTATE
		currentState = 0;
	}

	/**
	 * Méthode permettant de changer d'état de partie. Ce n'est au final qu'un
	 * mutateur permettant de définir currentState.
	 * 
	 * @see GameStateManager#currentState
	 */

	public void switchState(int state) {
		currentState = state;
	}
	
	/**
	 * Quelques accesseurs et mutateurs.
	 */
	
	public int getState() {
		return currentState;
	}

}
