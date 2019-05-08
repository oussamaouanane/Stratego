package be.ac.umons.stratego.model.player;

import java.util.ArrayList;

import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.pawn.PawnInteraction;

/**
 * <h1>Player</h1>
 * 
 * <p>
 * Classe permettant de mod�liser un joueur, il existe au plus deux joueurs lors
 * d'une partie. Chaque joueur poss�de un set de 40 pions. La classe poss�de des
 * m�thodes li�es � la gestion d'une partie et de son organisation. On y
 * retrouvera donc des m�thodes qui v�rifient l'�tat actuelle de la partie pour
 * l'instance du joueur donn�e.
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

	private ArrayList<Pawn> alivePawns;

	private Grid grid;

	/**
	 * Constructeur permettant de cr�er le joueur humain.
	 * 
	 * @param grid La grille o� la partie se d�roule.
	 */

	public Player(Grid grid) {
		initializeVariable();
		this.grid = grid;
		
		playerId = 1;
	}
	
	public void initializePawns() {
		for (int i = 0; i < 40; i++) {
			for (int c : Pawn.PAWNS_COMPOSITION)
				alivePawns.add(new Pawn(c, playerId));
		}
	}

	public void initializeVariable() {
		hasFlag = hasMinersLeft = flagSurrounded = true;
	}

	public void flagPosition() {
		switch (playerId) {

		case 1:
			flagX = Square.flagA[0];
			flagY = Square.flagA[1];
			break;
		case 2:
			flagX = Square.flagB[0];
			flagY = Square.flagB[1];
			break;
		}
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

			// if grid.getSquare(flagX, flagY).getPawn == null
			// hasFlag = False;
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

		int index = 0;

		// V�rifie que le drapeau n'est pas captur�
		if (!hasFlag())
			return false;
		// Gestion des murs � l'aide des m�thodes cr��es dans PawnInteraction
		// @see PawnInteraction
		else {
			// Coordonn�es du drapeau sous la forme d'un couple (x, y)
			// @see PawnInteraction, Couple
			PawnInteraction flagCoord = new PawnInteraction(flagX, flagY, grid);
			// Cr�ation des diff�rentes positions dont il faut analyser le contenu, e.g. (0,
			// 1) est un mouvement verticale d'une unit� vers le haut. On agit sur la grille
			// comme un rep�re cart�sien.
			Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };
			// Utilisation de la m�thode possibleMovements afin d'obtenir les instances
			// Square autour du drapeau et v�rifier les pions qu'elles contiennent, ici on
			// cherche � voir si tous les pions sont des bombes

			Couple atIndex = possibleMovements[index];
			// for (int i : flagCoord.availableMovement()) {
			// Si l'instance Square n'est pas un mur donc est accessible, on regarde si le
			// pion contenu n'est pas une bombe, dans ce cas le drapeau n'est pas entour� de
			// bombes. i == 1 car on avait dit que si le mouvement �tait possible, on
			// retournait 1 dans la m�thode availableMovement().

			// @see PawnInteraction#availableMovement()
			// if (i == 1 && !grid.getSquare(atIndex.getX(),
			// atIndex.getY()).getPawn().isPawnA(10))
			flagSurrounded = false;
		}
		return flagSurrounded;
	}

	// }

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
		PawnInteraction couple;
		// Parcourt la liste des deux joueurs pour voir si le joueur 1 a des pi�ces
		// moins puissantes que ceux du joueur 2.
		for (Pawn oppPawn : p2.alivePawns) { // oppPawn = ennemi
			for (Pawn pawn : alivePawns) {
				couple = new PawnInteraction(pawn.getSquare(), oppPawn.getSquare(), grid);
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
	 * joueur adverse (p2) est dans une situation o� il ne peut plus bouger. - Le
	 * joueur adverse (p2) est dans une situation o� toutes ses pi�ces sont
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
	
	public Grid getGrid() {
		return grid;
	}
}
