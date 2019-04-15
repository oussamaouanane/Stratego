package com.stratego.model.pawn;

import com.stratego.model.grid.Grid;
import com.stratego.model.grid.Square;

/**
 * <h1>Pawn</h1>
 * 
 * <p>
 * Classe permettant de modéliser un pion. Un pion possède plusieurs attributs
 * qui permettent de le définir: - Un ID unique 0 <= ID <= 80 - un rang <i>c.f
 * arrayRanks</i> - un joueur - un état, vivant ou mort - une visibilité - une
 * portée.
 * </p>
 * 
 * @author O.S
 */

public class Pawn {

	private int id, rank, player, range = 1;
	private boolean state;
	private boolean visible;
	private String[] arrayRanks = { "Espion", "Eclaireur", "Demineur", "Sergent", "Lieutenant", "Capitaine", "Commandant",
			"Colonel", "General", "Marechal", "Bombe", "Drapeau" };
	private Square square;
	private String icon;

	public final static int[] PAWNS_COMPOSITION = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5,
			5, 5, 5, 6, 6, 6, 7, 7, 8, 9, 10, 11 };

	/**
	 * Constructeur par défaut permettant de définir une instance de Pawn qui est
	 * composée d'un id, un rang et un joueur au pion
	 * 
	 * @param id     ID unique
	 * @param rank   rang
	 * @param player Possesseur du pion (1 ou 2)
	 */

	public Pawn(int id, int rank, int player) {

		this.setId(id);
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

	public Square getSquare(Grid grid) {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	/**
	 * Quelques accesseurs (getters) et mutateurs (setters)
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	/**
	 * Méthode permettant de vérifier si un pion est d'un rang spécifique.
	 */
	public boolean isPawnA(int rank) {
		return this.rank == rank;
	}
}
