package com.stratego.model;

/**
 * Pawn --- Class that defines methods and attributes for a Pawn Object - Will
 * help simplify the task in the code.
 * 
 * @author O.S
 */

public class Pawn {

	private byte id, rank, player, rangeOfPawn = 1;
	private boolean state;
	private boolean visible;
	private String[] arrayRanks = { "Spy", "Scouts", "Miners", "Sergents", "Lieutenants", "Captains", "Commanders",
			"Colonels", "General", "Marshal", "Bombs", "Flag" };

	/**
	 * Default constructor that takes three parameters -- and sets the id, rank,
	 * player of the Pawn object.
	 * 
	 * @param id     Unique ID given to the Pawn object.
	 * @param rank   Rank of the Pawn object, goes from 0 to 11 (and can be
	 *               translated into String with arrayRanks[rank]).
	 * @param player Pawn object's owner - 1 for Player I or 2 for Player II.
	 */

	public Pawn(byte id, byte rank, byte player) {

		this.setId(id);
		this.rank = rank;
		this.player = player;

		// Default
		this.setState(false);
		this.visible = false;

		// Change the range of the Pawn object for the: Bombs, Flag and the Scout.
		if (rank >= 10)
			this.rangeOfPawn = 0;
		else if (rank == 1)
			this.rangeOfPawn = 9;
	}

	public Pawn(byte rank) {
		this.rank = rank;
	}

	// @return player A byte that represents 1 for Player I or 2 for Player II
	public byte getPlayer() {
		return player;
	}

	// @return rangeOfPawn A byte that represents 1, 2 or 9 (check constructor).
	public byte getRangeOfPawn() {
		return rangeOfPawn;
	}

	// @return rank A byte that represents, goes from 0 to 11.
	public byte getRank() {
		return rank;
	}

	// @return arrayRanks[rank] A String that represents the rank name.
	public String getRankName() {
		return arrayRanks[rank];
	}

	// @return visible A boolean that represents whenever it is visible or not
	public boolean getVisible() {
		return visible;
	}

	// Set Pawn Object as visible or not.
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	// @return state A boolean that says whether it is alive or not.
	public boolean getState() {
		return state;
	}

	// Set Pawn Object as alive or not.
	public void setState(boolean state) {
		this.state = state;
	}

	// @return id A unique byte that represents the Pawn Object.
	public byte getId() {
		return id;
	}

	// Set a unique byte that represents the Pawn Object.
	public void setId(byte id) {
		this.id = id;
	}
}
