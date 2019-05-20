package be.ac.umons.stratego.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import be.ac.umons.stratego.SaveLoad;
import be.ac.umons.stratego.controller.GridController;
import be.ac.umons.stratego.model.GameProcess;
import be.ac.umons.stratego.model.grid.Square;
import be.ac.umons.stratego.model.pawn.Couple;
import be.ac.umons.stratego.model.pawn.Pawn;
import be.ac.umons.stratego.model.state.GameState;
import be.ac.umons.stratego.view.SquareView;
import be.ac.umons.stratego.view.PawnView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <h1>GameView</h1>
 * 
 * <p>
 * Classe permettant de representer graphiquement une partie.
 * </p>
 *
 */

public class GameView {

	private Stage primaryStage;

	private SquareView[][] square = new SquareView[10][10];
	private Pane grid = new Pane();
	private Pane setup = new Pane();
	private Pane inGame = new Pane();
	private GameProcess game;

	private PawnView pawnChosenSettingUp;
	private PawnView pawnChosen;
	private PawnView opponentPawnChosen;
	private ArrayList<SquareView> highlightSquareView = new ArrayList<SquareView>();

	private int[] PAWNS_COMPOSITION = { 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 1 };
	private GridController gridController;

	private boolean settingUpAIFinished = false;
	// Score
	Label userScoreDisplay;
	Label aiScoreDisplay;

	private Button save;

	public GameView(int ai) {

		primaryStage = new Stage();
		// Scene
		Scene scene = new Scene(inGame, 1000, 610);
		// Titre
		primaryStage.setTitle("Stratego");
		// Reglage de Scene
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		scene.getStylesheets().add("file:assets/styles/menu.css");
		// Afficher Scene
		primaryStage.show();
		primaryStage.setResizable(false);
		// Permet de creer tous les elements graphiques
		draw(primaryStage);
		setupPawns();
		// Permet de creer une instance de GameProcess
		game = new GameProcess(ai);
		// Permet de creer une instance de GridController
		gridController = new GridController(this, game);

	}

	/**
	 * Constructeur a utiliser en cas de chargement de sauvegarde.
	 * 
	 * @param game Instance GameProcess de la partie sauvegardee.
	 */

	public GameView(GameProcess game) {

		primaryStage = new Stage();
		// Scene
		Scene scene = new Scene(inGame, 1000, 610);
		// Titre
		primaryStage.setTitle("Stratego");
		// Reglage de Scene
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		scene.getStylesheets().add("file:assets/styles/menu.css");
		// Afficher Scene
		primaryStage.show();
		primaryStage.setResizable(false);
		// Permet de creer tous les elements graphiques
		draw(primaryStage);
		// Permet de charger l'instance de GameProcess
		this.game = game;
		// Permet de creer une instance de GridController
		gridController = new GridController(this, game);
		// Permet de mettre en place
		loadData();
	}

	// XXX Representation graphique des elements de base

	public void draw(Stage stage) {

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

		// Creation de la grille
		createGrid();

		// Bouton: Sauvegarder la partie
		save = new Button("Sauvegarder");
		save.setOnAction(e -> {
			try {
				SaveLoad.write(game);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		save.setTextFill(Color.WHITE);
		save.setTranslateX(640.0);
		save.setTranslateY(540.0);
		save.setPrefHeight(40.0);
		save.setPrefWidth(160.0);
		save.setVisible(false);

		save.setId("loadButton");
		inGame.getChildren().add(save);

		// Bouton: Quitter la partie
		Button leave = new Button("Revenir au menu principal");

		leave.setTextFill(Color.WHITE);
		leave.setTranslateX(820.0);
		leave.setTranslateY(540.0);
		leave.setPrefHeight(40.0);
		leave.setPrefWidth(160.0);
		leave.setOnAction(e -> stage.close());

		leave.setId("leaveButton");

		inGame.getChildren().add(leave);
	}

	/**
	 * Methode permettant de creer la grille de SquareView.
	 */

	private void createGrid() {

		for (int i = 9; i >= 0; i--) {
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

	// XXX Sauvegarde

	/**
	 * Methode permettant de charger la sauvegarde et mettre en place les elements.
	 */

	public void loadData() {
		// Fenetre de scores
		setup.setPrefSize(400, 610);
		setup.setTranslateX(900 - 290);
		grid.getChildren().add(setup);
		showingScore();

		for (int i = 0; i < PAWNS_COMPOSITION.length; i++)
			PAWNS_COMPOSITION[i] = 0;
		// Affichage des pions
		for (int i = 9; i >= 0; i--) {
			for (int j = 0; j < 10; j++) {
				if (getSquare(getSquareView(9 - i, j)).getPawn() != null) {
					PawnView pawn = createPawn(getSquare(getSquareView(9 - i, j)).getPawn().getRank(),
							getSquare(getSquareView(9 - i, j)).getPawn().getPlayer());
					handleMovementGUI(pawn, square[i][j]);
				}
			}
		}
	}

	// XXX On est encore dans le placement des pions.

	/**
	 * Methode permettant de mettre en place le placement des pions, creation des
	 * icones de pions.
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
	 * Methode permettant de generer la liste des pions a placer pour l'intelligence
	 * artificielle et creer les pions necessaires.
	 */

	public ArrayList<Integer> settingUpAI() {

		ArrayList<Integer> compo = new ArrayList<Integer>();
		// Generer une composition de pions (ranks)
		for (int i : Pawn.PAWNS_COMPOSITION)
			compo.add(i);

		for (int i = 0; i < 15; i++)
			Collections.shuffle(compo);

		return compo;
	}

	/**
	 * Methode permettant de mettre en place le placement des pions pour
	 * l'intelligence artificielle.
	 */

	public void setupPawnsAI() {

		ArrayList<Integer> pawnRanks = settingUpAI();

		int cnt = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {

				int rank = pawnRanks.get(cnt);

				PawnView pawn = createPawn(rank, 2);
				SquareView square = getSquareView(9 - i, j);
				// Partie graphique
				handleMovementGUI(pawn, square);
				// Partie logique
				Pawn pawnM = new Pawn(rank, 2);
				handleMovement(pawnM, getSquare(square));
				game.getAI().addPawn(pawnM);
				cnt++;
			}
		}
	}

	/**
	 * Meme chose que createPawn mais pour les pions de placement.
	 * 
	 * @param rank   Rang du pion.
	 * @param player Joueur a qui appartient le pion.
	 * @return ImageView Une image qui represente le pion.
	 * 
	 * @see GridController#createPawn(int, int)
	 */

	public ImageView createPawnForSettingUp(int rank, int player) {

		PawnView pawn = new PawnView(rank, player);
		// @see #PawnEventSettingUp
		pawn.setOnMousePressed(e -> PawnEventSettingUp(e));
		// Permet de gerer la transparence
		pawn.setPickOnBounds(true);
		setup.getChildren().add(pawn);

		return pawn;
	}

	/**
	 * Methode permettant de creer un pion lorsqu'on place une image de pion dans
	 * une case SquareView, ne fonctionne que pendant la phase de placement de
	 * pions.
	 * 
	 * @param sq           La case ou on veut placer pawnToCreate
	 * @param pawnToCreate Le pion qu'il faut creer dans sq
	 */

	public void movePawnSettingUp(SquareView sq) {

		// Creation d'un pion
		PawnView pawn = createPawn(pawnChosenSettingUp.getRank(), pawnChosenSettingUp.getPlayer());
		handleMovementGUI(pawn, sq);
		Pawn newPawn = new Pawn(pawn.getRank(), pawn.getPlayer());
		handleMovement(newPawn, getSquare(sq));
		game.getUser().addPawn(newPawn);

		// Permet de mettre a jour la liste des pions
		PAWNS_COMPOSITION[pawn.getRank()]--;
		setupPawns();

		if (PAWNS_COMPOSITION[pawn.getRank()] == 0) {
			pawnChosenSettingUp = null;
		}
	}

	/**
	 * Methode permettant de definir le processus de remplacement d'un pion lors de
	 * la phase d'installation des pions.
	 * 
	 * @param source Pion qui doit se faire remplacer
	 */

	public void switchPawnSettingUp(PawnView source) {

		// Permet d'incrementer de un le compteur du pion
		PAWNS_COMPOSITION[source.getRank()]++;
		// Recuperation du carre du pion
		SquareView square = source.getSquareView();
		// Creation du pion
		PawnView pawn = createPawn(pawnChosenSettingUp.getRank(), pawnChosenSettingUp.getPlayer());
		// Coordonnees du pion
		handleMovementGUI(pawn, square);
		Pawn pawnModel = new Pawn(pawn.getRank(), pawn.getPlayer());
		handleMovement(pawnModel, getSquare(square));
		inGame.getChildren().remove(source);
		PAWNS_COMPOSITION[pawnChosenSettingUp.getRank()]--;

		setupPawns();

		pawnChosenSettingUp = null;
	}

	/**
	 * Methode permettant de verifier si le placement des pions est termine.
	 * Traverse la liste des pions restants a placer et verifie si il y en a encore.
	 * 
	 * @return Booleen qui indique si le placement est termine ou pas.
	 */

	public boolean checkSettingUpFinish() {

		for (int i : PAWNS_COMPOSITION) {
			if (i > 0)
				return false;
		}
		if (settingUpAIFinished == false) {
			endSettingUp();
			settingUpAIFinished = true;
		}
		return true;
	}

	/**
	 * Methode permettant de mettre fin au placement des pions et met en place un
	 * systeme de score.
	 */

	public void endSettingUp() {

		setupPawnsAI();
		setup.getChildren().clear();
		showingScore();
		game.endSettingUp();
		save.setVisible(true);
	}

	// XXX On est plus dans le placement des pions.

	/**
	 * Methode permettant d'afficher le score une fois le placement de pions
	 * termine.
	 * 
	 * @see GameView#endSettingUp
	 */

	public void showingScore() {

		int userScore = game.getScore(1);
		int aiScore = game.getScore(2);

		Label title = new Label("Affichage du score");
		title.setTranslateX(60);
		title.setTranslateY(25);
		title.setFont(new Font("Lucida Console", 24.0));
		title.setTextFill(Color.web("#DDDDDD"));

		userScoreDisplay = new Label(Integer.toString(userScore));
		userScoreDisplay.setFont(new Font("Arial Bold", 70.0));
		userScoreDisplay.setTextFill(Color.web("#4969A0"));
		userScoreDisplay.setTranslateX(130);
		userScoreDisplay.setTranslateY(200);

		Label dash = new Label("-");
		dash.setFont(new Font("Arial Bold", 70.0));
		dash.setTranslateX(185);
		dash.setTranslateY(200);
		dash.setTextFill(Color.web("#DDDDDD"));

		aiScoreDisplay = new Label(Integer.toString(aiScore));
		aiScoreDisplay.setFont(new Font("Arial Bold", 70.0));
		aiScoreDisplay.setTextFill(Color.web("#A94749"));
		aiScoreDisplay.setTranslateX(230);
		aiScoreDisplay.setTranslateY(200);

		setup.getChildren().addAll(title, userScoreDisplay, dash, aiScoreDisplay);
	}

	/**
	 * Methode permettant de mettre a jour le score de la partie
	 */

	public void updateScore() {

		userScoreDisplay.setText(Integer.toString(game.getScore(1)));
		aiScoreDisplay.setText(Integer.toString(game.getScore(2)));

		if (game.getScore(1) > 9)
			userScoreDisplay.setTranslateX(90);
	}

	// XXX La partie est termine

	public boolean checkFinish() {

		if (game.checkWin()) {
			endGame(game.winner());
			game.endGame();
			return true;
		}
		return false;
	}

	public void endGame(int winner) {

		Image bg = null;

		if (winner == 1) {
			bg = new Image("file:assets/styles/victoire.png");
		} else {
			bg = new Image("file:assets/styles/defaite.png");
		}
		ImageView bgView = new ImageView(bg);
		bgView.setFitHeight(165);
		bgView.setFitWidth(465);

		Group endGame = new Group(bgView);
		// Scene
		Scene secondScene = new Scene(endGame, 450, 150);
		// Stage
		Stage stage = new Stage();
		stage.setScene(secondScene);
		// Title
		stage.setTitle("Fin de la partie");
		// Size

		stage.centerOnScreen();

		stage.initModality(Modality.WINDOW_MODAL);
		stage.setOnCloseRequest(e -> primaryStage.close());
		stage.initOwner(primaryStage);
		stage.show();
		// Settings + importing CSS
		secondScene.getStylesheets().add("file:assets/styles/menu.css");
		stage.setResizable(false);

	}

	// XXX Methodes liees aux pions.

	/**
	 * Methode permettant de creer un pion sous forme visuelle.
	 * 
	 * @param rank   Rang du pion.
	 * @param player Joueur a qui appartient le pion.
	 * @return PawnView Un pion sous la forme d'une illustration.
	 */

	public PawnView createPawn(int rank, int player) {

		// Creation du PawnView
		PawnView pawn = new PawnView(rank, player);
		// @see #PawnEvent
		pawn.setOnMousePressed(e -> PawnEvent(e));
		// Permet de gerer la transparence
		pawn.setPickOnBounds(true);
		inGame.getChildren().add(pawn);
		return pawn;
	}

	/**
	 * Methode permettant de surligner les SquareView ou les mouvements sont legaux
	 * autour du pion.
	 * 
	 * @param square est l'instance Square ou le pion est stocke.
	 */

	public void setHighlight(Square square) {
		ArrayList<Couple> couple = gridController.SquareToHighlight(square);
		if (!couple.isEmpty()) {
			for (Couple c : couple) {
				SquareView sq = getSquareView(c.getX(), c.getY());
				sq.setId("highlight");
				highlightSquareView.add(sq);
			}
		}
	}

	/**
	 * Methode permettant d'enlever tout surlignement de la grille.
	 */

	public void resetHighlight() {
		if (highlightSquareView.size() > 0) {
			for (SquareView c : highlightSquareView) {
				c.setId("square");
			}

			highlightSquareView.clear();
		}
	}

	/**
	 * Methode pour appliquer un deplacement d'un Pawn vers un Square.
	 * 
	 * @param pawn   Pion qu'on veut deplacer.
	 * @param square Case ou on veut deplacer pawn.
	 */

	public void handleMovement(Pawn pawn, Square square) {

		// Reinitialisation
		if (pawn.getSquare() != null)
			pawn.getSquare().setPawn(null);

		square.setPawn(pawn);
		pawn.setSquare(square);
	}

	/**
	 * Methode pour appliquer un deplacement d'un PawnView vers un SquareView.
	 * 
	 * @param pawn   Pion qu'on veut deplacer.
	 * @param square Case ou on veut deplacer pawn.
	 */

	public void handleMovementGUI(PawnView pawn, SquareView square) {

		// Reinitialisation
		if (pawn.getSquareView() != null)
			pawn.getSquareView().setPawnView(null);

		square.setPawnView(pawn);
		pawn.setSquareView(square);
		pawn.setX(square.getX());
		pawn.setY(square.getY());
	}

	// XXX Methodes liees aux IA

	/**
	 * Methode permettant de reveler un pion.
	 * 
	 * @param pawn Pion qu'on veut reveler.
	 */

	public void setAIPawnVisible(PawnView pawn) {
		pawn.setVisible();
	}

	/**
	 * Methode permettant de masquer un pion.
	 * 
	 * @param pawn Pion qu'on veut masquer.
	 */

	public void setAIPawnHidden(PawnView pawn) {
		pawn.setHidden();
	}

	/**
	 * Methode permettant de gerer le tour de l'intelligence artificielle. On y gere
	 * le mouvement et les combats.
	 * 
	 * @see GridController#AITurn()
	 */

	public void AIturn() {

		Couple couple = game.getAI().getNextMove();
		// Au cas ou on a un deplacement nul
		while (couple.getSquareA() == couple.getSquareB())
			couple = game.getAI().getNextMove();
		Square initialSquare = couple.getSquareA();
		Square destinationSquare = couple.getSquareB();

		// On suppose que notre methode a deja verifie qu'il n'y a pas de pion allie a
		// cette position, donc qu'il s'agit forcement d'un pion ennemi.
		if (destinationSquare.getPawn() != null) {
			opponentPawnChosen = getSquareView(initialSquare).getPawnView();
			setAIPawnVisible(opponentPawnChosen);
			gridController.doFighting(initialSquare, destinationSquare);
		} else {
			handleMovementGUI(getSquareView(initialSquare).getPawnView(), getSquareView(destinationSquare));
			handleMovement(initialSquare.getPawn(), destinationSquare);
		}

		updateScore();
		turnPlayed();
		checkFinish();

	}

	// XXX Methodes liees au GameProcess

	/**
	 * Methode permettant d'indiquer qu'un tour a ete joue.
	 * 
	 * @see GameProcess#play()
	 */

	public void turnPlayed() {
		game.play();
	}

	/**
	 * Methode permettant de retourner l'etat actuel de la partie en cours.
	 * 
	 * @return L'etat de la partie
	 */

	public GameState getState() {
		return game.getGameStateManager().getState();
	}

	// XXX Evenements souris

	/**
	 * Ensemble de methodes qui permettent de gerer les evenements lies au clics de
	 * souris. Verifier la documentation de MouseEvent pour avoir plus
	 * d'informations sur le fonctionnement.
	 * 
	 * @param e Clic gauche souris
	 * @see MouseEvent
	 */

	/**
	 * Methode permettant de gerer les clics qui se font sur les images des pions
	 * pendant le placement des pions.
	 */

	private void PawnEventSettingUp(MouseEvent e) {

		PawnView pawn = (PawnView) e.getSource();
		if (PAWNS_COMPOSITION[pawn.getRank()] != 0)
			pawnChosenSettingUp = pawn;
	}

	/**
	 * Methode permettant de gerer les clics qui se font sur les pions. Il existe
	 * plusieurs cas: - checkSettingUpFinish n'est pas termine donc il s'agit d'un
	 * remplacement entre deux pions - Aucun pion n'est stocke dans pawnChosen donc
	 * cela signifie qu'aucun mouvement est en cours - Un pion est stocke et on
	 * desire seulement de selectionner un autre pion, le pion stocke sera donc
	 * ecrase - Un pion est stocke et on clique sur un pion adverse (ici un combat
	 * s'ensuit).
	 */

	private void PawnEvent(MouseEvent e) {

		// Permet de masquer le dernier pion cache
		if (opponentPawnChosen != null)
			setAIPawnHidden(opponentPawnChosen);

		PawnView pawn = (PawnView) e.getSource();

		// Condition pour qu'un pion soit manipulable.
		boolean rightPlayerCondition = (pawn.getPlayer() == 1) && (game.getTurn() == 1)
				&& ((pawn.getRank() != 11) && (pawn.getRank() != 10));
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
		// on est dans un deplacement en cours ou alors on s'apprete a changer de pion!
		else if ((pawnChosen == null) && (rightPlayerCondition) && (game.getTurn() == 1)) {
			pawnChosen = pawn;
			setHighlight(getSquare(pawnChosen.getSquareView()));
		}

		// Gestion lorsqu'on a deja clique sur un pion et qu'on change de pion
		else if ((pawnChosen != null && pawnChosen.getPlayer() == 1) && (rightPlayerCondition)) {
			resetHighlight();
			setHighlight(getSquare(pawn.getSquareView()));
			pawnChosen = pawn;
		}

		// Gestion lorsqu'on a deja clique sur un pion et qu'on clique sur un pion
		// adverse, ici un combat!
		else if ((pawnChosen != null) && (pawn.getPlayer() == 2) && (game.getTurn() == 1)
				&& (gridController.isMovePossible(getSquare(pawnChosen.getSquareView()), getSquare(pawn.getSquareView())))){

			opponentPawnChosen = pawn;
			setAIPawnVisible(opponentPawnChosen);
			gridController.doFighting(getSquare(pawnChosen.getSquareView()), getSquare(pawn.getSquareView()));
			resetHighlight();
			turnPlayed();
			updateScore();
			if (!checkFinish())
				AIturn();
			pawnChosen = null;
		}
	}

	/**
	 * Methode permettant de deplacer le pion stocke dans l'instance SquareView
	 * selectionnee. Il existe plusieurs cas: - checkSettingUp n'est pas termine,
	 * dans ce cas on place seulement le pion stocke dans pawnChosenSettingUp dans
	 * l'instance SquareView - pawnChosen n'est pas vide, dans ce cas on verifie que
	 * le mouvement est legal et dans ce cas, on place le pion dans l'instance
	 * SquareView.
	 */

	private void SquareEvent(MouseEvent e) {

		// Recupere la source du pion
		SquareView sq = (SquareView) e.getSource(); 
		// Pendant le placement des pions
		if (pawnChosenSettingUp != null)
			// Dans le cas ou le placement des pions est toujours en cours.
			if (!checkSettingUpFinish() && (sq.getRow() < 4) && (sq.getPawnView() == null)) {
				movePawnSettingUp(sq);
			}
		

		/**
		 * ON SUPPOSE LE PLACEMENT DES PIONS TERMINE A PARTIR D'ICI.
		 */

		if (pawnChosen != null && gridController.isMovePossible(getSquare(pawnChosen.getSquareView()), getSquare(sq))) {
			// Fixe les coordonnees de la case au pion.
			Pawn pawn = getSquare(pawnChosen.getSquareView()).getPawn();
			handleMovementGUI(pawnChosen, sq);
			handleMovement(pawn, getSquare(sq));
			resetHighlight();
			pawnChosen = null;
			turnPlayed();
			AIturn();
		}
	}

	// XXX Methodes pour acceder aux cases logiques et graphiques

	/**
	 * Methode permettant de retourner une instance de Square.
	 * 
	 * @param square SquareView qu'on doit convertir en Square
	 * @return Square L'instance square convertie.
	 */

	public Square getSquare(SquareView square) {
		return game.getGrid().getSquare((square.getRow()), square.getColumn());
	}

	/**
	 * Methode permettant de retourner l'instance SquareView a l'aide de
	 * coordonnees.
	 * 
	 * @param row    Represente la rangee
	 * @param column Represente la colonne
	 * @return SquareView L'instance voulue.
	 */

	public SquareView getSquareView(int row, int column) {
		return square[9 - row][column];
	}

	public SquareView getSquareView(Square square) {
		return getSquareView(square.getRow(), square.getColumn());
	}

	public GameProcess getGameProcess() {
		return game;
	}

	// XXX Accesseurs et mutateurs
	public Pane getInGamePane() {
		return inGame;
	}
}
