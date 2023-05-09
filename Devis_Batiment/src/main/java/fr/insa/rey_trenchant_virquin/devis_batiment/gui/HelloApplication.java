package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.Batiment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloApplication extends Application {
    public static Batiment bâtiment;
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