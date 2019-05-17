package be.ac.umons.stratego.model.pawn;

import java.io.Serializable;

import be.ac.umons.stratego.model.grid.Square;

/**
 * <h1>Pawn</h1>
 * 
 * <p>
 * Classe permettant de mod�liser un pion. Un pion poss�de plusieurs attributs
 * qui permettent de le d�finir: - Un ID unique 0 <= ID <= 80 (Pas s�r de
 * l'impl�menter) - un rang <i>c.f arrayRanks</i> - un joueur - un �tat, vivant
 * ou mort - une visibilit� - une port�e.
 * </p>
 * 
 * @author O.S
 */

public class Pawn implements Serializable {

	private static final long serialVersionUID = -3502069392978745637L;
	private int rank, player, range = 1;
	private Square square;

	private static final String[] rankString = { "Espion", "Eclaireur", "Demineur", "Sergent", "Lieutenant", "Capitaine",
			"Commandant", "Colonel", "General", "Marechal", "Bombe", "Drapeau" };
	public static final int[] PAWNS_COMPOSITION = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5,
			5, 6, 6, 6, 7, 7, 8, 9, 10, 10, 10, 10, 10, 10, 11 };

	/**
	 * Constructeur par d�faut permettant de d�finir une instance de Pawn qui est
	 * compos�e d'un id, un rang et un joueur au pion
	 * 
	 * @param id     ID unique
	 * @param rank   rang
	 * @param player Possesseur du pion (1 ou 2)
	 */

	public Pawn(int rank, int player) {

		this.rank = rank;
		this.player = player;

		// Change la port�e si le pion est un �claireur (9), drapeau ou bombes (0)
		if (rank >= 10)
			this.range = 0;
		else if (rank == 1)
			this.range = 9;

	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	/**
	 * Quelques accesseurs (getters) et mutateurs (setters), lire la pr�sentation de
	 * la classe pour en savoir plus sur les variables d'instances.
	 */
	public int getPlayer() {
		return player;
	}

	public int getRange() {
		return range;
	}

	public int getRank() {
		return rank;
	}

	public String getRankName() {
		return rankString[rank];
	}

	/**
	 * M�thode permettant de v�rifier si un pion est d'un rang sp�cifique. On
	 * pourrait utiliser une condition bool�en uniquement � l'aide d'une condition
	 * de ce type: SI pion.getRank() == rank ALORS mais c'est plus concis de cette
	 * mani�re.
	 */
	public boolean isPawnA(int rank) {
		return this.rank == rank;
	}
}
