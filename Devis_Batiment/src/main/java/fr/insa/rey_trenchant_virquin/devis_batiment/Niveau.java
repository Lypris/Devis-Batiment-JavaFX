package fr.insa.rey_trenchant_virquin.devis_batiment;

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;
import fr.insa.rey_trenchant_virquin.devis_batiment.gui.NewNiveauController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Niveau {
    private int id;
    private static List<Piece> Listpiece = new ArrayList<>();
    private double h; // hauteur sous le plafond
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    public Niveau(int id, List<Piece> Listpiece, double h){
        this.id=id;
        this.Listpiece=Listpiece;
        this.h=h;
    }
    public Niveau(int id, double h){
        this.id=id;
        this.h=h;
    }
    public Niveau(int id) {
        this.id = id;
    } //constructeur utilisé par TabNavigation
    public List<Piece> getPiece() {
        return Listpiece;
    }
    public double getH() {
        return h;
    }
    public void setH(double h) {
        this.h = h;
    }
    public int getId() {
        return id;
    }
    public static void setListpiece(List<Piece> Listpiece) {
        Niveau.Listpiece = Listpiece;
    }
    public void addPiece(Piece p){
        this.Listpiece.add(p);
    }
    public void removePiece(Piece p){
        this.Listpiece.remove(p);
    }
    public void setId(int id) {
        this.id = id;
    }
    public void changeId(int newId) {
        this.id = newId;
    }
    @Override
    public String toString() {
        return "Niveau{" + "id= " + id + ", h= " + h + '}';
    }

    //méthode qui permet de créer un nouveau niveau en récupérant la hauteur saisie par l'utilisateur dans la fenêtre de création d'un niveau
    public static Niveau creerNiveau(int id_niv) {
        if (Objfromid.NiveauFromId(id_niv) == null) {
            try {
                // on ouvre la fenêtre pour saisir la hauteur du nouveau niveau
                openNiveauPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // on attend que l'utilisateur ait saisi une hauteur et ait cliqué sur le bouton de création
            while (NewNiveauController.getHauteur() == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // on récupère la hauteur saisie
            double h = NewNiveauController.getHauteur();
            List<Piece> ListPieces = new ArrayList<>();
            Niveau niv = new Niveau(id_niv, ListPieces, h);
            Gestion.ListNiveau.add(niv);
            System.out.println(niv);
            return niv;
        } else {
            System.out.println("Ce niveau existe déjà : vous travaillez maintenant sur le niveau : " + id_niv);
            return Objfromid.NiveauFromId(id_niv);
        }
    }
    //méthode qui permet d'ouvrir la fenêtre de création d'un niveau
    public static void openNiveauPage() throws IOException {
        //on récupère la taille de l'écran de l'utilisateur
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //on récupère le stage
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("new-niveau-page.fxml"));
        stage = new Stage();
        scene = new Scene(root, primaryScreenBounds.getWidth()*0.3, primaryScreenBounds.getHeight()*0.4);
        scene.getStylesheets().add(HelloApplication.class.getResource("new-niveau-page-style.CSS").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Choix de la hauteur du niveau");
        stage.showAndWait();
    }

}
