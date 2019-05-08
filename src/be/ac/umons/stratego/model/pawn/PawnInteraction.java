package be.ac.umons.stratego.model.pawn;

import java.util.ArrayList;

import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.grid.Grid;
import be.ac.umons.stratego.model.grid.Square;

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
 */

public class PawnInteraction extends Couple {

	private Grid grid;

	/**
	 * Constructeur par d�faut
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
	 * M�thode permettant de simplifier la r�cup�ration du rang d'un pion dans une
	 * instance Square.
	 * 
	 * @param square Instance Square dont on veut r�cup�rer le rang du pion contenu
	 * @return Le rang du pion contenu dans le param�tre square.
	 */

	public int getRank(Square square) {
		return square.getPawn().getRank();
	}

	/**
	 * M�thode permettant de simplifier la r�cup�ration d'une instance Square.
	 * 
	 * @param Coordonn�es (x,y)
	 */

	public Square getSquare(int x, int y) {
		return grid.getSquare(x, y);
	}

	/**
	 * M�thode qui permet d'�valuer le vainqueur d'un duel entre pawnA (pion qui
	 * attaque) et pawnB (pion que se fait attaquer) o� pawnA et pawnB sont deux
	 * instances de Pawn. Cette m�thode sera appel�e dans doFighting().
	 * 
	 * @return 1 Dans le cas o� pawnA est plus fort que pawnB sinon 0 dans le cas
	 *         d'un duel nul entre pawnA et pawnB sinon -1 dans le cas o� pawnA est
	 *         plus faible
	 * 
	 * @see PawnInteractions#doFighting()
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
		getSquareB().getPawn().setVisible(true);

		switch (evaluation) {

		case 1:
			// pawnA > PawnB
			getSquareB().setPawn(getSquareA().getPawn());
			getSquareA().removePawn();
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
	 * M�thode permettant de v�rifier si le mp
	 * 
	 * @param initialRow Repr�sente la rang�e d'une instance Square.
	 * @param initialCol Repr�sente la colonne d'une instance Square.
	 * @param coord      Repr�sente un couple de int, mouvement que l'on veut
	 *                   effectuer sur le grille � deux dimensions.
	 * 
	 * @return Un entier (1 ou 0) qui repr�sente si le mouvement est l�gal. On
	 *         aurait pu retourner un bool�en mais pour les autres m�thodes qui
	 *         suivent, 1 et 0 seront utiles.
	 * 
	 * @see Couple
	 * @see PawnInteractions#evaluateFighting()
	 */

	public Integer stayInBoard(int initialRow, int initialCol, Couple coord) {

		// Permet de v�rifier si le mouvement est dans grid et si le carr� est
		// accessible.

		int finalRow = (initialRow + coord.getX());
		int finalCol = (initialCol + coord.getY());
		boolean stayInBoard = (((finalRow > 0) && (finalRow < 10)) && ((finalCol > 0) && (finalCol < 10)));

		if (stayInBoard && getSquare(finalRow, finalCol).getAccess())
			return 1;
		else
			return 0;
	}

	/**
	 * M�thode permettant de v�rifier si des coordonn�es repr�sent�es par les
	 * param�tres (row, column) sont dans la matrice carr�e 10x10 (Grid).
	 * 
	 * @param row    Repr�sente la rang�e.
	 * @param column Repr�sente la colonne.
	 * 
	 * @return Un bool�en qui v�rifie la condition �nonc�e ci-dessus.
	 * 
	 * @see Grid
	 */

	public boolean stayInBoard(int row, int column) {

		return (((row >= 0) && (row < 10)) && ((column >= 0) && (column < 10)));
	}

	public ArrayList<Couple> availableMovementTest() {
		int[] counter = { 0, 0, 0, 0 };
		ArrayList<Couple> evaluation = new ArrayList<Couple>();

		// V�rifie les mouvements
		for (int i = 0; i < 4; i++) {
			while (counter[i] <= getRank(getSquare(getX(), getY()))) {
				switch (i) {
				case 0:
					if (new PawnInteraction(getSquare(getX(), getY()), getSquare(getX() + counter[i], getY()), grid)
							.isMovePossible()) {
						evaluation.add(new Couple(getX(), getY() + counter[i]));
						counter[i]++;
					}
				case 1:
					if (new PawnInteraction(getSquare(getX(), getY()), getSquare(getX() + counter[i], getY()), grid)
							.isMovePossible()) {
						evaluation.add(new Couple(getX() + counter[i], getY()));
						counter[i]++;
					}
				case 2:
					if (new PawnInteraction(getSquare(getX(), getY()), getSquare(getX() + counter[i], getY()), grid)
							.isMovePossible()) {
						evaluation.add(new Couple(getX(), getY() + counter[i]));
						counter[i]--;
					}
				case 3:
					if (new PawnInteraction(getSquare(getX(), getY()), getSquare(getX() + counter[i], getY()), grid)
							.isMovePossible()) {
						evaluation.add(new Couple(getX() + counter[i], getY()));
						counter[i]--;
					}

				}
			}
		}

		return evaluation;

	}

	/**
	 * M�thode permettant de cr�er une liste dynamique repr�sentant les diff�rents
	 * mouvements l�gaux qu'un pion (� partir d'une instance Square) peut effectuer
	 * 
	 * @return Une ArrayList permettant de repr�senter si le pion peut aller (haut,
	 *         droite, bas, gauche)
	 */

	public ArrayList<Couple> availableMovement() {

		Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };
		ArrayList<Couple> evaluation = new ArrayList<Couple>();

		for (Couple c : possibleMovements) {
			// evaluation.add(stayInBoard(getX(), getY(), c));
			if (new PawnInteraction(getSquare(getX(), getY()), getSquare(getX() + c.getX(), getY() + c.getY()), grid)
					.isMovePossible())
				evaluation.add(new Couple(getX() + c.getX(), getY() + c.getY()));

		}

		return evaluation;
	}

	/**
	 * M�me chose que availableMovement() mais pour l'�claireur car sa port�e est
	 * plus grande.
	 * 
	 * @see PawnInteractions#availableMovement()
	 */

	public ArrayList<Integer> availableMovementScout() {
		int initialSquareX = getX();
		int initialSquareY = getY();

		// Initialisation de range, permet de donner la port�e du pion dans les 4
		// directions.
		ArrayList<Integer> range = new ArrayList<Integer>();
		for (int i = 1; i <= 4; i++)
			range.add(0);

		Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				while ((stayInBoard(getX(), getY(), possibleMovements[i]) == 1)
						&& (grid.getSquare(initialSquareX + possibleMovements[i].getX(),
								initialSquareY + possibleMovements[i].getY()).getPawn() != null))
					range.set(i, range.get(i) + 1);
			}
		}

		return range;
	}

	/**
	 * M�thode permettant de calculer la possiblit� d'un mouvement selon plusieurs
	 * conditions, rappelons qu'un mouvement est possible ssi: Le mouvement est
	 * horizontal ou vertical, le mouvement donn� rentre dans la port�e du pion et
	 * qu'il n'y ai aucun pion du m�me joueur dans la destination.
	 * 
	 * 
	 * @return Si le mouvement est possible ou pas.
	 */

	public boolean isMovePossible() {

		int range = getSquareA().getPawn().getRange();
		int differenceRow = getSquareB().getRow() - getSquareA().getRow();
		int differenceColumn = getSquareB().getColumn() - getSquareA().getColumn();

		// Gestion tentative diagonale et gestion port�e (l'un implique l'autre),
		// v�rifie si le mouvement reste dans la grille et si la destination est
		// accessible.
		if ((differenceRow != 0 && differenceColumn != 0) || (Math.abs(differenceRow) > range)
				|| (Math.abs(differenceColumn) > range)
				|| (!stayInBoard(getSquareB().getRow(), getSquareB().getColumn())) || (!getSquareB().getAccess())
				|| (getSquareB().getPawn() != null
						&& getSquareB().getPawn().getPlayer() == getSquareA().getPawn().getPlayer()))

			return false;

		return true;
	}

}
