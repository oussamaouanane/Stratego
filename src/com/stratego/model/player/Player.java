package com.stratego.model.player;

/**
 * <h1>Player</h1>
 * 
 * <p>
 * Interface permettant de créer diverses méthodes qui serviront à vérifier
 * l'état du jeu lors de chaque changement de rôles.
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
