package com.stratego.view;

import com.stratego.model.pawn.Pawn;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PawnView extends Pawn {

	Image icon;
	ImageView iconView;

	public PawnView(int id, int rank, int player) {
		super(id, rank, player);
		createIcon();
	}

	public void createIcon() {

		if (getPlayer() == 2) {
			if (!getVisible())
				icon = new Image("/com/Stratego/assets/sprite/Hidden_J2.png");
			else
				icon = new Image("/com/Stratego/assets/sprite/" + getRankName() + "_J2.png");
		}

		else
			icon = new Image("/com/Stratego/assets/sprite/" + getRankName() + "_J1.png");
		iconView = new ImageView(icon);
		
	}
}
