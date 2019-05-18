package be.ac.umons.stratego.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe permettant de representer de maniere graphique Pawn, elle herite des
 * Composants de sa classe mere ImageView.
 * 
 * @see Pawn
 * @see ImageView
 * 
 */

public class PawnView extends ImageView {

	// Caracteristiques du pion, à la maniere de Pawn
	private int rank;
	private int player;
	private boolean visible;
	private SquareView square;
	// true pour vivant, false pour mort.
	private boolean state;

	// Liste de tous les pions
	private final static String[] rankString = { "Espion", "Eclaireur", "Demineur", "Sergent", "Lieutenant",
			"Capitaine", "Commandant", "Colonel", "General", "Marechal", "Bombe", "Drapeau" };

	/**
	 * Constructeur permettant de representer un pion
	 * @param rank rang du pion
	 * @param player joueur à qui il appartient
	 * 
	 * @see Player
	 */
	public PawnView(int rank, int player) {

		super(createImage(rank, player));
		// Variables d'instances
		this.rank = rank;
		this.player = player;

		if (player == 2)
			visible = false;
		else
			visible = true;

	}

	public static Image createImage(int rank, int player) {

		String path = getPathName(rank, player);
		return new Image(path);
	}

	public static String getPathName(int rank, int player) {

		if (player == 1)
			return "file:assets/sprites/" + rankString[rank] + "_J1.png";
		else
			return "file:assets/sprites/Hidden_J2.png";
	}
	
	public void setVisible() {
		this.setImage(new Image("file:assets/sprites/" + rankString[rank] + "_J2.png"));
	}
	
	public void setHidden() {
		this.setImage(new Image("file:assets/sprites/Hidden_J2.png"));
	}

	// Quelques accesseurs (getters) et mutateurs (setters)

	public int getPlayer() {
		return player;
	}

	public int getRank() {
		return rank;
	}

	public SquareView getSquare() {
		return square;
	}

	public void setSquare(SquareView square) {
		this.square = square;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisiblePawn(boolean visible) {
		this.visible = visible;
	}

	public boolean isAlive() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
