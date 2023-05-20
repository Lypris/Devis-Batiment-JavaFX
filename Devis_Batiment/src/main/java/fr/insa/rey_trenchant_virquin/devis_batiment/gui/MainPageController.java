package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.Enregistrement;
import fr.insa.rey_trenchant_virquin.devis_batiment.Objfromid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;
import java.net.URL;

public class MainPageController implements Initializable {
    @FXML
    private VBox mainVBox;
    @FXML
    private HBox HBoxEnd;
    @FXML
    private SplitPane splitPane;
    @FXML
    private ImageView toggleButtonImage;
    @FXML
    private MenuItem SaveAs;
    private double screenHeight, screenWidth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obtenez la hauteur et la largeur de l'écran de l'utilisateur
        screenHeight = javafx.stage.Screen.getPrimary().getVisualBounds().getHeight();
        screenWidth = javafx.stage.Screen.getPrimary().getVisualBounds().getWidth();

        // Dimensions du SplitPane
        double splitPaneHeight = screenHeight * 0.8;
        double splitPaneWidth = screenWidth;
        double splitPaneDividerPosition = 0.175;

        // Dimensions de la HBox finale
        double endBoxHeight = screenHeight * 0.03;
        double endBoxWidth = screenWidth;
        // Dimensions de la HBox des boutons


        splitPane.setPrefHeight(splitPaneHeight);
        splitPane.setPrefWidth(splitPaneWidth);
        splitPane.setDividerPositions(splitPaneDividerPosition);

        HBoxEnd.setPrefHeight(endBoxHeight);
        HBoxEnd.setPrefWidth(endBoxWidth);

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
        //On propose un nom de fichier par défaut
        fileChooser.setInitialFileName(HelloApplication.bâtiment.getNom());
        // Afficher la fenêtre de dialogue
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            // Create an instance of MyClass
            Enregistrement save = new Enregistrement();
            // Call the enregistrerConfigurations method and pass in the filename and filepath
            save.enregistrerConfigurations(selectedFile.getName(), selectedFile.getParent());
            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enregistrer sous");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier a été enregistré avec succès");
            alert.showAndWait();
        }
    }
    // Ajouter une méthode getter pour screenHeight
    public double getScreenHeight() {
        return screenHeight;
    }
    public double getScreenWidth(){
        return screenHeight;
    }


}

