package fr.insa.rey_trenchant_virquin.devis_batiment.gui;


import fr.insa.rey_trenchant_virquin.devis_batiment.Objfromid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ButtonPage {
    @FXML
    private HBox HBoxButtons;
    @FXML
    protected Button CoinButton, CursorButton, MurButton, PorteButton, PieceButton, FenetreButton, TremieButton;
    @FXML
    private Separator SeparatorControls;
    @FXML
    private Label l1, l2;
    @FXML
    private ToggleButton TransparenceButton;
    static boolean create_coin = false;
    static boolean create_mur = false;
    boolean create_porte = false;
    static boolean create_piece = false;
    boolean create_fenetre = false;
    boolean create_tremie = false;
    @FXML
    private TabPane tabPane;
    public ButtonPage(TabPane tabPane) {
        this.tabPane = tabPane;
    }
    public ButtonPage() {
        // Constructeur par défaut
    }
    MainPageController mainPageController = new MainPageController();
    double screenHeight = mainPageController.getScreenHeight();
    double screenWidth = mainPageController.getScreenWidth();
    public void initialize() throws IOException {
        double buttonBoxHeight = screenHeight * 0.05;
        double buttonBoxWidth = screenWidth;
        // Appliquez les dimensions aux éléments correspondants
        HBoxButtons.setPrefHeight(buttonBoxHeight);
        HBoxButtons.setPrefWidth(buttonBoxWidth);
        l1.setPrefHeight(buttonBoxHeight*0.6);
        l2.setPrefHeight(buttonBoxHeight*0.6);
        //Appliquer un ratio de 0.8 pour la hauteur des éléments dans la HBox des boutons
        l1.setPrefHeight(buttonBoxHeight*0.8);
        l2.setPrefHeight(buttonBoxHeight*0.8);
        SeparatorControls.setMaxHeight(buttonBoxHeight*0.8);
        SeparatorControls.setValignment(javafx.geometry.VPos.CENTER);
        //Appliquer une marge de 0.05 entre les éléments de la HBox des boutons
        HBoxButtons.setSpacing(buttonBoxHeight*0.075);


        CoinButton.setOnAction(event -> {
            handleCoinButtonAction(event);
        });

        // Reste du code...
        //action lors d'un clic sur le bouton mur : on change le curseur de la souris
        MurButton.setOnAction(event -> {
            Scene scene = MurButton.getScene();
            //on récupère l'onglet sélectionné
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            //on récupère le canvas de l'onglet sélectionné
            DessinCanvas canvas = (DessinCanvas) selectedTab.getContent();
            canvas.setOnMouseEntered(event1 -> {
                scene.setCursor(Cursor.HAND);
                create_mur = true;
            });
            canvas.setOnMouseExited(event1 -> {
                scene.setCursor(javafx.scene.Cursor.DEFAULT);
                create_mur = false;
            });
        });
        //action lors d'un clic sur le bouton piece : on change le curseur de la souris
        PieceButton.setOnAction(event -> {
            Scene scene = PieceButton.getScene();
            //on récupère l'onglet sélectionné
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            //on récupère le canvas de l'onglet sélectionné
            DessinCanvas canvas = (DessinCanvas) selectedTab.getContent();
            canvas.setOnMouseEntered(event1 -> {
                scene.setCursor(Cursor.HAND);
                create_piece = true;
            });
            canvas.setOnMouseExited(event1 -> {
                scene.setCursor(javafx.scene.Cursor.DEFAULT);
                create_piece = false;
            });
        });
        //action lors d'un clic sur le bouton curseur : on change le curseur de la souris
        CursorButton.setOnAction(event -> {
            Scene scene = CursorButton.getScene();
            scene.setCursor(javafx.scene.Cursor.DEFAULT);
            create_coin = false;
            create_mur = false;
            create_piece = false;
            //on vide la liste des objets sélectionnés
            DessinCanvas.selectedCorners.clear();
            DessinCanvas.selectedMurs.clear();
            //TODO: redessiner les objets déselectionnés
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            //on récupère le canvas de l'onglet sélectionné
            DessinCanvas canvas = (DessinCanvas) selectedTab.getContent();
            canvas.redrawAll(Objfromid.NiveauFromId(HelloApplication.niv_actu));
        });

    }

    public void handleCoinButtonAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        Scene scene = button.getScene();
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            DessinCanvas canvas = (DessinCanvas) selectedTab.getContent();
            canvas.setOnMouseEntered(event1 -> {
                scene.setCursor(Cursor.CROSSHAIR);
                create_coin = true;
            });

            canvas.setOnMouseExited(event1 -> {
                scene.setCursor(Cursor.DEFAULT);
                create_coin = false;
            });
        }
    }
}