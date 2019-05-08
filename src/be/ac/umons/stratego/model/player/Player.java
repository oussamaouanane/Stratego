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
 * Classe permettant de modéliser un joueur, il existe au plus deux joueurs lors
 * d'une partie. Chaque joueur possède un set de 40 pions. La classe possède des
 * méthodes liées à la gestion d'une partie et de son organisation. On y
 * retrouvera donc des méthodes qui vérifient l'état actuelle de la partie pour
 * l'instance du joueur donnée.
 * </p>
 *
 */

public class Player {

	/**
	 * Variables d'instances permettant de vérifier si une partie est terminée.
	 * Ensemble de booléens. hasFlag: le joueur a un drapeau. flagSurrounded: le
	 * drapeau du joueur est entouré de bombes. hasMinersLeft: le joueur a encore
	 * des démineurs.
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
	 * Constructeur permettant de créer le joueur humain.
	 * 
	 * @param grid La grille où la partie se déroule.
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
	 * Méthode permettant de vérifier si le drapeau du joueur n'a pas encore été
	 * saisi. On parcourt la liste des pions vivants du joueur pour voir si le
	 * drapeau y est contenu.
	 * 
	 * @return Booléen qui indique si le drapeau du joueur a été saisi ou non.
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
	 * Méthode permettant de vérifier si le drapeau du joueur est entouré de bombes
	 * alliées. Tous les détails sont expliqués dans la fonction.
	 * 
	 * @return Booléen qui indique si le drapeau du joueur est entouré de bombes ou
	 *         non.
	 */

	public boolean isFlagSurrounded() {

		int index = 0;

		// Vérifie que le drapeau n'est pas capturé
		if (!hasFlag())
			return false;
		// Gestion des murs à l'aide des méthodes créées dans PawnInteraction
		// @see PawnInteraction
		else {
			// Coordonnées du drapeau sous la forme d'un couple (x, y)
			// @see PawnInteraction, Couple
			PawnInteraction flagCoord = new PawnInteraction(flagX, flagY, grid);
			// Création des différentes positions dont il faut analyser le contenu, e.g. (0,
			// 1) est un mouvement verticale d'une unité vers le haut. On agit sur la grille
			// comme un repère cartésien.
			Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };
			// Utilisation de la méthode possibleMovements afin d'obtenir les instances
			// Square autour du drapeau et vérifier les pions qu'elles contiennent, ici on
			// cherche à voir si tous les pions sont des bombes

			Couple atIndex = possibleMovements[index];
			// for (int i : flagCoord.availableMovement()) {
			// Si l'instance Square n'est pas un mur donc est accessible, on regarde si le
			// pion contenu n'est pas une bombe, dans ce cas le drapeau n'est pas entouré de
			// bombes. i == 1 car on avait dit que si le mouvement était possible, on
			// retournait 1 dans la méthode availableMovement().

			// @see PawnInteraction#availableMovement()
			// if (i == 1 && !grid.getSquare(atIndex.getX(),
			// atIndex.getY()).getPawn().isPawnA(10))
			flagSurrounded = false;
		}
		return flagSurrounded;
	}

	// }

	/**
	 * Méthode permettant de vérifier si tous les démineurs du joueur n'ont pas
	 * encore été saisis. On parcourt la liste des pions vivants du joueur pour voir
	 * si les démineurs y sont contenus.
	 * 
	 * @return Booléen qui indique si les démineurs du joueur ont été saisis ou non.
	 */

	@SuppressWarnings("unlikely-arg-type")
	public boolean hasMinersLeft() {
		// Parcourt la liste des pions encore vivants pour voir si elle contient au
		// moins un démineur.
		for (Pawn pawn : alivePawns) {
			boolean pawnAMiner = pawn.isPawnA(2);
			if (!alivePawns.contains(pawnAMiner)) {
				hasMinersLeft = false;
				break;
			}
		}
		// Retourne vrai ou faux en fonction de si le joueur a au moins un démineur.
		return hasMinersLeft;

	}

	public boolean hasWeakerPawns(Player p2) {
		PawnInteraction couple;
		// Parcourt la liste des deux joueurs pour voir si le joueur 1 a des pièces
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
	 * Méthode permettant de vérifier le joueur a gagné la partie. Une partie est
	 * gagnée si et seulement si l'état du jeu répond à un de ces critères:
	 * 
	 * - Le joueur adverse (p2) n'a plus de drapeau. - Le joueur a encerclé son
	 * drapeau de bombes et le joueur adverse (p2) n'a plus de démineurs. - TODO Le
	 * joueur adverse (p2) est dans une situation où il ne peut plus bouger. - Le
	 * joueur adverse (p2) est dans une situation où toutes ses pièces sont
	 * strictements plus faibles que celles du joueur.
	 * 
	 * @return Booléen qui indique si le joueur a gagné ou pas.
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
