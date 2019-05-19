package be.ac.umons.stratego.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import be.ac.umons.stratego.SaveLoad;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * <h1>MenuState</h1>
 * 
 * <p>
 * Classe permettant de representer graphiquement le menu principal du jeu.
 * </p>
 * 
 */

public class MenuView extends Application {

	Pane menu = new Pane();

	public void draw(Stage primaryStage) {

		// Image de fond
		Image background = new Image("file:assets/styles/main.png");
		ImageView backgroundView = new ImageView(background);

		backgroundView.setX(-67.0);
		backgroundView.setPreserveRatio(true);
		backgroundView.setFitHeight(614.0);
		backgroundView.setFitWidth(621.0);

		menu.getChildren().add(backgroundView);

		// Image logo
		Image logo = new Image("file:assets/logo_pixel.png");
		ImageView logoView = new ImageView(logo);

		logoView.setX(584.0);
		logoView.setY(35.0);
		logoView.setFitHeight(163.0);
		logoView.setFitWidth(300.0);
		logoView.setPreserveRatio(true);

		menu.getChildren().add(logoView);

		// Label de copyright
		Label copyright = new Label("Projet d'informatique // 2018-2019");
		copyright.setTextFill(Color.web("#DDDDDD"));
		copyright.setFont(new Font("Lucida Console", 12.0));

		copyright.setTranslateX(602.0);
		copyright.setTranslateY(567.0);

		menu.getChildren().add(copyright);

		// Bouton: Nouvelle partie
		Button start = new Button("Lancer une nouvelle partie");

		start.setTextFill(Color.WHITE);
		start.setTranslateX(632.0);
		start.setTranslateY(262.0);
		start.setPrefHeight(40.0);
		start.setPrefWidth(189.0);

		start.setId("startButton");

		start.setOnAction(e -> chooseOpponentWindow(primaryStage));

		menu.getChildren().add(start);

		// Bouton: Chargement de sauvegarde
		Button save = new Button("Charger une sauvegarde");

		save.setTextFill(Color.WHITE);
		save.setTranslateX(632.0);
		save.setTranslateY(312.0);
		save.setPrefHeight(40.0);
		save.setPrefWidth(189.0);

		save.setId("loadButton");

		save.setOnAction(e -> {
			try {
				new GameView(SaveLoad.read());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		menu.getChildren().add(save);

		// Bouton: Quitter la partie
		Button leave = new Button("Quitter le jeu");

		leave.setTextFill(Color.WHITE);
		leave.setTranslateX(632.0);
		leave.setTranslateY(362.0);
		leave.setPrefHeight(40.0);
		leave.setPrefWidth(189.0);

		leave.setId("leaveButton");

		leave.setOnAction(e -> Platform.exit());

		menu.getChildren().add(leave);
	}

	public void chooseOpponentWindow(Stage primaryStage) {

		Label choose = new Label("Qui veux-tu affronter?");
		choose.setTextFill(Color.web("#DDDDDD"));
		choose.setFont(new Font("Lucida Console", 14.0));
		choose.setTranslateX(130);
		choose.setTranslateY(20);

		Button me = new Button("Aleatoire");
		Button he = new Button("Difficile");

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

		chooseOpponent.centerOnScreen();

		chooseOpponent.initModality(Modality.WINDOW_MODAL);
		chooseOpponent.initOwner(primaryStage);
		chooseOpponent.show();
		// Settings + importing CSS
		secondScene.getStylesheets().add("file:assets/styles/menu.css");
		chooseOpponent.setResizable(false);

		// Actions

		me.setOnAction(e -> chooseOpponent(1, chooseOpponent));
		he.setOnAction(e -> chooseOpponent(2, chooseOpponent));

	}

	public void chooseOpponent(int i, Stage stage) {

		new GameView(i);
		stage.close();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		menu.setPrefHeight(600);
		menu.setPrefWidth(900);

		// Scene
		Scene scene = new Scene(menu);
		// Titre
		primaryStage.setTitle("Stratego - Menu principal");
		// Reglage de Scene
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		scene.getStylesheets().add("file:assets/styles/menu.css");

		// Afficher Scene
		primaryStage.show();

		// Permet d'afficher les elements graphiques
		draw(primaryStage);
	}

}
