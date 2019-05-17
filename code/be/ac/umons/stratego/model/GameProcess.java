package be.ac.umons.stratego.model;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.player.Player;
import be.ac.umons.stratego.model.player.PlayerAI;
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

public class GameProcess implements Serializable {

	private static final long serialVersionUID = -3486397833048737600L;
	private Player user;
	private PlayerAI ai;
	private int[] playerTurn = { 1, 2 };
	private int index = 0;
	private Grid grid;

	private int userScore;
	private int aiScore;

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
		this.ai = new PlayerAI(grid, ai, this);

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

	/**
	 * Méthode permettant de mettre fin à la partie en changeant le GameState à
	 * ENDGAMESTATE.
	 * 
	 * @see GameState
	 * @see GameState#ENDGAMESTATE
	 */

	public void endGame() {
		if (checkWin())
			state.setState(GameState.ENDGAMESTATE);
	}

	/**
	 * Méthode permettant de représenter une manche, elle sert uniquement à changer
	 * de tour et non sa gestion.
	 */

	public void play() {
		if (state.getState() == GameState.GAMESTATE) {
			// Changement de tour, changement de joueur
			index++;
			index %= 2;
		}
	}
	
	/**
	 * Méthode permettant de retourner le tour de la personne qui doit jouer.
	 * 
	 * @return Retourne 1 si c'est au tour du joueur non-AI, 2 sinon.
	 */

	public int getTurn() {
		return playerTurn[index];
	}
	
	public int getScore(int player) {
		
		if (player == 1)
			return user.getScore();
		else
			return ai.getScore();
	}
	
	public void setScore(int player) {
		if (player == 1)
			user.updateScore();
		else
			ai.updateScore();
	}
	
	// Quelques accesseurs (getters) et mutateurs (setters)

	public Grid getGrid() {
		return grid;
	}

	public Player getUser() {
		return user;
	}

	public PlayerAI getAI() {
		return ai;
	}

	public GameStateManager getGameStateManager() {
		return state;
	}

}
