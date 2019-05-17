package be.ac.umons.stratego.model.state;

/**
 * <h1>GameState</h1>
 * 
 * <p>
 * Enum permettant de définir les différents états d'une partie en cours, leurs
 * présentations figurent ci-dessous.
 * </p>
 * 
 * @see SettingUpState Représente le positionnement des pions.
 * @see GameState Représente le déroulement de la partie.
 * @see EndGameState Représente la fin du déroulement de la partie.
 * 
 * @author O.S
 */

public enum GameState {
	SETTINGUPSTATE(), GAMESTATE(), ENDGAMESTATE();
}