package com.stratego.model.player;

import java.util.ArrayList;

import com.stratego.model.pawn.Pawn;

/**
 * <h1>Client</h1>
 * 
 * Classe permettant de représenter un joueur local.
 * 
 * @see Player
 * 
 * @author O.S
 */

public class Client implements Player {
	
	private boolean hasFlag;
	private boolean flagSurrounded;
	private boolean hasMinersLeft;
	private ArrayList<Pawn> pawnsArray;
		
	private Server serverPlayer;

	public Client() {
		pawnInitialization();
		hasFlag = flagSurrounded = hasMinersLeft = true;
	}
	
	@Override
	public boolean hasFlag() {
		return hasFlag;
	}
	
	@Override
	public boolean hasFlagSurrounded() {
		return false;
	}
	
	@Override
	public boolean hasMinersLeft() {
		return hasMinersLeft;
	}
	
	@Override
	public boolean hasMovesLeft() {
		return false;
	}
	
	@Override
	public void pawnInitialization() {
		
		//Création des pions de l'utilisateur Client
		for (int i = 1; i <= 40; i++) {
			for (int c: Pawn.PAWNS_COMPOSITION)
			pawnsArray.add(new Pawn(i, c, 1));
		}
	}
	
	/**
	 * Méthode permettant de vérifier la victoire d'un des deux joueurs. Une partie
	 * est gagnée si et seulement si l'état du jeu répond à un de ces critères:
	 * 
	 * - Le joueur Server n'a plus de drapeau. 
	 * - Le joueur Client a encerclé son drapeau de bombes et le joueur Serveur n'a plus de démineurs. 
	 * - Le joueur Server est dans une situation où il ne peut plus bouger. 
	 * - Le joueur Server est dans une situation où toutes ses pièces sont strictements plus faibles
	 *   que celles du joueur Client.
	 * 
	 * @return
	 */
	public boolean checkWin() {

		// Le joueur Server n'a plus de drapeau
		if (serverPlayer.hasFlag() == false
		// Le joueur Client a encerclé son drapeau et Server n'a plus de démineur.
		   || ((this.flagSurrounded) && (!serverPlayer.hasMinersLeft()))
		   || (!serverPlayer.hasMovesLeft()))
			return true;
		else
			return false;
	}
}
