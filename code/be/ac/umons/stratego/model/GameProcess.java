package be.ac.umons.stratego.model;

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
 * Classe permettant de modeliser le deroulement d'une partie, elle contient
 * plusieurs methodes visant a decrire l'etat actuelle de la partie en cours.
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

	GameStateManager state = new GameStateManager();

	/**
	 * Methode permettant de verifier la victoire d'un des deux joueurs. Une partie
	 * est gagnee si et seulement si l'etat du jeu repond a un de ces criteres:
	 * 
	 * - Le joueur perdant n'a plus de drapeau. - Le joueur gagnant a encercle son
	 * drapeau de bombes et le joueur perdant n'a plus de demineurs. - Le joueur
	 * perdant est dans une situation où il ne peut plus bouger. - Le joueur perdant
	 * est dans une situation où toutes ses pieces sont strictements plus faibles
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
	 * Methode permettant de verifier si la partie est terminee, utilisee lors de
	 * chaque debut de tour.
	 * 
	 * @return Retourne vrai si la partie est terminee, non sinon.
	 * 
	 * @see Player#checkWin()
	 */

	public boolean checkWin() {
		return user.checkWin(ai) || ai.checkWin(user);
	}

	/**
	 * Methode permettant de mettre fin a la partie en changeant le GameState a
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
	 * Methode permettant de representer une manche, elle sert uniquement a changer
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
	 * Methode permettant de retourner le tour de la personne qui doit jouer.
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

