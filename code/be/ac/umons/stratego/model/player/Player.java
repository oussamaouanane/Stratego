package be.ac.umons.stratego.model.player;

import java.io.Serializable;
import java.util.ArrayList;

import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.grid.Grid;
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

	private ArrayList<Pawn> alivePawns = new ArrayList<Pawn>();

	private int score = 0;
	private Grid grid;

	/**
	 * Constructeur permettant de creer le joueur humain.
	 * 
	 * @param grid La grille ou la partie se deroule.
	 */

	public Player(Grid grid) {
		this.grid = grid;
		playerId = 1;
	}

	public void initializePawns() {
		for (int i = 0; i < 40; i++) {
			for (int c : Pawn.PAWNS_COMPOSITION)
				alivePawns.add(new Pawn(c, playerId));
		}
	}

	/**
	 * Methode permettant de rechercher la position du drapeau.
	 */

	public void flagPosition() {

		for (Pawn p : alivePawns) {
			if (p.isPawnA(11)) {
				flagX = p.getSquare().getRow();
				flagY = p.getSquare().getColumn();
			}
		}
	}

	/**
	 * Methode permettant de verifier si les pions du joueur sont bloques.
	 * 
	 * @return Booleen qui indique si le joueur peut encore bouger ses pions.
	 */

	public boolean canMovePawn() {
		for (Pawn c : alivePawns) {
			if ((new PawnInteraction(c.getSquare().getRow(), c.getSquare().getColumn(), grid).availableMovement() != null) && (!new PawnInteraction(c.getSquare().getRow(), c.getSquare().getColumn(), grid).availableMovement()
					.isEmpty()))
				return true;
		}
		return false;
	}

	/**
	 * Methode permettant de verifier si le drapeau du joueur n'a pas encore ete
	 * saisi. On parcourt la liste des pions vivants du joueur pour voir si le
	 * drapeau y est contenu.
	 * 
	 * @return Booleen qui indique si le drapeau du joueur a ete saisi ou non.
	 */

	public boolean hasFlag() {
		// Parcourt la liste des pions encore vivants pour voir si elle contient encore
		// le drapeau.
		for (Pawn pawn : alivePawns) {
			if (pawn.isPawnA(11)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Methode permettant de verifier si le drapeau du joueur est entoure de bombes
	 * alliees. Tous les details sont expliques dans la fonction.
	 * 
	 * @return Booleen qui indique si le drapeau du joueur est entoure de bombes ou
	 *         non.
	 */

	public boolean isFlagSurrounded() {

		// Verifie que le drapeau n'est pas capture
		if (!hasFlag())
			return false;

		flagPosition();
		Couple[] possibleMovements = { new Couple(1, 0), new Couple(0, 1), new Couple(-1, 0), new Couple(0, -1) };

		for (Couple i : possibleMovements) {
			if (PawnInteraction.stayInBoard(flagX + i.getX(), flagY + i.getY())) {
				if ((grid.getSquare(flagX + i.getX(), flagY + i.getY()).getPawn() != null)
						&& (!grid.getSquare(flagX + i.getX(), flagY + i.getY()).getPawn().isPawnA(10))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Methode permettant de verifier si tous les demineurs du joueur n'ont pas
	 * encore ete saisis. On parcourt la liste des pions vivants du joueur pour voir
	 * si les demineurs y sont contenus.
	 * 
	 * @return Booleen qui indique si les demineurs du joueur ont ete saisis ou non.
	 */

	public boolean hasMinersLeft() {
		// Parcourt la liste des pions encore vivants pour voir si elle contient au
		// moins un demineur.
		for (Pawn pawn : alivePawns) {
			if (pawn.isPawnA(2))
				return true;
		}
		return true;
	}

	/**
	 * Methode permettant de verifier le joueur a gagne la partie. Une partie est
	 * gagnee si et seulement si l'etat du jeu repond a un de ces criteres:
	 * 
	 * - Le joueur adverse (p2) n'a plus de drapeau. - Le joueur a encercle son
	 * drapeau de bombes et le joueur adverse (p2) n'a plus de demineurs. - Le
	 * joueur adverse (p2) est dans une situation ou il ne peut plus bouger.
	 * 
	 * @return Booleen qui indique si le joueur a gagne ou pas.
	 */

	public boolean checkWin(Player p2) {
		return ((!p2.hasFlag())) || (isFlagSurrounded() && !p2.hasMinersLeft())
		 || (!p2.canMovePawn());
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

	// Pour junit
	public void setAlivePawns(ArrayList<Pawn> pawn) {
		alivePawns = pawn;
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
