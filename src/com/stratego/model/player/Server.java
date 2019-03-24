package com.stratego.model.player;

import java.util.ArrayList;

import com.stratego.model.pawn.Pawn;

public class Server implements Player {

	private boolean hasFlag;
	private boolean flagSurrounded;
	private boolean hasMinersLeft;
	
	private ArrayList<Pawn> pawnsArray;
	Client clientPlayer;
	Pawn pawn;
	
	public Server() {
		pawnInitialization();
		hasFlag = flagSurrounded = hasMinersLeft = true;
	}
	
	@Override
	public boolean hasFlag() {
		for (Pawn pawn: pawnsArray) {
			if (pawn.isPawnA(11))
				hasFlag = true;
		}
		return hasFlag;
	}
	
	@Override
	public boolean hasFlagSurrounded() {
		// TODO Auto-generated method stub
		return flagSurrounded;
	}
	
	@Override
	public boolean hasMinersLeft() {
		for (Pawn pawn: pawnsArray) {
			if (pawn.isPawnA(3))
				hasMinersLeft = true;
		}
		return hasMinersLeft;
	}
	
	@Override
	public boolean hasMovesLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pawnInitialization() {
		//Création des pions de l'utilisateur Server
		for (int i = 40; i <= 80; i++) {
			for (int c: Pawn.PAWNS_COMPOSITION)
			pawnsArray.add(new Pawn(i, c, 1));
		}
		
	}

	public boolean checkWin() {

		// Le joueur Client perdant n'a plus de drapeau
		if (clientPlayer.hasFlag() == false
		// Le joueur Server a encerclé son drapeau et Client n'a plus de démineur.
		   || ((this.flagSurrounded) && (!clientPlayer.hasMinersLeft()))
		   || (!clientPlayer.hasMovesLeft()))
			return true;                                     
		else                                                 
			return false;                                    
	}                                                        
	
}
