package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;
import java.net.URL;
import javafx.stage.Screen;

public class MainPageController implements Initializable {
    @FXML
    private VBox mainVBox;
    @FXML
    private HBox HBoxEnd, HBoxButtons;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Separator SeparatorControls;
    @FXML
    private ToggleButton TransparenceButton;
    @FXML
    private ImageView toggleButtonImage;
    @FXML
    private Label l1, l2;
    @FXML
    private MenuItem SaveAs;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obtenez la hauteur et la largeur de l'écran de l'utilisateur
        double screenHeight = javafx.stage.Screen.getPrimary().getVisualBounds().getHeight();
        double screenWidth = javafx.stage.Screen.getPrimary().getVisualBounds().getWidth();

        // Dimensions de la HBox des boutons
        double buttonBoxHeight = screenHeight * 0.05;
        double buttonBoxWidth = screenWidth;

        // Dimensions du SplitPane
        double splitPaneHeight = screenHeight * 0.8;
        double splitPaneWidth = screenWidth;
        double splitPaneDividerPosition = 0.175;

        // Dimensions de la HBox finale
        double endBoxHeight = screenHeight * 0.03;
        double endBoxWidth = screenWidth;

        // Appliquez les dimensions aux éléments correspondants
        HBoxButtons.setPrefHeight(buttonBoxHeight);
        HBoxButtons.setPrefWidth(buttonBoxWidth);
        l1.setPrefHeight(buttonBoxHeight*0.6);
        l2.setPrefHeight(buttonBoxHeight*0.6);

        splitPane.setPrefHeight(splitPaneHeight);
        splitPane.setPrefWidth(splitPaneWidth);
        splitPane.setDividerPositions(splitPaneDividerPosition);

        HBoxEnd.setPrefHeight(endBoxHeight);
        HBoxEnd.setPrefWidth(endBoxWidth);

        //Appliquer un ratio de 0.8 pour la hauteur des éléments dans la HBox des boutons
        l1.setPrefHeight(buttonBoxHeight*0.8);
        l2.setPrefHeight(buttonBoxHeight*0.8);
        SeparatorControls.setMaxHeight(buttonBoxHeight*0.8);
        SeparatorControls.setValignment(javafx.geometry.VPos.CENTER);
        //Appliquer une marge de 0.05 entre les éléments de la HBox des boutons
        HBoxButtons.setSpacing(buttonBoxHeight*0.075);

        SaveAs.setOnAction(event -> {
            saveAs();
        });
    }
    public void selectItem(){

    }
    // méthode pour enregistrer le fichier en tant que
    public void saveAs(){
        // Créer un objet de type FileChooser
        FileChooser fileChooser = new FileChooser();
        // Définir le titre de la fenêtre
        fileChooser.setTitle("Enregistrer sous");
        // Définir l'extension du fichier
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier texte", "*.txt")
        );
        // Afficher la fenêtre de dialogue
        fileChooser.showSaveDialog(null);
    }

}

