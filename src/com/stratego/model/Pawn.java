package com.stratego.model;

/**
 * <h1>Pawn</h1> 
 * 
 * <p>Classe qui permet de d�finir un objet qui repr�sente avec un
 * pion. Un pion poss�de plusieurs attributs qui permettent de le d�finir: - Un
 * ID unique 0 <= ID <= 80 - un rang <i>c.f arrayRanks</i> - un joueur - un
 * �tat, vivant ou mort - une visibilit� - une port�e.</p>
 * 
 * @author O.S
 */

public class Pawn {

	private int id, rank, player, range = 1;
	private boolean state;
	private boolean visible;
	private String[] arrayRanks = { "Spy", "Scouts", "Miners", "Sergents", "Lieutenants", "Captains", "Commanders",
			"Colonels", "General", "Marshal", "Bombs", "Flag" };

	/**
	 * Constructeur par d�faut qui d�finit un id, un rang et un joueur au pion
	 * 
	 * @param id     ID unique
	 * @param rank   rang
	 * @param player Possesseur du pion (1 ou 2)
	 */

	public Pawn(int id, int rank, int player) {

		this.setId(id);
		this.rank = rank;
		this.player = player;

		// D�faut
		this.setState(false);
		this.visible = false;

		// Change la port�e si le pion est un �claireur (9), drapeau ou bombes (0)
		if (rank >= 10)
			this.range = 0;
		else if (rank == 1)
			this.range = 9;
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
}
