package com.stratego.view;

import com.stratego.model.Couple;
import com.stratego.model.GameProcess;
import com.stratego.model.player.Player;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InGameState {
	
	 private SquareView[][] square = new SquareView[10][10];
	 private Pane inGame = new Pane();
	 private Pane grid = new Pane();
	 
	 public InGameState(int ai) {
		 	
		 	Stage primaryStage = new Stage();
		 	// Scene
			Scene scene = new Scene(inGame, 1000, 600);
			// Titre
			primaryStage.setTitle("Stratego - Main Menu");
			// R�glage de Scene
			primaryStage.setScene(scene);
			scene.getStylesheets().add("/com/stratego/assets/styles/menu.css");
			// Afficher Scene
			primaryStage.show();
			
			primaryStage.setResizable(false);
			
			// Permet de cr�er tous les �l�ments graphiques
			draw();
			
			// Permet de cr�er la grille
			createGrid();
			
			// Permet de cr�er une instant de GameProcess
			GameProcess game = new GameProcess(ai);

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
		
		// Cr�ation de la grille
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
		leave.setTranslateX(810.0);
		leave.setTranslateY(540.0);
		leave.setPrefHeight(40.0);
		leave.setPrefWidth(160.0);

		leave.setId("leaveButton");
	
		inGame.getChildren().add(leave);
	}
	
	private void createGrid() {
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				
				square[i][j] = new SquareView(61*j, 61*i, 61, 61);
				square[i][j].setId("square");
				square[i][j].setRow(9-i);
				square[i][j].setColumn(j);
				
				square[i][j].setOnMousePressed(e -> getPawn(e));
				square[i][j].setOnMouseDragReleased(e -> getFinal(e));
				
				grid.getChildren().add(square[i][j]);

			}
		}		
	}
	
	private void getFinal(MouseDragEvent e) {
		SquareView sq = (SquareView) e.getSource();
		Couple finalCoord = new Couple(sq.getRow(), sq.getColumn());
		System.out.println("La rang�e finale est: " + sq.getRow() + " et la colonne finale est: " + sq.getColumn());
	}

	public void getPawn(MouseEvent e) {
		SquareView sq = (SquareView) e.getSource();
		System.out.println("La rang�e est: " + sq.getRow() + " et la colonne est: " + sq.getColumn());
	}

	
}
