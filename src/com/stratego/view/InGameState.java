package com.stratego.view;

import com.stratego.model.grid.Grid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InGameState extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Pane grid = new Pane();
		Button[][] square = new Button[10][10];
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				square[i][j] = new Button("Salut");
				square[i][j].setTranslateX(60);
				square[i][j].setTranslateY(60);
				square[i][j].setId("leaveButton");
				
				grid.getChildren().add(square[i][j]);
				
			}
		}

	    
		
		// Image de fond
		Image background = new Image("/com/stratego/assets/maps/map_main.png");
		ImageView backgroundView = new ImageView(background);

		backgroundView.setFitHeight(610.0);
		backgroundView.setFitWidth(610.0);
		backgroundView.setPreserveRatio(true);
		grid.getChildren().add(backgroundView);
		
		// Bouton: Sauvegarder la partie
		Button save = new Button("Charger une sauvegarde");

		save.setTextFill(Color.WHITE);
		save.setTranslateX(632.0);
		save.setTranslateY(312.0);
		save.setPrefHeight(40.0);
		save.setPrefWidth(189.0);

		save.setId("loadButton");
		grid.getChildren().add(save);

		// Bouton: Quitter la partie 
		Button leave = new Button("Quitter le jeu");

		leave.setTextFill(Color.WHITE);
		leave.setTranslateX(632.0);
		leave.setTranslateY(362.0);
		leave.setPrefHeight(40.0);
		leave.setPrefWidth(189.0);

		leave.setId("leaveButton");
	
		grid.getChildren().add(leave);

	
		
		// Scene
		Scene scene = new Scene(grid, 1000, 600);
		
		// Titre
		primaryStage.setTitle("Stratego - Main Menu");
		
		// Réglage de Scene
		primaryStage.setScene(scene);
		scene.getStylesheets().add("/com/stratego/assets/styles/menu.css");

		// Afficher Scene
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
}
