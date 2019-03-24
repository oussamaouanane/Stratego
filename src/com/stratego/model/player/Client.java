package com.stratego.model.player;

import java.util.ArrayList;

import com.stratego.model.pawn.Pawn;

/**
 * <h1>Client</h1>
 * 
 * Classe permettant de repr�senter un joueur local.
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
		
		//Cr�ation des pions de l'utilisateur Client
		for (int i = 1; i <= 40; i++) {
			for (int c: Pawn.PAWNS_COMPOSITION)
			pawnsArray.add(new Pawn(i, c, 1));
		}
	}
	
	/**
	 * M�thode permettant de v�rifier la victoire d'un des deux joueurs. Une partie
	 * est gagn�e si et seulement si l'�tat du jeu r�pond � un de ces crit�res:
	 * 
	 * - Le joueur Server n'a plus de drapeau. 
	 * - Le joueur Client a encercl� son drapeau de bombes et le joueur Serveur n'a plus de d�mineurs. 
	 * - Le joueur Server est dans une situation o� il ne peut plus bouger. 
	 * - Le joueur Server est dans une situation o� toutes ses pi�ces sont strictements plus faibles
	 *   que celles du joueur Client.
	 * 
	 * @return
	 */
	public boolean checkWin() {

		// Le joueur Server n'a plus de drapeau
		if (serverPlayer.hasFlag() == false
		// Le joueur Client a encercl� son drapeau et Server n'a plus de d�mineur.
		   || ((this.flagSurrounded) && (!serverPlayer.hasMinersLeft()))
		   || (!serverPlayer.hasMovesLeft()))
			return true;
		else
			return false;
	}
}
