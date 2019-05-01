package be.ac.umons.stratego.model.state;

/**
 * <h1>GameStateManager</h1>
 * 
 * <p>
 * Classe permettant de g�rer les diff�rents des �tats lors d'une partie, lors
 * d'une partie, les joueurs passent par trois phases de jeu principales, le
 * placement des pions, le jeu jusque fin et la fin du jeu.
 * 
 * Attention GameStateManager n'est pas utilis� comme un outil permettant de
 * changer d'�tats (visuels) mais comme une m�thode permettant de changer les
 * �tats pendant une partie!
 * </p>
 * 
 * @see GameState#SETTINGUPSTATE
 * @see GameState#GAMESTATE
 * @see GameState#ENDGAMESTATE
 * 
 * @author O.S
 */

public class GameStateManager {

	// GameState repr�sentant l'�tat de la partie, pour plus d'informations sur
	// les attributions des �tats:
	// @see GameState
	private GameState currentState;

	public GameStateManager() {
		// Initialisation de l'�tat de la partie.
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

