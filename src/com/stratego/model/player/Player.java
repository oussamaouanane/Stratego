package com.stratego.model.player;

import java.util.ArrayList;

import com.stratego.model.Couple;
import com.stratego.model.grid.Grid;
import com.stratego.model.grid.Square;
import com.stratego.model.pawn.Pawn;
import com.stratego.model.pawn.PawnInteractions;

/**
 * <h1>Player</h1>
 * <p>
 * Classe permettant de mod�liser un joueur, il existe au plus deux joueurs lors
 * d'une partie. Chaque joueur poss�de un set de 40 pions.
 * </p>
 *
 */

public class Player {

	/**
	 * Variables d'instances permettant de v�rifier si une partie est termin�e.
	 * Ensemble de bool�ens. hasFlag: le joueur a un drapeau. flagSurrounded: le
	 * drapeau du joueur est entour� de bombes. hasMinersLeft: le joueur a encore
	 * des d�mineurs.
	 */

	private int playerId;
	private int flagX;
	private int flagY;

	private boolean hasFlag;
	private boolean flagSurrounded;
	private boolean hasMinersLeft;

	private int currentNbOfPawns;

	private ArrayList<Pawn> alivePawns;
	private ArrayList<Pawn> deathPawns;

	private Grid grid;

	public Player(boolean ai, Grid grid) {
		// D�faut
		hasFlag = hasMinersLeft = flagSurrounded = true;
		this.grid = grid;

		initializePawns();
		if (ai = true) {
			currentNbOfPawns = 40;
			playerId = 2;
		} else
			currentNbOfPawns = 0;
		playerId = 1;

		// Configuration si intelligence artificielle

	}

	public void initializePawns() {
		for (int i = currentNbOfPawns; i <= currentNbOfPawns + 40; i++) {
			for (int c : Pawn.PAWNS_COMPOSITION)
				alivePawns.add(new Pawn(i, c, playerId));
		}
	}
	
	public void pawnPlacement() {
		
	}

	public Couple getMovement() {
		// Impl�menter une m�thode pour r�cup�rer le mouvement voulu du joueur qui
		// retourne un couple (x, y) qui repr�sente une instance Square � la position de
		// l'instance Grid[x][y].
		return null;
	}

	public void play() {
		PawnInteractions move = new PawnInteractions(getMovement());
		

	}

	/**
	 * M�thode permettant de v�rifier si le drapeau du joueur n'a pas encore �t�
	 * saisi. On parcourt la liste des pions vivants du joueur pour voir si le
	 * drapeau y est contenu.
	 * 
	 * @return Bool�en qui indique si le drapeau du joueur a �t� saisi ou non.
	 */

	@SuppressWarnings("unlikely-arg-type")
	public boolean hasFlag() {
		// Parcourt la liste des pions encore vivants pour voir si elle contient encore
		// le drapeau.
		for (Pawn pawn : alivePawns) {
			boolean pawnAFlag = pawn.isPawnA(11);
			if (!alivePawns.contains(pawnAFlag)) {
				hasFlag = false;
			}
		}
		// Retourne vrai ou faux en fonction de si le joueur a encore le drapeau.
		return hasFlag;

	}

	/**
	 * M�thode permettant de v�rifier si le drapeau du joueur est entour� de bombes
	 * alli�es. Tous les d�tails sont expliqu�s dans la fonction.
	 * 
	 * @return Bool�en qui indique si le drapeau du joueur est entour� de bombes ou
	 *         non.
	 */

	public boolean isFlagSurrounded() {
		// On instancie un Square uniquement dans le but d'obtenir une information
		// n�cessaire au fonctionnement de la m�thode
		// @see Square#
		Square sq = new Square(playerId);

		// Attribue les coordonn�es selon le num�ro du joueur
		flagX = sq.getFlagPosition(playerId)[0];
		flagY = sq.getFlagPosition(playerId)[1];

		int index = 0;

		// V�rifie que le drapeau n'est pas captur�
		if (!hasFlag() || !hasMinersLeft())
			return false;
		// Gestion des murs � l'aide des m�thodes cr��es dans PawnInteractions
		// @see PawnInteractions
		else {
			// Coordonn�es du drapeau sous la forme d'un couple (x, y)
			// @see PawnInteractions, Couple
			PawnInteractions flagCoord = new PawnInteractions(flagX, flagY);
			// Cr�ation des diff�rentes positions dont il faut analyser le contenu, e.g. (0,
			// 1) est un mouvement verticale d'une unit� vers le haut. On agit sur la grille
			// comme un rep�re cart�sien.
			Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };
			// Utilisation de la m�thode possibleMovements afin d'obtenir les instances
			// Square autour du drapeau et v�rifier les pions qu'elles contiennent, ici on
			// cherche � voir si tous les pions sont des bombes

			Couple atIndex = possibleMovements[index];
			for (int i : flagCoord.availableMovement()) {
				// Si l'instance Square n'est pas un mur donc est accessible, on regarde si le
				// pion contenu n'est pas une bombe, dans ce cas le drapeau n'est pas entour� de
				// bombes
				if (i == 1 && !grid.getSquare(atIndex.getX(), atIndex.getY()).getPawn().isPawnA(10))
					flagSurrounded = false;
			}
			return flagSurrounded;
		}

	}

	/**
	 * M�thode permettant de v�rifier si tous les d�mineurs du joueur n'ont pas
	 * encore �t� saisis. On parcourt la liste des pions vivants du joueur pour voir
	 * si les d�mineurs y sont contenus.
	 * 
	 * @return Bool�en qui indique si les d�mineurs du joueur ont �t� saisis ou non.
	 */

	@SuppressWarnings("unlikely-arg-type")
	public boolean hasMinersLeft() {
		// Parcourt la liste des pions encore vivants pour voir si elle contient au
		// moins un d�mineur.
		for (Pawn pawn : alivePawns) {
			boolean pawnAMiner = pawn.isPawnA(2);
			if (!alivePawns.contains(pawnAMiner)) {
				hasMinersLeft = false;
				break;
			}
		}
		// Retourne vrai ou faux en fonction de si le joueur a au moins un d�mineur.
		return hasMinersLeft;

	}

	public boolean hasWeakerPawns(Player p2) {
		PawnInteractions couple;
		// Parcourt la liste des deux joueurs pour voir si le joueur 1 a des pi�ces
		// moins puissantes que ceux du joueur 2.
		for (Pawn oppPawn : p2.alivePawns) { // oppPawn = ennemi
			for (Pawn pawn : alivePawns) {
				couple = new PawnInteractions(pawn.getSquare(grid), oppPawn.getSquare(grid));
				int fight = couple.evaluateFighting();
				if (fight >= 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * M�thode permettant de v�rifier le joueur a gagn� la partie. Une partie est
	 * gagn�e si et seulement si l'�tat du jeu r�pond � un de ces crit�res:
	 * 
	 * - Le joueur adverse (p2) n'a plus de drapeau. - Le joueur a encercl� son
	 * drapeau de bombes et le joueur adverse (p2) n'a plus de d�mineurs. - TODO Le
	 * joueur adverse (p2) est dans une situation o� il ne peut plus bouger. - TODO
	 * Le joueur adverse (p2) est dans une situation o� toutes ses pi�ces sont
	 * strictements plus faibles que celles du joueur.
	 * 
	 * @return Bool�en qui indique si le joueur a gagn� ou pas.
	 */

	public boolean checkWin(Player p2) {
		return (!p2.hasFlag()) || (isFlagSurrounded() && !p2.hasMinersLeft()) || (p2.hasWeakerPawns(this));
	}

	// Quelques accesseurs et mutateurs

	public ArrayList<Pawn> getAlivePawns() {
		return alivePawns;
	}

	public ArrayList<Pawn> getDeathPawns() {
		return deathPawns;
	}

}