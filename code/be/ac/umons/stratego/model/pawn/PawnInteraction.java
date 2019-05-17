package be.ac.umons.stratego.model.pawn;

import java.util.ArrayList;

import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;

/**
 * <h1>PawnInteractions</h1>
 * 
 * <p>
 * Classe permettant de définir les interactions possibles entre une instance
 * Pawn et Grid/Pawn. Elle possède divers méthodes dont des méthodes de
 * déplacements, de contact entre deux instances de Pawn. Cette classe hérite de
 * la classe Couple qui permet de représenter deux objets (int ou Square) sous
 * forme d'un couple. - <i>cf. Couple.java</i>
 * </p>
 * 
 * @see Couple
 */

public class PawnInteraction extends Couple {

	private static final long serialVersionUID = -4996086782636355837L;
	private Grid grid;

	/**
	 * Constructeur par défaut permettant de manipuler deux instances de Square.
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
	 * Méthode permettant de simplifier la récupération du rang d'un pion dans une
	 * instance Square.
	 * 
	 * @param square Instance Square dont on veut récupérer le rang du pion contenu
	 * @return Le rang du pion contenu dans le paramètre square.
	 */

	public int getRank(Square square) {
		return square.getPawn().getRank();
	}

	/**
	 * Méthode permettant de simplifier la récupération d'une instance Square.
	 * 
	 * @param Coordonnées (x,y)
	 */

	public Square getSquare(int x, int y) {
		return grid.getSquare(x, y);
	}

	/**
	 * Méthode qui permet d'évaluer le vainqueur d'un duel entre pawnA (pion qui
	 * attaque) et pawnB (pion que se fait attaquer) où pawnA et pawnB sont deux
	 * instances de Pawn. Cette méthode sera appelée dans doFighting().
	 * 
	 * @return 1 Dans le cas où pawnA est plus fort que pawnB sinon 0 dans le cas
	 *         d'un duel nul entre pawnA et pawnB sinon -1 dans le cas où pawnA est
	 *         plus faible
	 * 
	 * @see PawnInteractions#doFighting()
	 */

	public int evaluateFighting() {

		if (getSquareB().getPawn() != null) {
			if ((getRank(getSquareA()) == 0 && getRank(getSquareB()) == 9) // Espion plus fort que le Maréchal
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
			// Jamais le cas, sert surtout à mieux contrôler les erreurs JUnit.
			return -2;
	}

	/**
	 * Méthode qui permet d'effectuer les actions qui découlent du duel, afin
	 * d'évaluer le résultat du duel, on fait appel à evaluateFighting(). Il existe
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
				getSquareA().getPawn().setSquare(null);
				getSquareA().removePawn();
				break;
			}
		}
	}

	/**
	 * Méthode permettant de vérifier si des coordonnées représentées par les
	 * paramètres (row, column) sont dans la matrice carrée 10x10 (Grid).
	 * 
	 * @param row    Représente la rangée d'une instance Square.
	 * @param column Représente la colonne d'une instance Square.
	 * 
	 * @return Un booléen qui vérifie la condition énoncée ci-dessus.
	 * 
	 * @see Grid
	 */

	public boolean stayInBoard(int row, int column) {

		return (((row >= 0) && (row < 10)) && ((column >= 0) && (column < 10)));
	}

	/**
	 * Méthode permettant de créer une liste dynamique représentant les différents
	 * mouvements légaux qu'un pion (à partir d'une instance Square) peut effectuer
	 * 
	 * @return Une ArrayList permettant de représenter les cases dans lesquelles le
	 *         pion peut aller.
	 */

	public ArrayList<Couple> availableMovement() {

		ArrayList<Couple> evaluation = new ArrayList<Couple>();

		// Dans ce cas on agit sur un pion de portée 1.
		if (getSquare(getX(), getY()).getPawn().getRange() == 1) {
			Couple[] availableMovement = { new Couple(1, 0), new Couple(0, 1), new Couple(-1, 0), new Couple(0, -1) };
			for (Couple c : availableMovement) {
				Square initialSquare = getSquare(getX(), getY());
				Square finalSquare = getSquare(getX() + c.getX(), getY() + c.getY());
				if (stayInBoard(finalSquare.getRow(), finalSquare.getColumn())) {
					if (new PawnInteraction(initialSquare, finalSquare, grid).isMovePossible())
						evaluation.add(new Couple(getX() + c.getX(), getY() + c.getY()));
				}

			}
		}
		// Dans ce cas on agit sur le pion éclaireur qui a une portée illimitée tant
		// qu'il ne saute pas de pion.
		else {

			boolean[] pawnSeen = { false, false, false, false };
			int counter = 0;
			Couple[] availableMovement = { new Couple(1, 0, 0), new Couple(0, 1, 1), new Couple(-1, 0, 2),
					new Couple(0, -1, 3) };

			for (Couple c : availableMovement) {

				while (pawnSeen[counter] == false) {
					Square initialSquare = getSquare(getX(), getY());
					Square destinationSquare = getSquare(getX() + c.getX(), getY() + c.getY());

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

				}
				counter++;
			}

		}

		return evaluation;
	}

	/**
	 * Méthode permettant de calculer la possiblité d'un mouvement selon plusieurs
	 * conditions, rappelons qu'un mouvement est possible ssi: Le mouvement est
	 * horizontal ou vertical, le mouvement donné rentre dans la portée du pion et
	 * qu'il n'y ai aucun pion du même joueur dans la destination.
	 * 
	 * 
	 * @return Si le mouvement est possible ou pas.
	 */

	public boolean isMovePossible() {

		int range = getSquareA().getPawn().getRange();
		int differenceRow = getSquareB().getRow() - getSquareA().getRow();
		int differenceColumn = getSquareB().getColumn() - getSquareA().getColumn();

		// Gestion tentative diagonale et gestion portée (l'un implique l'autre),
		// vérifie si le mouvement reste dans la grille et si la destination est
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

		int differenceRow = getSquareB().getRow() - getSquareA().getRow();
		int differenceColumn = getSquareB().getColumn() - getSquareA().getColumn();

		if ((Math.abs(differenceRow) > 0) && (getSquareB().getRow() > getSquareA().getRow())) {
			for (int i = getSquareA().getRow(); i < getSquareB().getRow(); i++) {
				if ((getSquare(i, getSquareA().getColumn()).getAccess() != true)
						|| (getSquare(i, getSquareA().getColumn()).getPawn().getPlayer() == 1))
					return false;
			}
			return true;
		}

		else if ((Math.abs(differenceRow) > 0) && (getSquareB().getRow() < getSquareA().getRow())) {
			for (int i = getSquareA().getColumn(); i > getSquareB().getColumn(); i--) {
				if ((getSquare(i, getSquareA().getColumn()).getAccess() != true)
						|| (getSquare(i, getSquareA().getColumn()).getPawn().getPlayer() == 1))
					return false;
			}
			return true;
		}
		
		else if ((Math.abs(differenceColumn) > 0) && (getSquareB().getColumn() > getSquareA().getColumn())) {
			for (int i = getSquareA().getRow(); i < getSquareB().getRow(); i++) {
				if ((getSquare(getSquareA().getRow(), i).getAccess() != true)
						|| (getSquare(getSquareA().getRow(), i).getPawn().getPlayer() == 1))
					return false;
			}
			return true;
		}

		else if ((Math.abs(differenceColumn) > 0) && (getSquareB().getColumn() < getSquareA().getColumn())) {
			for (int i = getSquareA().getRow(); i > getSquareB().getRow(); i--) {
				if ((getSquare(getSquareA().getRow(), i).getAccess() != true)
						|| (getSquare(getSquareA().getRow(), i).getPawn().getPlayer() == 1))
					return false;
			}
			return true;
		}
		return true;
	}

}
