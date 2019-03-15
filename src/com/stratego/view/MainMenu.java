package com.stratego.view;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * MainMenu --- Main menu of the game, give access to: Start a new game Loading
 * saves Leaving the app
 * 
 * @author
 */
public class MainMenu extends Application {

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		// Background image -- Loading and Settings
		Image background = new Image("/com/stratego/assets/styles/main.png");
		ImageView backgroundView = new ImageView(background);

		backgroundView.setX(-67.0);
		backgroundView.setFitHeight(614.0);
		backgroundView.setFitWidth(621.0);
		backgroundView.setPreserveRatio(true);

		// Logo image -- Loading and Settings
		Image logo = new Image("/com/stratego/assets/logo_pixel.png");
		ImageView logoView = new ImageView(logo);

		logoView.setX(584.0);
		logoView.setY(35.0);
		logoView.setFitHeight(163.0);
		logoView.setFitWidth(300.0);
		logoView.setPreserveRatio(true);

		// Copyright label
		Label copyright = new Label("Projet d'informatique // 2018-2019");
		copyright.setTextFill(Color.web("#DDDDDD"));
		copyright.setFont(new Font("Lucida Console", 12.0));

		copyright.setTranslateX(602.0);
		copyright.setTranslateY(567.0);

		// NewGame button
		Button start = new Button("Lancer une nouvelle partie");

		start.setTextFill(Color.WHITE);
		start.setTranslateX(632.0);
		start.setTranslateY(262.0);
		start.setPrefHeight(40.0);
		start.setPrefWidth(189.0);

		start.setId("startButton");

		start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Label choose = new Label("Qui veux-tu affronter?");
				choose.setTextFill(Color.web("#DDDDDD"));
				choose.setFont(new Font("Lucida Console", 14.0));
				choose.setTranslateX(130);
				choose.setTranslateY(20);

				Button me = new Button("Moi");
				Button he = new Button("Lui");

				me.setPrefHeight(40.0);
				me.setPrefWidth(189.0);
				me.setId("startButton");
				me.setTranslateX(25);
				me.setTranslateY(50);

				he.setPrefHeight(40.0);
				he.setPrefWidth(189.0);
				he.setId("loadButton");
				he.setTranslateX(235);
				he.setTranslateY(50);

				Group second = new Group(me, he, choose);
				// Scene
				Scene secondScene = new Scene(second, 450, 100);
				// Stage
				Stage chooseOpponent = new Stage();
				chooseOpponent.setScene(secondScene);
				// Title
				chooseOpponent.setTitle("Choisis ton adversaire!");
				// Size
				chooseOpponent.setX(primaryStage.getX() + (primaryStage.getX() / 2));
				chooseOpponent.setY(primaryStage.getY() + (primaryStage.getY() / 2));

				chooseOpponent.initModality(Modality.WINDOW_MODAL);
				chooseOpponent.initOwner(primaryStage);
				chooseOpponent.show();
				// Settings + importing CSS
				secondScene.getStylesheets().add("/com/stratego/assets/styles/menu.css");
				chooseOpponent.setResizable(false);
			}
		});

		// LoadSave button
		Button save = new Button("Charger une sauvegarde");

		save.setTextFill(Color.WHITE);
		save.setTranslateX(632.0);
		save.setTranslateY(312.0);
		save.setPrefHeight(40.0);
		save.setPrefWidth(189.0);

		save.setId("loadButton");

		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser saveUpload = new FileChooser();
				saveUpload.setTitle("Load your save");
				saveUpload.showOpenDialog(primaryStage);
				saveUpload.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
			}
		});

		// Leave button
		Button leave = new Button("Quitter le jeu");

		leave.setTextFill(Color.WHITE);
		leave.setTranslateX(632.0);
		leave.setTranslateY(362.0);
		leave.setPrefHeight(40.0);
		leave.setPrefWidth(189.0);

		leave.setId("leaveButton");

		leave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});

		Group root = new Group(backgroundView, logoView, copyright, start, save, leave);
		// Scene
		Scene scene = new Scene(root, 900, 600);
		// Title
		primaryStage.setTitle("Stratego - Main Menu");
		// Setting the scene
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		scene.getStylesheets().add("/com/stratego/assets/styles/menu.css");

		// Displaying
		primaryStage.show();
	}


}
