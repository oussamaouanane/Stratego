package be.ac.umons.stratego.model.state;

/**
 * <h1>GameState</h1>
 * 
 * <p>
 * Enum permettant de definir les differents etats d'une partie en cours, leurs
 * presentations figurent ci-dessous.
 * </p>
 * 
 * @see SettingUpState Represente le positionnement des pions.
 * @see GameState Represente le deroulement de la partie.
 * @see EndGameState Represente la fin du deroulement de la partie.
 * 
 */

public enum GameState {
	SETTINGUPSTATE(), GAMESTATE(), ENDGAMESTATE();
}