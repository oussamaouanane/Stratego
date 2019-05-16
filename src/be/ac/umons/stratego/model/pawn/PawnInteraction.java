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
		boolean lastSquareOpponentPawn = false;

		// Dans ce cas on agit sur un pion de portée 1.
		if (getSquare(getX(), getY()).getPawn().getRange() == 1) {
			Couple[] possibleMovements = { new Couple(0, 1), new Couple(1, 0), new Couple(0, -1), new Couple(-1, 0) };
			for (Couple c : possibleMovements) {
				Square initialSquare = getSquare(getX(), getY());
				Square finalSquare = getSquare(getX() + c.getX(), getY() + c.getY());
				if (new PawnInteraction(initialSquare, finalSquare, grid).isMovePossible())
					evaluation.add(new Couple(getX() + c.getX(), getY() + c.getY()));

			}
		}
		// Dans ce cas on agit sur le pion éclaireur qui a une portée illimitée tant
		// qu'il ne saute pas de pion.
		else {

			boolean finished = false;
			boolean[] state = { false, false, false, false };

			Couple[] possibleMovements = { new Couple(0, 1, 0), new Couple(1, 0, 1), new Couple(0, -1, 2),
					new Couple(-1, 0, 3) };

			int cnt = 0;
			while (finished == false) {
				for (Couple c : possibleMovements) {
					Square initialSquare = getSquare(getX(), getY());
					Square finalSquare = getSquare(getX() + c.getX(), getY() + c.getY());
					if (new PawnInteraction(initialSquare, finalSquare, grid).isMovePossible()
							&& (lastSquareOpponentPawn == false)) {
						evaluation.add(c);
						int direction = possibleMovements[cnt].getDirection();

						switch (direction) {
						case 0:
							possibleMovements[cnt] = new Couple(possibleMovements[cnt].getX() + 1, 0);
							break;
						case 1:
							possibleMovements[cnt] = new Couple(0, possibleMovements[cnt].getY() + 1);
							break;
						case 2:
							possibleMovements[cnt] = new Couple(possibleMovements[cnt].getX() - 1, 0);
							break;
						case 3:
							possibleMovements[cnt] = new Couple(0, possibleMovements[cnt].getY() - 1);
							break;
						}

						if (finalSquare.getPawn() != null)
							state[cnt] = true;

					}

					else {
						state[cnt] = true;
						int i = 0;
						for (boolean b : state)
							if (b == true)
								i++;
						if (i == 4)
							finished = true;
					}
					cnt++;
				}
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

}
