package be.ac.umons.stratego.model.state;

/**
 * <h1>GameStateManager</h1>
 * 
 * <p>
 * Classe permettant de gérer les différents des états lors d'une partie, lors
 * d'une partie, les joueurs passent par trois phases de jeu principales, le
 * placement des pions, le jeu jusque fin et la fin du jeu.
 * 
 * Attention GameStateManager n'est pas utilisé comme un outil permettant de
 * changer d'états (visuels) mais comme une méthode permettant de changer les
 * états pendant une partie!
 * </p>
 * 
 * @see GameState#SETTINGUPSTATE
 * @see GameState#GAMESTATE
 * @see GameState#ENDGAMESTATE
 * 
 * @author O.S
 */

public class GameStateManager {

	// GameState représentant l'état de la partie, pour plus d'informations sur
	// les attributions des états:
	// @see GameState
	private GameState currentState;

	public GameStateManager() {
		// Initialisation de l'état de la partie.
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

