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

	private Grid grid;

	/**
	 * Constructeur par défaut
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
	 * Méthode permettant de vérifier si le mp
	 * 
	 * @param initialRow Représente la rangée d'une instance Square.
	 * @param initialCol Représente la colonne d'une instance Square.
	 * @param coord      Représente un couple de int, mouvement que l'on veut
	 *                   effectuer sur le grille à deux dimensions.
	 * 
	 * @return Un entier (1 ou 0) qui représente si le mouvement est légal. On
	 *         aurait pu retourner un booléen mais pour les autres méthodes qui
	 *         suivent, 1 et 0 seront utiles.
	 * 
	 * @see Couple
	 * @see PawnInteractions#evaluateFighting()
	 */

	public Integer stayInBoard(int initialRow, int initialCol, Couple coord) {

		// Permet de vérifier si le mouvement est dans grid et si le carré est
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
	 * Méthode permettant de vérifier si des coordonnées représentées par les
	 * paramètres (row, column) sont dans la matrice carrée 10x10 (Grid).
	 * 
	 * @param row    Représente la rangée.
	 * @param column Représente la colonne.
	 * 
	 * @return Un booléen qui vérifie la condition énoncée ci-dessus.
	 * 
	 * @see Grid
	 */

	public boolean stayInBoard(int row, int column) {

		return (((row >= 0) && (row < 10)) && ((column >= 0) && (column < 10)));
	}

	public ArrayList<Couple> availableMovementTest() {
		int[] counter = { 0, 0, 0, 0 };
		ArrayList<Couple> evaluation = new ArrayList<Couple>();

		// Vérifie les mouvements
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
	 * Méthode permettant de créer une liste dynamique représentant les différents
	 * mouvements légaux qu'un pion (à partir d'une instance Square) peut effectuer
	 * 
	 * @return Une ArrayList permettant de représenter si le pion peut aller (haut,
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
	 * Même chose que availableMovement() mais pour l'éclaireur car sa portée est
	 * plus grande.
	 * 
	 * @see PawnInteractions#availableMovement()
	 */

	public ArrayList<Integer> availableMovementScout() {
		int initialSquareX = getX();
		int initialSquareY = getY();

		// Initialisation de range, permet de donner la portée du pion dans les 4
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

}
