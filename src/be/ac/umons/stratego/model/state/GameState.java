package be.ac.umons.stratego.model.state;

/**
 * <h1>GameState</h1>
 * 
 * <p>
 * Enum permettant de d�finir les diff�rents �tats d'une partie en cours, leurs
 * pr�sentations figurent ci-dessous.
 * </p>
 * 
 * @see SettingUpState Repr�sente le positionnement des pions.
 * @see GameState Repr�sente le d�roulement de la partie.
 * @see EndGameState Repr�sente la fin du d�roulement de la partie.
 * 
 * @author O.S
 */

public enum GameState {
	SETTINGUPSTATE(), GAMESTATE(), ENDGAMESTATE();
}