package be.ac.umons.stratego.model.state;

import java.io.Serializable;

/**
 * <h1>GameStateManager</h1>
 * 
 * <p>
 * Classe permettant de gerer les differents des etats lors d'une partie, lors
 * d'une partie, les joueurs passent par trois phases de jeu principales, le
 * placement des pions, le jeu jusque fin et la fin du jeu.
 * 
 * Attention GameStateManager n'est pas utilise comme un outil permettant de
 * changer d'etats (visuels) mais comme une methode permettant de changer les
 * etats pendant une partie!
 * </p>
 * 
 * @see GameState#SETTINGUPSTATE
 * @see GameState#GAMESTATE
 * @see GameState#ENDGAMESTATE
 * 
 */

public class GameStateManager implements Serializable {

	// GameState representant l'etat de la partie, pour plus d'informations sur
	// les attributions des etats:
	// @see GameState
	private static final long serialVersionUID = 1L;
	private GameState currentState;

	public GameStateManager() {
		// Initialisation de l'etat de la partie.
		currentState = GameState.SETTINGUPSTATE;
	}

	/**
	 * Quelques accesseurs et mutateurs.
	 */

	public GameState getState() {
		return currentState;
	}

	public void setState(GameState state) {
		currentState = state;
	}

}
