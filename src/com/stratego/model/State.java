package com.stratego.model;

/**
 * @author O.S
 *
 */
public class State {

	private byte[] PAWNS_COMPOSITION = { 0, 2, 3, 4, 5 };

	/**
	 * pawnPlacement Place the pawns at the beginning of the game. Numbers of pawns
	 * of each rank to place:
	 * 
	 * PANWS_COMPOSITION[rank]
	 */

	public void pawnPlacement() {
		for (int i = 0; i <= 40; i++)
			// TO-DO
			break;
	}

	/**
	 * checkWin Check if the game is over Conditions :
	 * 
	 * @param player Pawn object's owner - 1 for Player I or 2 for Player II.
	 *
	 */
	public boolean checkWin(byte player) {

		return true;
	}

}
