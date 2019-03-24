package com.stratego.model.player;

/**
 * <h1>Player</h1>
 * 
 * <p>
 * Interface permettant de cr�er diverses m�thodes qui serviront � v�rifier
 * l'�tat du jeu lors de chaque changement de r�les.
 * </p>
 * 
 * @see Client
 * @see Server
 * 
 * @author O.S
 */
public interface Player {
	
	public boolean hasFlag();
	public boolean hasFlagSurrounded();
	
	public boolean hasMinersLeft();
	public boolean hasMovesLeft();
	
	public void pawnInitialization();

}
