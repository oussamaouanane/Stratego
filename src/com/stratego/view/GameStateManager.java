package com.stratego.view;

/**
 * <h1>GameStateManager</h1>
 * 
 * <p>
 * Classe permettant de g�rer les diff�rents des �tats du jeu, il existe quatre
 * �tats: MenuState, comme le nom l'indique, il s'agit du menu principal lors
 * du lancement du jeu - GameState, il repr�sente une partie - PauseState,
 * un menu de pause et enfin - EndGameState, il repr�sente la fin d'une
 * partie.
 * </p>
 * 
 * @see MenuState
 * @see GameState
 * @see PauseState
 * @see EndGameState
 * 
 * @author O.S
 */

public class GameStateManager {
	
	private gameStates currentState;
	
	public enum gameStates {
		
		MENUSTATE,
		GAMESTATE,
		PAUSESTATE,
		ENDGAMESTATE;
	}
	
	public gameStates getCurrentState() {
		return currentState;
	}

}
