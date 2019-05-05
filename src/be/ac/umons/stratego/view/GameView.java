package be.ac.umons.stratego.view;

import java.util.ArrayList;

import be.ac.umons.stratego.controller.GridController;
import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.Pawn;
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

/**
 * <h1>GameView</h1>
 * 
 * <p>
 * Classe permettant de représenter graphiquement une partie.
 * </p>
 *
 */

public class GameView {

	private SquareView[][] square = new SquareView[10][10];
	private Pane grid = new Pane();
	private Pane setup = new Pane();
	private Pane inGame = new Pane();
	private GameProcess game;

	private PawnView pawnChosenSettingUp;
	private PawnView pawnChosen;
	private ArrayList<SquareView> highlightSquareView = new ArrayList<SquareView>();

	private int[] PAWNS_COMPOSITION = { 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 1 };
	private GridController gridController;

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
		setupPawns();
		// Permet de créer la grille
		createGrid();
		// Permet de créer une instance de GameProcess
		game = new GameProcess(ai);
		// Permet de créer une instance de GridController
		gridController = new GridController(game);
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
		Button save = new Button("Sauvegarder");

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

	private void createGrid() {

		for (int i = 9; i >= 0; i--) {
			for (int j = 0; j < 10; j++) {

				square[i][j] = new SquareView(61 * j, 61 * i, 61, 61);
				square[i][j].setId("square");
				square[i][j].setRow(9-i);
				square[i][j].setColumn(j);

				square[i][j].setOnMousePressed(e -> SquareEvent(e));

				grid.getChildren().add(square[i][j]);
			}
		}
	}

	/**
	 * Méthode permettant de mettre en place le placement des pions, création des
	 * icônes de pions.
	 */

	public void setupPawns() {

		setup.setPrefSize(400, 610);
		setup.setTranslateX(900 - 290);

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

	/**
	 * Méthode permettant de créer un pion sous forme visuelle.
	 * 
	 * @param rank   Rang du pion.
	 * @param player Joueur à qui appartient le pion.
	 * @return PawnView Un pion sous la forme d'une illustration.
	 */

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

	/**
	 * Même chose que createPawn mais pour les pions de placement.
	 * 
	 * @param rank   Rang du pion.
	 * @param player Joueur à qui appartient le pion.
	 * @return ImageView Une image qui représente le pion.
	 * 
	 * @see GridController#createPawn(int, int)
	 */

	public ImageView createPawnForSettingUp(int rank, int player) {

		PawnView pawn = new PawnView(rank, player);
		// @see #PawnEventSettingUp
		pawn.setOnMousePressed(e -> PawnEventSettingUp(e));
		// Permet de gérer la transparence
		pawn.setPickOnBounds(true);
		setup.getChildren().add(pawn);

		return pawn;
	}

	/**
	 * Méthode permettant de créer un pion lorsqu'on place une image de pion dans
	 * une case SquareView, ne fonctionne que pendant la phase de placement de
	 * pions.
	 * 
	 * @param sq           La case où on veut placer pawnToCreate
	 * @param pawnToCreate Le pion qu'il faut créer dans sq
	 */

	public void movePawnSettingUp(SquareView sq) {

		// Création d'un pion
		PawnView pawn = createPawn(pawnChosenSettingUp.getRank(), pawnChosenSettingUp.getPlayer());
		// Permet d'associer les coordonnées aux pions

		pawn.setX(sq.getX());
		pawn.setY(sq.getY());
		// Permet d'associer au pion l'instance SquareView, Square et vice-versa.
		pawn.setSquare(sq);
		sq.setPawnView(pawn);
		Pawn newPawn = new Pawn(pawn.getRank(), pawn.getPlayer());
		getSquare(sq).setPawn(newPawn);
		pawnSetSquare(newPawn, sq);

		// Permet de mettre à jour la liste des pions
		PAWNS_COMPOSITION[pawn.getRank()]--;
		setupPawns();

		if (PAWNS_COMPOSITION[pawn.getRank()] == 0) {
			pawnChosenSettingUp = null;
		}
	}

	/**
	 * Méthode permettant de définir le processus de remplacement d'un pion lors de
	 * la phase d'installation des pions.
	 * 
	 * @param source Pion qui doit se faire remplacer
	 */

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
		Pawn pawnModel = new Pawn(pawn.getRank(), pawn.getPlayer());
		getSquare(square).setPawn(pawnModel);

		inGame.getChildren().remove(source);
		PAWNS_COMPOSITION[pawnChosenSettingUp.getRank()]--;

		setupPawns();

		pawnChosenSettingUp = null;
	}

	/**
	 * Méthode permettant de vérifier si le placement des pions est terminé.
	 * Traverse la liste des pions restants à placer et vérifie si il y en a encore.
	 * 
	 * @return Booléen qui indique si le placement est terminé ou pas.
	 */

	public boolean checkSettingUpFinish() {

		for (int i : PAWNS_COMPOSITION) {
			if (i > 0)
				return false;
		}
		endSettingUp();
		return true;
	}

	/**
	 * Méthode permettant de mettre fin au placement des pions et met en place un
	 * système de score.
	 */

	public void endSettingUp() {

		setup.getChildren().clear();
		showingScore();
		game.endSettingUp();
	}

	/**
	 * Méthode permettant d'afficher le score une fois le placement de pions
	 * terminé.
	 * 
	 * @see GameView#endSettingUp
	 */

	public void showingScore() {

		int userScore = 0; // game.getAI().getDeathPawns().size();
		int aiScore = 0; // game.getUser().getDeathPawns().hashCode();

		Label title = new Label("Affichage du score");
		title.setTranslateX(60);
		title.setTranslateY(25);
		title.setFont(new Font("Lucida Console", 24.0));
		title.setTextFill(Color.web("#DDDDDD"));

		Label userScoreDisplay = new Label(Integer.toString(userScore));
		userScoreDisplay.setFont(new Font("Arial Bold", 70.0));
		userScoreDisplay.setTextFill(Color.web("#4969A0"));
		userScoreDisplay.setTranslateX(130);
		userScoreDisplay.setTranslateY(200);

		Label dash = new Label("-");
		dash.setFont(new Font("Arial Bold", 70.0));
		dash.setTranslateX(185);
		dash.setTranslateY(200);
		dash.setTextFill(Color.web("#DDDDDD"));

		Label aiScoreDisplay = new Label(Integer.toString(aiScore));
		aiScoreDisplay.setFont(new Font("Arial Bold", 70.0));
		aiScoreDisplay.setTextFill(Color.web("#A94749"));
		aiScoreDisplay.setTranslateX(230);
		aiScoreDisplay.setTranslateY(200);

		setup.getChildren().addAll(title, userScoreDisplay, dash, aiScoreDisplay);
	}

	/**
	 * Ensemble de méthodes qui permettent de gérer les événements liés au clics de
	 * souris. Vérifier la documentation de MouseEvent pour avoir plus
	 * d'informations sur le fonctionnement.
	 * 
	 * @param e Clic gauche souris
	 * @see MouseEvent
	 */

	/**
	 * Méthode permettant de gérer les clics qui se font sur les images des pions
	 * pendant le placement des pions.
	 */

	private void PawnEventSettingUp(MouseEvent e) {

		PawnView pawn = (PawnView) e.getSource();
		if (PAWNS_COMPOSITION[pawn.getRank()] != 0)
			pawnChosenSettingUp = pawn;
	}

	/**
	 * Méthode permettant de surligner les SquareView où les mouvements sont légaux
	 * autour du pion.
	 * 
	 * @param square est l'instance Square où le pion est stocké.
	 */

	public void setHighlight(Square square) {
		ArrayList<Couple> couple = gridController.SquareToHighlight(square);
		for (Couple c : couple) {
			SquareView sq = getSquareView(c.getX(), c.getY());
			sq.setId("highlight");
			highlightSquareView.add(sq);
		}
	}

	/**
	 * Méthode permettant d'enlever tout surlignement de la grille.
	 */

	public void resetHighlight() {
		for (SquareView c : highlightSquareView) {
			c.setId("square");
			highlightSquareView.remove(c);
		}
	}

	/**
	 * Méthode permettant de gérer les clics qui se font sur les pions. Il existe
	 * plusieurs cas: - checkSettingUpFinish n'est pas terminé donc il s'agit d'un
	 * remplacement entre deux pions - Aucun pion n'est stocké dans pawnChosen donc
	 * cela signifie qu'aucun mouvement est en cours - Un pion est stocké et on
	 * désire seulement de sélectionner un autre pion, le pion stocké sera donc
	 * écrasé - Un pion est stocké et on clique sur un pion adverse (ici un combat
	 * s'ensuit).
	 */

	private void PawnEvent(MouseEvent e) {

		PawnView pawn = (PawnView) e.getSource();
		// Condition pour qu'un pion soit manipulable.
		boolean rightPlayerCondition = (pawn.getPlayer() == 1) && (game.getTurn() == 1)
				&& ((pawn.getRank() != 11) && pawn.getRank() != 10);
		// Gestion pendant le placement des pions, ne servira que pour remplacer un
		// pion.
		if (!checkSettingUpFinish()) {
			if (pawnChosenSettingUp != null)
				switchPawnSettingUp(pawn);
		}

		/**
		 * ON SUPPOSE LE PLACEMENT DES PIONS TERMINE A PARTIR D'ICI.
		 */

		// Met pawnChosen seulement si il n'y a pas de pawnChosen. S'il y en a un alors
		// on est dans un déplacement en cours ou alors on s'apprête à changer de pion!
		else if ((pawnChosen == null) && (rightPlayerCondition)) {
			pawnChosen = pawn;
			setHighlight(getSquare(pawnChosen.getSquare()));
		}

		// Gestion lorsqu'on a déjà cliqué sur un pion et qu'on change de pion
		if ((pawnChosen != null) && (rightPlayerCondition)) {
			System.out.print(highlightSquareView.size());
			// resetHighlight();
			pawnChosen = pawn;
		}

		// Gestion lorsqu'on a déjà cliqué sur un pion et qu'on clique sur un pion
		// adverse, ici un combat!
		if ((pawnChosen != null) && (pawn.getPlayer() == 2) && (game.getTurn() == 1)) {

		}
	}

	/**
	 * Méthode permettant de déplacer le pion stocké dans l'instance SquareView
	 * sélectionnée. Il existe plusieurs cas: - checkSettingUp n'est pas terminé,
	 * dans ce cas on place seulement le pion stocké dans pawnChosenSettingUp dans
	 * l'instance SquareView - pawnChosen n'est pas vide, dans ce cas on vérifie que
	 * le mouvement est légal et dans ce cas, on place le pion dans l'instance
	 * SquareView.
	 */

	private void SquareEvent(MouseEvent e) {

		// Récupère la source du pion
		SquareView sq = (SquareView) e.getSource();
		System.out.println(("rangée: " + sq.getRow()));
		// Pendant le placement des pions
		if (pawnChosenSettingUp != null)
			// Dans le cas où le placement des pions est toujours en cours.
			if (!checkSettingUpFinish() && (sq.getRow() < 4)) {
				movePawnSettingUp(sq);
			}

		/**
		 * ON SUPPOSE LE PLACEMENT DES PIONS TERMINE A PARTIR D'ICI.
		 */

		if (pawnChosen != null && gridController.isMovePossible(getSquare(pawnChosen.getSquare()), getSquare(sq))) {
			// Fixe les coordonnées de la case au pion.
			Pawn pawn = getSquare(pawnChosen.getSquare()).getPawn();
			pawnChosen.setX(sq.getX());
			pawnChosen.setY(sq.getY());
			pawnChosen.setSquare(sq);
			getSquare(sq).setPawn(pawn);
			pawnChosen = null;
			//resetHighlight();
			// + changer le tour ce qui

		}
	}

	/**
	 * Méthode permettant d'appliquer au pawn l'instance Square liée.
	 * 
	 * @param pawn   Pion dont on veut placer Square
	 * @param square Square graphique, SquareView
	 */

	public void pawnSetSquare(Pawn pawn, SquareView square) {
		pawn.setSquare(getSquare(square));
	}

	/**
	 * Méthode permettant de retourner l'état actuel de la partie en cours.
	 * 
	 * @return L'état de la partie
	 */

	public GameState getState() {
		return game.getGameStateManager().getState();
	}

	/**
	 * Méthode permettant de retourner une instance de Square.
	 * 
	 * @param square SquareView qu'on doit convertir en Square
	 * @return Square L'instance square convertie.
	 */

	public Square getSquare(SquareView square) {
		return game.getGrid().getSquare((9-square.getRow()), square.getColumn());
	}

	public SquareView getSquareView(int row, int column) {
		return square[row][column];
	}

}
