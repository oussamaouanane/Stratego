package com.stratego.model;

import java.util.ArrayList;

/**
 * <h1>PawnInteractions</h1>
 * 
 * <p>
 * Classe permettant de d�finir les interactions possibles entre une instance
 * Pawn et Grid/Pawn. Elle poss�de divers m�thodes dont des m�thodes de
 * d�placements, de contact entre deux instances de Pawn. Cette classe h�rite de
 * la classe Couple qui permet de repr�senter deux objets (int ou Square) sous
 * forme d'un couple. - <i>cf. Couple.java</i>
 * </p>
 * 
 * @see Couple
 * @author O.S
 */

public class PawnInteractions extends Couple {

	Grid grid;

	/**
	 * Constructeur par d�faut
	 */

	public PawnInteractions(Square squareA, Square squareB) {
		super(squareA, squareB);
	}

	public PawnInteractions(int row, int column) {
		super(row, column);
	}

	private int getRank(Square square) {
		return square.getPawn().getRank();
	}

	/**
	 * M�thode qui permet d'�valuer le vainqueur d'un duel entre pawnA (pion qui
	 * attaque) et pawnB (pion que se fait attaquer) o� pawnA et pawnB sont deux
	 * instances de Pawn.
	 * 
	 * @return 1 Dans le cas o� pawnA est plus fort que pawnB.
	 * @return 0 Dans le cas d'un duel nul entre pawnA et pawnB.
	 * @return -1 Dans le cas o� pawnA est plus faible
	 */

	public int evaluateFighting() {

		if ((getRank(getSquareA()) == 0 && getRank(getSquareB()) == 9) // Espion plus fort que le Mar�chal
				|| (getRank(getSquareA()) == 2 && getRank(getSquareB()) == 10) // Mineur plus fort que la bombe
				|| (getRank(getSquareB()) == 11) // pawnB est un drapeau
				|| (getRank(getSquareA()) > getRank(getSquareB()))) // PawnA > PawnB
			return 1;
		else if (getRank(getSquareA()) == getRank(getSquareB())) // PawnA == PawnB
			return 0;
		else // PawnA < PawnB
			return -1;
	}

	/**
	 * M�thode qui permet d'effectuer les actions qui d�coulent du duel, afin
	 * d'�valuer le r�sultat du duel, on fait appel � evaluateFighting(). Il existe
	 * trois cas possibles:
	 * 
	 * evaluateFighting() = 1 - pawnA prend la place de pawnB cf. Square.java
	 * evaluateFighting() = 0 - pawnA et pawnB n'ont plus de place cf. Square.java
	 * evaluateFighting() = -1 - pawnA se voit supprimer sa place cf. Square.java
	 * 
	 * @see PawnInteractions#evaluateFighting()
	 */

	public void doFighting() {

		int evaluation = evaluateFighting();

		switch (evaluation) {
		case 1:
			// pawnA > PawnB
			getSquareB().setPawn(getSquareA().getPawn());
			break;
		case 0:
			// pawnA = PawnB
			getSquareA().removePawn();
			getSquareB().removePawn();
			break;
		case -1:
			// pawnA < pawnB
			getSquareA().removePawn();
			break;
		}
	}

	/**
	 * 
	 * @param initialRow Repr�sente la ligne d'une instance Square.
	 * @param initialCol Repr�sente la colonne d'une instance Square.
	 * @param coord      Repr�sente un couple de int.
	 * 
	 * @return Un bool�en qui repr�sente si le mouvement est l�gal.
	 * 
	 * @see Couple
	 * @see PawnInteractions#evaluateFighting()
	 */

	public Integer isMovePossible(int initialRow, int initialCol, Couple coord) {

		int finalRow = (initialRow + coord.getX());
		int finalCol = (initialCol + coord.getY());
		boolean stayInBoard = (((finalRow > 0) && (finalRow < 10)) && ((finalCol > 0) && (finalCol < 10)));

		if (stayInBoard && grid.getSquare(finalRow, finalCol).getAccess())
			return 1;
		else
			return 0;
		
	}

	/**
	 * M�thode permettant de cr�er une liste dynamique repr�sentant les diff�rents
	 * mouvements l�gaux qu'un pion (� partir d'une instance Square) peut effectuer
	 * 
	 * @return Une ArrayList permettant de repr�senter si le pion peut aller (haut,
	 *         droite, bas, gauche)
	 */

	public ArrayList<Integer> availableMovement() {

		Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };
		ArrayList<Integer> evaluation = new ArrayList<Integer>();

		for (Couple c : possibleMovements)
			evaluation.add(isMovePossible(getSquareA().getRow(), getSquareA().getColumn(), c));

		return evaluation;

	}

	/**
	 * M�me chose que availableMovement() mais pour l'�claireur car sa port�e est
	 * plus grande.
	 * 
	 * @see PawnInteractions#availableMovement()
	 */

	public ArrayList<Integer> availableMovementScout() {

		// Initialisation de range.
		ArrayList<Integer> range = new ArrayList<Integer>();
		for (int i = 1; i <= 4; i++)
			range.add(0);

		Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				while (isMovePossible(getSquareA().getRow(), getSquareA().getColumn(), possibleMovements[i]) == 1)
					range.set(i, range.get(i) + 1);
			}
		}

		return range;
	}

	/**
	 * M�thode permettant de calculer la possiblit� d'un mouvement selon plusieurs
	 * conditions, rappelons qu'un mouvement est possible ssi: Le mouvement est
	 * horizontal ou vertical et le mouvement donn� rentre dans la port�e du pion.
	 * 
	 * @return Si le mouvement est possible ou pas.
	 */

	public boolean canMove() {

		int range = getSquareA().getPawn().getRange();
		int differenceRow = getSquareB().getRow() - getSquareA().getRow();
		int differenceColumn = getSquareB().getColumn() - getSquareA().getColumn();

		// Gestion tentative diagonale et gestion port�e (l'un implique l'autre)

		if ((differenceRow == 0 && differenceColumn == 0) || (differenceRow > range) && (differenceColumn <= range))
			return false;
		else
			return true;

	}
}
