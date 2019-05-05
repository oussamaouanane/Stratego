package be.ac.umons.stratego.model.pawn;

import be.ac.umons.stratego.model.grid.Square;

/**
 * <h1>Pawn</h1>
 * 
 * <p>
 * Classe permettant de modéliser un pion. Un pion possède plusieurs attributs
 * qui permettent de le définir: - Un ID unique 0 <= ID <= 80 (Pas sûr de
 * l'implémenter) - un rang <i>c.f arrayRanks</i> - un joueur - un état, vivant
 * ou mort - une visibilité - une portée.
 * </p>
 * 
 * @author O.S
 */

public class Pawn {

	private int rank, player, range = 1;
	private boolean state;
	private boolean visible;
	private Square square;

	private final String[] arrayRanks = { "Espion", "Eclaireur", "Demineur", "Sergent", "Lieutenant", "Capitaine",
			"Commandant", "Colonel", "General", "Marechal", "Bombe", "Drapeau" };
	public static int[] PAWNS_COMPOSITION = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5,
			5, 6, 6, 6, 7, 7, 8, 9, 10, 11 };

	/**
	 * Constructeur par défaut permettant de définir une instance de Pawn qui est
	 * composée d'un id, un rang et un joueur au pion
	 * 
	 * @param id     ID unique
	 * @param rank   rang
	 * @param player Possesseur du pion (1 ou 2)
	 */

	public Pawn(int rank, int player) {

		this.rank = rank;
		this.player = player;

		// Défaut
		this.setState(false);
		this.visible = false;

		// Change la portée si le pion est un éclaireur (9), drapeau ou bombes (0)
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
	 * Quelques accesseurs (getters) et mutateurs (setters), lire la présentation de
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
		return arrayRanks[rank];
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * Méthode permettant de vérifier si un pion est d'un rang spécifique. On
	 * pourrait utiliser une condition booléen uniquement à l'aide d'une condition
	 * de ce type: SI pion.getRank() == rank ALORS mais c'est plus concis de cette
	 * manière.
	 */
	public boolean isPawnA(int rank) {
		return this.rank == rank;
	}
}
