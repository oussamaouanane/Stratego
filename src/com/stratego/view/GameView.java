package be.ac.umons.stratego.view;

import java.util.ArrayList;

import be.ac.umons.stratego.controller.GridController;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.state.GameState;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameView {

	private SquareView[][] square = new SquareView[10][10];
	private Pane grid = new Pane();
	private Pane setup = new Pane();
	private Pane inGame = new Pane();
	private GameProcess game;
	private GridController gridController;

	private PawnView pawnChosenSettingUp;
	private PawnView pawnChosen;

	private int[] PAWNS_COMPOSITION = { 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 1 };

	public GameView(int ai) {

		Stage primaryStage = new Stage();
		// Scene
		Scene scene = new Scene(inGame, 1000, 600);
		// Titre
		primaryStage.setTitle("Stratego");
		// Réglage de Scene
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		scene.getStylesheets().add("file:assets/styles/menu.css");
		// Afficher Scene
		primaryStage.show();
		primaryStage.setResizable(false);
		// Permet de créer tous les éléments graphiques
		draw();
		// Permet de créer la grille
		createGrid();
		setupPawns();
		// Permet de créer une instant de GameProcess
		game = new GameProcess(ai);

		gridController = new GridController(game.getGrid());

	}

	public void draw() {
		inGame.getChildren().add(grid);

		grid.setPrefHeight(600);
		grid.setPrefWidth(600);

		// Image de fond
		Image background = new Image("file:assets/maps/map_main.png");
		ImageView backgroundView = new ImageView(background);

		backgroundView.setFitHeight(610.0);
		backgroundView.setFitWidth(610.0);
		backgroundView.setPreserveRatio(true);
		grid.getChildren().add(backgroundView);

		// Création de la grille
		createGrid();

		// Bouton: Sauvegarder la partie
		Button save = new Button("Charger une sauvegarde");

		save.setTextFill(Color.WHITE);
		save.setTranslateX(640.0);
		save.setTranslateY(540.0);
		save.setPrefHeight(40.0);
		save.setPrefWidth(160.0);

		save.setId("loadButton");
		inGame.getChildren().add(save);

		// Bouton: Quitter la partie
		Button leave = new Button("Quitter le jeu");

		leave.setTextFill(Color.WHITE);
		leave.setTranslateX(820.0);
		leave.setTranslateY(540.0);
		leave.setPrefHeight(40.0);
		leave.setPrefWidth(160.0);
		leave.setOnAction(e -> Platform.exit());

		leave.setId("leaveButton");

		inGame.getChildren().add(leave);

	}

	private void setupPawns() {

		setup.setPrefSize(400, 610);
		setup.setTranslateX(900 - 290);

		// Implémenter si tous les pions = 0 alors, changer état
		ArrayList<ImageView> listOfPawns = null;

		setup.getChildren().clear();
		grid.getChildren().remove(setup);
		grid.getChildren().add(setup);

		Label title = new Label("Placement des pions");
		title.setTranslateX(55);
		title.setTranslateY(25);
		title.setFont(new Font("Lucida Console", 24.0));
		title.setTextFill(Color.web("#DDDDDD"));

		setup.getChildren().add(title);

		int counter = 0;
		int x = 65;
		int y = 100;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {

				if (PAWNS_COMPOSITION[counter] > 0) {

					ImageView pawn = createPawnForSettingUp(counter, 1);
					pawn.setX(x);
					pawn.setY(y);

					Label nbr = new Label(Integer.toString(PAWNS_COMPOSITION[counter]));
					nbr.setTranslateX(x + 25);
					nbr.setTranslateY(y + 70);
					setup.getChildren().add(nbr);

				}

				counter++;

				x += 100;
			}
			y += 100;
			x -= 300;

		}
	}

	private void createGrid() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				square[i][j] = new SquareView(61 * j, 61 * i, 61, 61);
				square[i][j].setId("square");
				square[i][j].setRow(9 - i);
				square[i][j].setColumn(j);

				square[i][j].setOnMousePressed(e -> SquareEvent(e));

				grid.getChildren().add(square[i][j]);

			}
		}
	}

	private PawnView createPawn(int rank, int player) {

		// Création du PawnView
		PawnView pawn = new PawnView(rank, player);
		// @see #PawnEvent
		pawn.setOnMousePressed(e -> PawnEvent(e));
		// Permet de gérer la transparence
		pawn.setPickOnBounds(true);
		inGame.getChildren().add(pawn);
		return pawn;
	}

	private ImageView createPawnForSettingUp(int rank, int player) {

		PawnView pawn = new PawnView(rank, player);
		// @see #PawnEventSettingUp
		pawn.setOnMousePressed(e -> PawnEventSettingUp(e));
		// Permet de gérer la transparence
		pawn.setPickOnBounds(true);
		setup.getChildren().add(pawn);

		return pawn;

	}

	private void PawnEventSettingUp(MouseEvent e) {

		PawnView source = (PawnView) e.getSource();

		if (PAWNS_COMPOSITION[source.getRank()] != 0)
			pawnChosenSettingUp = source;

	}

	public void movePawnSettingUp(SquareView sq, PawnView pawnToCreate) {

		// Création d'un pion
		PawnView pawn = createPawn(pawnChosenSettingUp.getRank(), pawnChosenSettingUp.getPlayer());
		// Permet d'associer les coordonnées aux pions

		pawn.setX(sq.getX());
		pawn.setY(sq.getY());
		// Permet d'associer au pion l'instance SquareView, Square et vice-versa.
		pawn.setSquare(sq);
		sq.setPawnView(pawn);
		// getSquare(sq).setPawn(new Pawn(pawn.getRank(), pawn.getPlayer()));

		// Permet de mettre à jour la liste des pions
		PAWNS_COMPOSITION[pawn.getRank()]--;
		setupPawns();

		if (PAWNS_COMPOSITION[pawn.getRank()] == 0) {
			pawnChosenSettingUp = null;
		}
	}

	public void switchPawnSettingUp(PawnView source) {

		// Permet d'incrémenter de un le compteur du pion
		PAWNS_COMPOSITION[source.getRank()]++;
		// Récupération du carré du pion
		SquareView square = source.getSquare();
		// Création du pion
		PawnView pawn = createPawn(pawnChosenSettingUp.getRank(), pawnChosenSettingUp.getPlayer());
		// Coordonnées du pion
		pawn.setX(square.getX());
		pawn.setY(square.getY());
		pawn.setSquare(square);

		inGame.getChildren().remove(source);
		PAWNS_COMPOSITION[pawnChosenSettingUp.getRank()]--;

		setupPawns();
		if (PAWNS_COMPOSITION[source.getRank()] == 0) {
			pawnChosenSettingUp = null;
		}
	}

	/**
	 * Méthode permettant de récupérer le pion et de le stocker dans une variable.
	 */

	private void PawnEvent(MouseEvent e) {

		// Permet de récupérer les pions
		PawnView source = (PawnView) e.getSource();

		if (!checkSettingUpFinish()) {

			// Dans le cas où le placement des pions est toujours en cours.
			if (pawnChosenSettingUp != null) {
				switchPawnSettingUp(source);

			}
		}

	}

	public boolean checkSettingUpFinish() {

		int var = 0;

		for (int i : PAWNS_COMPOSITION) {
			if (i > 0)
				var++;
		}

		if (var > 0)
			return false;
		else
			return true;
	}

	/**
	 * Méthode permettant de déplacer le pion stocké dans l'instance SquareView
	 * sélectionnée.
	 */

	public void SquareEvent(MouseEvent e) {

		// Récupère la source du pion
		SquareView sq = (SquareView) e.getSource();

		if (pawnChosenSettingUp != null)
			// Dans le cas où le placement des pions est toujours en cours.
			if (!checkSettingUpFinish() && (sq.getRow() < 4)) {
				movePawnSettingUp(sq, pawnChosenSettingUp);

			}

			else {

			}

	}

	public GameState getState() {
		return game.getGameStateManager().getState();
	}

	public SquareView getSquareView(int row, int column) {
		return square[row][column];
	}

	public Square getSquare(SquareView square) {

		return game.getGrid().getSquare(square.getRow(), square.getColumn());
	}

}
