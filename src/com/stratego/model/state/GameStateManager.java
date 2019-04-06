package com.stratego.model.state;

/**
 * <h1>GameStateManager</h1>
 * 
 * <p>
 * Classe permettant de g�rer les diff�rents des �tats du jeu, il existe quatre
 * �tats: MenuState, comme le nom l'indique, il s'agit du menu principal lors du
 * lancement du jeu - GameState, il repr�sente une partie - PauseState, un menu
 * de pause et enfin - EndGameState, il repr�sente la fin d'une partie.
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

	// Nombre entier repr�sentant l'�tat de la partie, pour plus d'informations sur
	// les attributions des entiers:
	// @see GameState
	private int currentState;

	public GameStateManager() {
		// Initialisation de l'�tat de la partie � 0.
		// @see GameState#MENUSTATE
		currentState = 0;
	}

	/**
	 * M�thode permettant de changer d'�tat de partie. Ce n'est au final qu'un
	 * mutateur permettant de d�finir currentState.
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
