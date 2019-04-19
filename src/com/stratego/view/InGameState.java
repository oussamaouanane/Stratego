package com.stratego.view;

import com.stratego.model.GameProcess;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class InGameState {
	
	 private ImageView pawnChosen;
	 private SquareView[][] square = new SquareView[10][10];
	 private Pane inGame = new Pane();
	 private Pane grid = new Pane();
	 GameProcess game;
	 
	 private final String[] arrayRanks = { "Espion", "Eclaireur", "Demineur", "Sergent", "Lieutenant", "Capitaine", "Commandant",
				"Colonel", "General", "Marechal", "Bombe", "Drapeau" };
	 private int[] PAWNS_COMPOSITION = { 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 0 };
	 
	 public InGameState(int ai) {
		 		 	
		 	Stage primaryStage = new Stage();
		 	// Scene
			Scene scene = new Scene(inGame, 1000, 600);
			// Titre
			primaryStage.setTitle("Stratego - Main Menu");
			// Réglage de Scene
			primaryStage.setScene(scene);
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
		
		SplitPane setup = new SplitPane();
		
		for (int i = 0; i < 12; i++) {
			int nb = PAWNS_COMPOSITION[i];
			for (int j = 0; j < nb; j++)
				createPawn(i);
			
		}
		
		setup.setPrefSize(400, 510);
		setup.setTranslateX(610);
		grid.getChildren().add(setup);


	}
	
	private void createGrid() {
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				
				square[i][j] = new SquareView(61*j, 61*i, 61, 61);
				square[i][j].setId("square");
				square[i][j].setRow(9-i);
				square[i][j].setColumn(j);
				
				square[i][j].setOnMousePressed(e -> SquareEvent(e));
				
				grid.getChildren().add(square[i][j]);

			}
		}		
	}
	
	private void createPawn(int rank) {
		
		Image pawnTest = new Image("com/stratego/assets/sprites/" + arrayRanks[rank] + "_J1.png");
		ImageView pawn = new ImageView(pawnTest);

		switch (rank) {
		case 0:
		}
		
		pawn.setOnMousePressed(e -> PawnEvent(e));
		inGame.getChildren().add(pawn);
		
	}
	
	private void PawnEvent(MouseEvent e) {
		
		pawnChosen = (ImageView) e.getSource();
		System.out.println("mdr");

	}

	public void SquareEvent(MouseEvent e) {
		SquareView sq = (SquareView) e.getSource();
		int sqRow = sq.getRow();
		int sqColumn = sq.getColumn();
		
		System.out.println(game.getGrid().getTest());
		
		if (game.getGrid().getSquare(sqRow, sqColumn).getAccess()) {
			pawnChosen.setX(sq.getX());
			pawnChosen.setY(sq.getY());
			pawnChosen = null;
		}
		
	}

	
}
