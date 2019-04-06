package com.stratego.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InGameState extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Image de fond
		Image background = new Image("/com/stratego/assets/maps/map_main.png");
		ImageView backgroundView = new ImageView(background);

		backgroundView.setFitHeight(610.0);
		backgroundView.setFitWidth(610.0);
		backgroundView.setPreserveRatio(true);
		
		// Bouton: Sauvegarder la partie
		Button save = new Button("Charger une sauvegarde");

		save.setTextFill(Color.WHITE);
		save.setTranslateX(632.0);
		save.setTranslateY(312.0);
		save.setPrefHeight(40.0);
		save.setPrefWidth(189.0);

		save.setId("loadButton");

		// Bouton: Quitter la partie 
		Button leave = new Button("Quitter le jeu");

		leave.setTextFill(Color.WHITE);
		leave.setTranslateX(632.0);
		leave.setTranslateY(362.0);
		leave.setPrefHeight(40.0);
		leave.setPrefWidth(189.0);

		leave.setId("leaveButton");

		// Panneau pions vivants et morts
		TabPane tab = new TabPane();
		Tab alivePawns = new Tab("Pions vivants");
		tab.getTabs().add(alivePawns);
		
		tab.setPrefSize(300, 610);
		
		
		
		Group root = new Group(backgroundView, save, leave, tab);
		
		// Scene
		Scene scene = new Scene(root, 1000, 600);
		
		// Titre
		primaryStage.setTitle("Stratego - Main Menu");
		
		// Réglage de Scene
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		scene.getStylesheets().add("/com/stratego/assets/styles/menu.css");

		// Afficher Scene
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
}
