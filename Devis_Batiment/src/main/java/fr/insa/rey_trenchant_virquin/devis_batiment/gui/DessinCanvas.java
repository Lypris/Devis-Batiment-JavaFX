package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.Coin;
import fr.insa.rey_trenchant_virquin.devis_batiment.Gestion;
import fr.insa.rey_trenchant_virquin.devis_batiment.Objfromid;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DessinCanvas extends Canvas {
    public DessinCanvas(double width, double height) {
        super(width, height);
    }
    //Méthode qui dessine la grille sur le canvas
    public void drawGrid(){
        this.getGraphicsContext2D().clearRect(0,0,this.getWidth(),this.getHeight());
        //on dessine les lignes et les colonnes avec une couleur noire d'opacité 0.8
        this.getGraphicsContext2D().setStroke(javafx.scene.paint.Color.rgb(0,0,0,0.8));
        //on dessine les lignes
        for(int i=0;i<this.getWidth();i+=25){
            this.getGraphicsContext2D().strokeLine(i,0,i,this.getHeight());
        }
        //on dessine les colonnes
        for(int i=0;i<this.getHeight();i+=25){
            this.getGraphicsContext2D().strokeLine(0,i,this.getWidth(),i);
        }
    }
    //méthode qui écoute les clics de la souris sur le canvas et appelle la méthode de création de coin si le canvas est cliqué
    public void listenMouseClick(){
        this.setOnMouseClicked(event -> {
            //on récupère les coordonnées du clic
            double x = event.getX();
            double y = event.getY();
            //on vérifie si le clic est dans le canvas et si le bouton de création de coin est activé
            if(x>=0 && x<=this.getWidth() && y>=0 && y<=this.getHeight() && MainPageController.create_coin == true){
                //on crée le coin
                Coin c = Coin.creerCoin(x,y);
                if(c != null) {
                    c.dessine(this.getGraphicsContext2D());
                }
            }
        });
    }

}
