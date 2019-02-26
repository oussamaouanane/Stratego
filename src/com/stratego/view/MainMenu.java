package com.stratego.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

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
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class MainMenu extends Application {

	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

        // Background image -- Loading and Settings
		Image background = new Image("/com/stratego/assets/maps/map_main.png");
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
        Label copyright = new Label("Copyright // 2018-2019");
        copyright.setTextFill(Color.web("#DDDDDD"));
        copyright.setFont(new Font("Lucida Console", 12.0));
        
        copyright.setTranslateX(642.0);
        copyright.setTranslateY(567.0);
        
        // NewGame button
        Button start = new Button("Start a new game!");
        
        start.setTextFill(Color.WHITE);
        start.setTranslateX(632.0);
        start.setTranslateY(262.0);
        start.setPrefHeight(40.0);
        start.setPrefWidth(189.0);
        
        start.setId("startButton");
        
        // LoadSave button
        Button save = new Button("Load a save!");
        
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
				saveUpload.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("XML", "*.xml")
						);
			}
		});
        
        // Leave button
        Button leave = new Button("Leave the game!");
        
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

        //Displaying
        primaryStage.show();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
