package be.ac.umons.stratego.model.player;

import java.io.Serializable;
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
 * Classe permettant de modeliser un joueur, il existe au plus deux joueurs lors
 * d'une partie. Chaque joueur possede un set de 40 pions. La classe possede des
 * methodes liees a la gestion d'une partie et de son organisation. On y
 * retrouvera donc des methodes qui verifient l'etat actuelle de la partie pour
 * l'instance du joueur donnee.
 * </p>
 *
 */

public class Player implements Serializable {

	private static final long serialVersionUID = 5843168112852480170L;
	
	/**
	 * Variables d'instances permettant de verifier si une partie est terminee.
	 * Ensemble de booleens. hasFlag: le joueur a un drapeau. flagSurrounded: le
	 * drapeau du joueur est entoure de bombes. hasMinersLeft: le joueur a encore
	 * des demineurs.
	 */

	private int playerId;
	private int flagX;
	private int flagY;
	
	private boolean hasFlag;
	private boolean flagSurrounded;
	private boolean hasMinersLeft;

	private ArrayList<Pawn> alivePawns = new ArrayList<Pawn>();

	private int score = 0;
	private Grid grid;

	/**
	 * Constructeur permettant de creer le joueur humain.
	 * 
	 * @param grid La grille ou la partie se deroule.
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
	 * Methode permettant de verifier si le drapeau du joueur n'a pas encore ete
	 * saisi. On parcourt la liste des pions vivants du joueur pour voir si le
	 * drapeau y est contenu.
	 * 
	 * @return Booleen qui indique si le drapeau du joueur a ete saisi ou non.
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
	 * Methode permettant de verifier si le drapeau du joueur est entoure de bombes
	 * alliees. Tous les details sont expliques dans la fonction.
	 * 
	 * @return Booleen qui indique si le drapeau du joueur est entoure de bombes ou
	 *         non.
	 */
	
	// TODO FIXER CETTE METHODE.

	public boolean isFlagSurrounded() {

		int index = 0;

		// Verifie que le drapeau n'est pas capture
		if (!hasFlag())
			return false;
		// Gestion des murs a l'aide des methodes creees dans PawnInteraction
		// @see PawnInteraction
		else {
			// Coordonnees du drapeau sous la forme d'un couple (x, y)
			// @see PawnInteraction, Couple
			PawnInteraction flagCoord = new PawnInteraction(flagX, flagY, grid);
			// Creation des differentes positions dont il faut analyser le contenu, e.g. (0,
			// 1) est un mouvement verticale d'une unite vers le haut. On agit sur la grille
			// comme un repere cartesien.
			Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };
			// Utilisation de la methode possibleMovements afin d'obtenir les instances
			// Square autour du drapeau et verifier les pions qu'elles contiennent, ici on
			// cherche a voir si tous les pions sont des bombes

			Couple atIndex = possibleMovements[index];
			// for (int i : flagCoord.availableMovement()) {
			// Si l'instance Square n'est pas un mur donc est accessible, on regarde si le
			// pion contenu n'est pas une bombe, dans ce cas le drapeau n'est pas entoure de
			// bombes. i == 1 car on avait dit que si le mouvement etait possible, on
			// retournait 1 dans la methode availableMovement().

			// @see PawnInteraction#availableMovement()
			// if (i == 1 && !grid.getSquare(atIndex.getX(),
			// atIndex.getY()).getPawn().isPawnA(10))
			flagSurrounded = false;
		}
		return flagSurrounded;
	}

	// }

	/**
	 * Methode permettant de verifier si tous les demineurs du joueur n'ont pas
	 * encore ete saisis. On parcourt la liste des pions vivants du joueur pour voir
	 * si les demineurs y sont contenus.
	 * 
	 * @return Booleen qui indique si les demineurs du joueur ont ete saisis ou non.
	 */

	@SuppressWarnings("unlikely-arg-type")
	public boolean hasMinersLeft() {
		// Parcourt la liste des pions encore vivants pour voir si elle contient au
		// moins un demineur.
		for (Pawn pawn : alivePawns) {
			boolean pawnAMiner = pawn.isPawnA(2);
			if (!alivePawns.contains(pawnAMiner)) {
				hasMinersLeft = false;
				break;
			}
		}
		// Retourne vrai ou faux en fonction de si le joueur a au moins un demineur.
		return hasMinersLeft;

	}

	public boolean hasWeakerPawns(Player p2) {
		PawnInteraction couple;
		// Parcourt la liste des deux joueurs pour voir si le joueur 1 a des pieces
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
	 * Methode permettant de verifier le joueur a gagne la partie. Une partie est
	 * gagnee si et seulement si l'etat du jeu repond a un de ces criteres:
	 * 
	 * - Le joueur adverse (p2) n'a plus de drapeau. - Le joueur a encercle son
	 * drapeau de bombes et le joueur adverse (p2) n'a plus de demineurs. - TODO Le
	 * joueur adverse (p2) est dans une situation ou il ne peut plus bouger. - Le
	 * joueur adverse (p2) est dans une situation ou toutes ses pieces sont
	 * strictements plus faibles que celles du joueur.
	 * 
	 * @return Booleen qui indique si le joueur a gagne ou pas.
	 */

	public boolean checkWin(Player p2) {
		return (!p2.hasFlag()) || (isFlagSurrounded() && !p2.hasMinersLeft()) || (p2.hasWeakerPawns(this));
	}
	
	/**
	 * Permet de mettre a jour le score du joueur.
	 */
	
	public void updateScore() {
		score++;
	}

	// Quelques accesseurs et mutateurs

	public ArrayList<Pawn> getAlivePawns() {
		return alivePawns;
	}
	
	public void addPawn(Pawn pawn) {
		alivePawns.add(pawn);
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public int getScore() {
		return score;
	}
	
}

