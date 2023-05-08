package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.Gestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewNiveauController implements Initializable {
    @FXML
    private Button ButtonCreateLevel, ButtonCancel;
    @FXML
    private TextField FieldHauteur;
    private static double hauteur;
    private TabPane tabPane;

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        valideButton();
        ButtonCancel.setOnAction(event -> {
            System.out.println("Annulation de la création du niveau");
            // Fermer la fenêtre
            Stage stage = (Stage) ButtonCancel.getScene().getWindow();
            stage.close();
            // Si l'utilisateur annule la création du niveau, on supprime le tab
            tabPane.getTabs().remove(tabPane.getTabs().size()-1);
            //Si l'utilisateur annule la création du niveau, on supprime le niveau
            Gestion.ListNiveau.remove(Gestion.ListNiveau.size()-1);
            //Si l'utilisateur annule la modification de la hauteur, on remet l'ancienne hauteur
            Gestion.ListNiveau.get(Gestion.ListNiveau.size()-1).setH(Gestion.ListNiveau.get(Gestion.ListNiveau.size()-1).getH());
        });
    }

    // Méthode qui permet de valider le bouton : il est désactivé tant que la hauteur du niveau n'est pas renseignée
    public void valideButton() {
        ButtonCreateLevel.setDisable(true);
        FieldHauteur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || !newValue.matches("\\d+(\\.\\d+)?")) {
                ButtonCreateLevel.setDisable(true);
            } else {
                ButtonCreateLevel.setDisable(false);
            }
        });
        ButtonCreateLevel.setOnAction(event -> {
            System.out.println("Niveau créé");
            // Récupérer la hauteur saisie par l'utilisateur
            hauteur = Double.parseDouble(FieldHauteur.getText());
            // Fermer la fenêtre
            FieldHauteur.getScene().getWindow().hide();
        });
    }

    public static double getHauteur() {
        return hauteur;
    }
}
