package com.stratego.view;

import java.util.ArrayList;

import com.stratego.controller.GridController;
import com.stratego.model.Couple;
import com.stratego.model.GameProcess;
import com.stratego.model.grid.Square;
import com.stratego.model.state.GameState;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game {

	private SquareView[][] square = new SquareView[10][10];
	private Pane inGame = new Pane();
	private Pane grid = new Pane();
	private GameProcess game;
	private GridController gridController;

	private ImageView pawnChosen;
	private int pawnChosenRank;
	private int pawnChosenRow;
	private int pawnChoseColumn;

	int lol = 0;

	private int[] PAWNS_COMPOSITION = { 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 0 };

	public Game(int ai) {

		Stage primaryStage = new Stage();
		// Scene
		Scene scene = new Scene(inGame, 1000, 600);
		// Titre
		primaryStage.setTitle("Stratego");
		// Réglage de Scene
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		scene.getStylesheets().add("/com/stratego/assets/styles/menu.css");
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
		Image background = new Image("/com/stratego/assets/maps/map_main.png");
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

		// for (int i = 0; i < 12; i++) {
		// int nb = PAWNS_COMPOSITION[i];
		// for (int j = 0; j < nb; j++)
		// createPawn(i);

		// }
		int cnt = 0;
		int y = 100;

		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= 2; j++) {
				int nb = PAWNS_COMPOSITION[cnt];
				int nb1 = PAWNS_COMPOSITION[cnt + 1];

				for (int t = 0; t < nb; t++) {
					ImageView icon = createPawn(nb, 1);
					icon.setX(700);
					icon.setY(y);
				}

				for (int z = 0; z < nb1; z++) {
					ImageView icon = createPawn(nb1, 1);
					icon.setX(850);
					icon.setY(y);
				}

			}
			cnt += 2;
			y += 100;

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

	private ImageView createPawn(int rank, int player) {

		PawnView pawn = new PawnView(rank, player);

		pawn.getIcon().setOnMousePressed(e -> PawnEvent(e));
		inGame.getChildren().add(pawn.getIcon());

		return pawn.getIcon();

	}

	private void PawnEventSettingUp(MouseEvent e) {

		pawnChosen = (ImageView) e.getSource();
		pawnChosenRank = (Integer.parseInt(pawnChosen.getId()));

	}

	/**
	 * Méthode permettant de récupérer le pion et de le stocker dans une variable.
	 */

	private void PawnEvent(MouseEvent e) {

		// Permet de récupérer les pions
		ImageView source = (ImageView) e.getSource();
		String[] splitId = source.getId().split(",");

		// Si le pion n'est pas mort ou n'appartient pas au joueur 2 ou si le tour n'est
		// pas au joueur 1
		if (!((source.getId() == "mort") && (splitId[1] == "2") && (game.getTurn() != 1))) {
			pawnChosen = source;
			
		}

		// Si tour = 1 alors on met que pawnChosen = (...) e.getSource();
		if ((game.getTurn() == 1)) {

			String pawnProp[] = pawnChosen.getId().split(",");
			// pawnChosenRow = Integer.parseInt(pawnProp[1]);
			// pawnChoseColumn = Integer.parseInt(pawnProp[2]);

			System.out.println(pawnChosenRow);

			// ArrayList<Couple> highlight = gridController
			// .highlightAvailableMove(getSquare(getSquareView(pawnChosenRow,
			// pawnChoseColumn)));
			// for (Couple c : highlight)
			// square[pawnChosenRow + c.getX()][pawnChoseColumn +
			// c.getY()].setId("highlight");
		}
	}

	/**
	 * Méthode permettant de déplacer le pion stocké dans l'instance SquareView
	 * sélectionnée.
	 */

	public void SquareEvent(MouseEvent e) {

		SquareView sq = (SquareView) e.getSource();

		Square initialSquare = getSquare(getSquareView(pawnChosenRow, pawnChoseColumn));
		Square destinationSquare = getSquare(getSquareView(sq.getRow(), sq.getColumn()));

		if (pawnChosen != null) {
			if (getState() == GameState.SETTINGUPSTATE) {
				sq.setPawnView(pawnChosen);
				pawnChosen.setId(pawnChosen.getId() + "," + sq.getRow() + "," + sq.getColumn());
				pawnChosen.setX(sq.getX());
				pawnChosen.setY(sq.getY());
				game.getGameStateManager().setState(GameState.ENDGAMESTATE);
				System.out.println(pawnChosen.getId());
			} else if (gridController.isMovePossible(initialSquare, destinationSquare)) {
				gridController.movePawn(initialSquare, destinationSquare);
				sq.setPawnView(pawnChosen);
				pawnChosen.setId(pawnChosen.getId() + "," + sq.getRow() + "," + sq.getColumn());
				pawnChosen.setX(sq.getX());
				pawnChosen.setY(sq.getY());

			}

			pawnChosenRank = pawnChoseColumn = pawnChosenRank = 0;
			pawnChosen = null;
		}
		// // Vérifie s'il y a un pion
		// if (pawnChosenView != null) {
		// // Vérifie si Square est accessible
		// if (getSquare(sq).getAccess()) {
		// // Fixe les coordonnées du pion
		// pawnChosenView.setX(sq.getX());
		// pawnChosenView.setY(sq.getY());
		// //sq.setPawnView(pawnChosen);
		// new Pawn(pawnRank, pawnPlayer);
		// game.getGrid().getSquare(sq.getRow(), sq.getColumn());
		// // Réinitialisation du pion
		// pawnPlayer = pawnRank = 0;
		// pawnChosenView = null;
		//
		// // Associe le pion au carré
		//
		//
	}
	// }
	// }

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
