package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloApplication extends Application {
    public static Batiment bâtiment;
    public static Toit toit;
    public static List<Appartement> ListAppartement = new ArrayList<>();
    public static List<Niveau> ListNiveau = new ArrayList<>();
    public static List<Coin> ListCoin = new ArrayList<>();
    public static List<Mur> ListMur = new ArrayList<>();
    public static List<Piece> ListPiece = new ArrayList<>();
    public static List<Sol> ListSol = new ArrayList<>();
    public static List<Plafond> ListPlafond = new ArrayList<>();
    public static int niv_actu=1; //id du niveau sur lequel on travaille
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(HelloApplication.class.getResource("start-page-style.CSS").toExternalForm());
            stage.setTitle("Devis Bâtiment - Start Page");
            stage.setScene(scene);
            //on empêche le redimensionnement de la fenêtre
            stage.setResizable(false);
            //on récupère la taille de l'écran de l'utilisateur
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            //on sélectionne la taille de la fenêtre en fonction de la taille de l'écran de l'utilisateur avec un ratio de 0.6
            stage.setWidth(primaryScreenBounds.getWidth()*0.6);
            stage.setHeight(primaryScreenBounds.getHeight()*0.9);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }


}