package com.stratego.model;

/*
* Pawn --- Class that defines methods and attributes for a Pawn Object - Will simplify the task in the code.
* @author 
*/

public abstract class Pawn {

	private byte id, rank, player, rangeOfPawn = 1;
	private boolean state;
	private boolean visible;
	private String [] arrayRanks = {"Spy", "Scouts", "Miners", "Sergents", "Lieutenants", "Captains", "Commanders", "Colonels", "General", "Mareshal"};



/*
* @param id Unique ID given to a Pawn
* @param rank Rank of the Pawn, goes from 0 to 11 and can be translated into String with arrayRanks[rank]
* @param player Pawn's owner - 1 for Player I or 2 for Player II
* @param state Define if the Pawn is known or not by the opponent
* @param visible Define if Pawn is visible or not by the opponent
*/

	public Pawn(byte id, byte rank, byte player) {
    /*
    * Default constructor that takes three parameters -- and sets the id, rank, player of the Pawn.
    * @param id Unique ID given to a Pawn
    * @param rank Rank of the Pawn, goes from 0 to 11 and can be translated into String with arrayRanks[rank]
    * @param player Pawn's owner - 1 for Player I or 2 for Player II.
    */

    this.id = id;
    this.rank = rank;
    this.player = player;

    // Default
    this.state = false;
    this.visible = false;

    // Change the range of the pawn for the: Bombs, Flag and the Scout.
    if (rank >= 10)
        this.rangeOfPawn = 0;
    else if(rank == 1)
        this.rangeOfPawn = 9;
    //else rangeOfPawn will stay default, 1.
	}

//Some accessors
	public byte getPlayer() {
		return player;
	}

	public byte getRangeOfPawn() {
		return rangeOfPawn;
	}

	public byte getRank() {
		return rank;
	}

	public String getRankName() {
		return arrayRanks[rank];
	}
}




