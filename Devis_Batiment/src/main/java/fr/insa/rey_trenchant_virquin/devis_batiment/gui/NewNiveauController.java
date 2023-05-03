package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewNiveauController implements Initializable {
    @FXML
    private Button ButtonCreateLevel;
    @FXML
    private TextField FieldHauteur;
    private double hauteur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valideButton();
    }
    // Méthode qui permet de valider le bouton: il est désactivé tant que la hauteur du niveau n'est pas renseignée
    private void valideButton() {
        ButtonCreateLevel.setDisable(true);
        //on vérifie que la hauteur du niveau est renseignée et correspond à un nombre entier ou un nombre décimal positif
        while (FieldHauteur.getText().isEmpty() || FieldHauteur.getText().equals("0") || FieldHauteur.getText().matches("[0-9]+") || FieldHauteur.getText().matches("[0-9]+.[0-9]+")) {
            ButtonCreateLevel.setDisable(true);
        }
        ButtonCreateLevel.setOnAction(event -> {
            System.out.println("Niveau créé");
            // Récupérer la hauteur saisie par l'utilisateur
            double hauteur = Double.parseDouble(FieldHauteur.getText());
            // Fermer la fenêtre
            FieldHauteur.getScene().getWindow().hide();
        });
    }
}
