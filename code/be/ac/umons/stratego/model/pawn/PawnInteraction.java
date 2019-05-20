package be.ac.umons.stratego.model.pawn;

import java.util.ArrayList;

import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;

/**
 * <h1>PawnInteractions</h1>
 * 
 * <p>
 * Classe permettant de definir les interactions possibles entre une instance
 * Pawn et Grid/Pawn. Elle possede divers methodes dont des methodes de
 * deplacements, de contact entre deux instances de Pawn. Cette classe herite de
 * la classe Couple qui permet de representer deux objets (int ou Square) sous
 * forme d'un couple. - <i>cf. Couple.java</i>
 * </p>
 * 
 * @see Couple
 */

public class PawnInteraction extends Couple {

	private static final long serialVersionUID = -4996086782636355837L;
	private Grid grid;

	/**
	 * Constructeur par defaut permettant de manipuler deux instances de Square.
	 */

	public PawnInteraction(Square squareA, Square squareB, Grid grid) {
		super(squareA, squareB);
		this.grid = grid;
	}

	public PawnInteraction(int row, int column, Grid grid) {
		super(row, column);
		this.grid = grid;
	}

	public PawnInteraction(Couple movement, Grid grid) {
		super(movement.getX(), movement.getY());
		this.grid = grid;
	}

	/**
	 * Methode permettant de simplifier la recuperation du rang d'un pion dans une
	 * instance Square.
	 * 
	 * @param square Instance Square dont on veut recuperer le rang du pion contenu
	 * @return Le rang du pion contenu dans le parametre square.
	 */

	public int getRank(Square square) {
		return square.getPawn().getRank();
	}

	/**
	 * Methode permettant de simplifier la recuperation d'une instance Square.
	 * 
	 * @param Coordonnees (x,y)
	 */

	public Square getSquare(int x, int y) {
		return grid.getSquare(x, y);
	}

	/**
	 * Methode qui permet d'evaluer le vainqueur d'un duel entre pawnA (pion qui
	 * attaque) et pawnB (pion que se fait attaquer) ou pawnA et pawnB sont deux
	 * instances de Pawn. Cette methode sera appelee dans doFighting().
	 * 
	 * @return 1 Dans le cas ou pawnA est plus fort que pawnB sinon 0 dans le cas
	 *         d'un duel nul entre pawnA et pawnB sinon -1 dans le cas ou pawnA est
	 *         plus faible
	 * 
	 * @see PawnInteractions#doFighting()
	 */

	public int evaluateFighting() {

		if (getSquareB().getPawn() != null) {
			if ((getRank(getSquareA()) == 0 && getRank(getSquareB()) == 9) // Espion plus fort que le Marechal
					|| (getRank(getSquareA()) == 2 && getRank(getSquareB()) == 10) // Mineur plus fort que la bombe
					|| (getRank(getSquareB()) == 11) // pawnB est un drapeau
					|| (getRank(getSquareA()) > getRank(getSquareB()))) // PawnA > PawnB
				return 1;
			else if (getRank(getSquareA()) == getRank(getSquareB())) // PawnA == PawnB
				return 0;
			else // PawnA < PawnB
				return -1;
		}

		else
			// Jamais le cas, sert surtout a mieux controler les erreurs JUnit.
			return -2;
	}

	/**
	 * Methode qui permet d'effectuer les actions qui decoulent du duel, afin
	 * d'evaluer le resultat du duel, on fait appel a evaluateFighting(). Il existe
	 * trois cas possibles:
	 * 
	 * evaluateFighting() = 1 - pawnA prend la place de pawnB cf. Square.java
	 * evaluateFighting() = 0 - pawnA et pawnB n'ont plus de place cf. Square.java
	 * evaluateFighting() = -1 - pawnA se voit supprimer sa place cf. Square.java
	 * 
	 * @see PawnInteractions#evaluateFighting()
	 */

	public void doFighting() {

		if (getSquareB().getPawn() != null) {
			int evaluation = evaluateFighting();

			switch (evaluation) {

			case 1:
				// pawnA > PawnB
				getSquareB().setPawn(getSquareA().getPawn());
				getSquareB().getPawn().setSquare(getSquareB());
				getSquareA().getPawn().setSquare(null);
				getSquareA().removePawn();
				break;
			case 0:
				// pawnA = PawnB
				getSquareA().getPawn().setSquare(null);
				getSquareA().removePawn();
				getSquareB().getPawn().setSquare(null);
				getSquareB().removePawn();
				break;
			case -1:
				// pawnA < pawnB
				getSquareA().removePawn();
				break;
			}
		}
	}

	/**
	 * Methode permettant de verifier si des coordonnees representees par les
	 * parametres (row, column) sont dans la matrice carree 10x10 (Grid).
	 * 
	 * @param row    Represente la rangee d'une instance Square.
	 * @param column Represente la colonne d'une instance Square.
	 * 
	 * @return Un booleen qui verifie la condition enoncee ci-dessus.
	 * 
	 * @see Grid
	 */

	public static boolean stayInBoard(int row, int column) {

		return (((row >= 0) && (row < 10)) && ((column >= 0) && (column < 10)));
	}

	/**
	 * Methode permettant de creer une liste dynamique representant les differents
	 * mouvements legaux qu'un pion (a partir d'une instance Square) peut effectuer
	 * 
	 * @return Une ArrayList permettant de representer les cases dans lesquelles le
	 *         pion peut aller.
	 */

	public ArrayList<Couple> availableMovement() {

		ArrayList<Couple> evaluation = new ArrayList<Couple>();

		// Dans ce cas on agit sur un pion de portee 1.
		if ((getSquare(getX(), getY()).getPawn() != null) && (getSquare(getX(), getY()).getPawn().getRange() == 1)) {
			Couple[] availableMovement = { new Couple(1, 0), new Couple(0, 1), new Couple(-1, 0), new Couple(0, -1) };
			for (Couple c : availableMovement) {
				Square initialSquare = getSquare(getX(), getY());
				if (stayInBoard(getX() + c.getX(), getY() + c.getY())) {
					Square finalSquare = getSquare(getX() + c.getX(), getY() + c.getY());
					if (new PawnInteraction(initialSquare, finalSquare, grid).isMovePossible())
						evaluation.add(new Couple(getX() + c.getX(), getY() + c.getY()));
				}

			}
		}
		// Dans ce cas on agit sur le pion eclaireur qui a une portee illimitee tant
		// qu'il ne saute pas de pion.
		else if ((getSquare(getX(), getY()).getPawn() != null) && (getSquare(getX(), getY()).getPawn().getRange() == 9)) {

			boolean[] pawnSeen = { false, false, false, false };
			int counter = 0;
			Couple[] availableMovement = { new Couple(1, 0, 0), new Couple(0, 1, 1), new Couple(-1, 0, 2),
					new Couple(0, -1, 3) };

			for (Couple c : availableMovement) {

				while (pawnSeen[counter] == false) {
					Square initialSquare = getSquare(getX(), getY());
					Square destinationSquare;
					if (PawnInteraction.stayInBoard(getX() + c.getX(), getY() + c.getY())) {
						destinationSquare = getSquare(getX() + c.getX(), getY() + c.getY());

						if (new PawnInteraction(initialSquare, destinationSquare, grid).isMovePossible()) {
							evaluation.add(new Couple(destinationSquare.getRow(), destinationSquare.getColumn()));
							int direction = c.getDirection();

							switch (direction) {
							case 0:
								c = new Couple(c.getX() + 1, 0, 0);
								break;
							case 1:
								c = new Couple(0, c.getY() + 1, 1);
								break;
							case 2:
								c = new Couple(c.getX() + -1, 0, 2);
								break;
							case 3:
								c = new Couple(0, c.getY() + 1, 3);
								break;

							}

							if (destinationSquare.getPawn() != null)
								pawnSeen[counter] = true;
						}

						else
							pawnSeen[counter] = true;
					} else
						break;
				}
				counter++;
			}

		}

		return evaluation;
	}

	/**
	 * Methode permettant de calculer la possiblite d'un mouvement selon plusieurs
	 * conditions, rappelons qu'un mouvement est possible ssi: Le mouvement est
	 * horizontal ou vertical, le mouvement donne rentre dans la portee du pion et
	 * qu'il n'y ai aucun pion du meme joueur dans la destination.
	 * 
	 * 
	 * @return Si le mouvement est possible ou pas.
	 */

	public boolean isMovePossible() {

		int range = getSquareA().getPawn().getRange();
		int differenceRow = getSquareB().getRow() - getSquareA().getRow();
		int differenceColumn = getSquareB().getColumn() - getSquareA().getColumn();
		// Gestion tentative diagonale et gestion portee (l'un implique l'autre),
		// verifie si le mouvement reste dans la grille et si la destination est
		// accessible.

		if ((differenceRow != 0 && differenceColumn != 0) || (Math.abs(differenceRow) > range)
				|| (Math.abs(differenceColumn) > range)
				|| (!stayInBoard(getSquareB().getRow(), getSquareB().getColumn())) || (!getSquareB().getAccess())
				|| (getSquareB().getPawn() != null
						&& getSquareB().getPawn().getPlayer() == getSquareA().getPawn().getPlayer()))
			return false;

		return true;
	}

	public boolean isMovePossibleScout() {

		Couple destination = new Couple(getSquareB().getRow(), getSquareB().getColumn());
		ArrayList<Couple> availableMove = new PawnInteraction(getSquareA().getRow(), getSquareA().getColumn(), grid)
				.availableMovement();
		for (Couple c : availableMove) {
			if ((c.getX() == destination.getX()) && (c.getY() == destination.getY()))
				return true;
		}
		return false;
	}

}
