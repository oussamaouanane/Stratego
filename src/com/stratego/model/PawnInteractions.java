package com.stratego.model;

/**
 * PawnInteractions --- Win: Greater rank. The spy is stronger than Marshal.
 * Miner is stronger than Bombs. Lose: Lower rank. Miner and Spy rules Draw:
 * Same ranks.
 * 
 * @author O.S
 */

public class PawnInteractions extends Couple {

	public PawnInteractions(Square squareA, Square squareB) {
		super(squareA, squareB);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param couple Couple of (pawnA, pawnB)
	 * @return 1 pawnA > pawnB Stronger
	 * @return 0 pawnA == pawnB Draw
	 * @return -1 pawnA < pawnB Lose
	 */
	public byte evaluateFighting() {

		if ((getSquareA().getPawn().getRank() == 0 && getSquareB().getPawn().getRank() == 9) // Spy stronger than Marshal
				|| (getSquareA().getPawn().getRank() == 2 && getSquareB().getPawn().getRank() == 10) // Miner strong than bomb
				|| (getSquareA().getPawn().getRank() == 11) // pawnB is a Flag
				|| getSquareB().getPawn().getRank() > getSquareB().getPawn().getRank()) // PawnA > PawnB
			return 1;
		else if (getSquareB().getPawn().getRank() == getSquareB().getPawn().getRank()) // PawnA == PawnB
			return 0;
		else // PawnA < PawnB
			return -1;
	}

	public void doFighting() {

		byte evaluation = evaluateFighting();

		switch (evaluation) {
		case 1:
			// pawnA > PawnB
			getSquareB().setPawn(getPawnA());
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

}
