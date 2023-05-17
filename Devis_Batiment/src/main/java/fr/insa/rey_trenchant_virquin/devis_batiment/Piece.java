/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.rey_trenchant_virquin.devis_batiment;

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author evant
 */
public class Piece {
    private int id;
    private Mur [] Mur = new Mur [4];
    private Coin [] Coin = new Coin [4];
    private Niveau n;
    private String nom;
    public static int idpiece=0;

    public Piece(Mur [] Mur, Coin [] Coin, Niveau niv, int id){
        this.id = id;
        this.Mur = Mur;
        this.Coin = Coin;
        this.n = niv;

    }

    public double surface(){

        double largeur = Math.min(Math.min(Mur[0].longueur(), Mur[1].longueur()),Mur[2].longueur());
        double longueur = Math.max(Math.max(Mur[0].longueur(), Mur[1].longueur()),Mur[2].longueur());
        return largeur*longueur;
    }

    public Coin[] getCoin() {
        return Coin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Piece{" + "id=" + id + ", I=" + Mur[0].getId() + ", II=" + Mur[1].getId() + ", III=" + Mur[2].getId() + ", IV=" + Mur[3].getId() + ", c1=" + Coin[0].getId() + ", c2=" + Coin[1].getId() + ", c3=" + Coin[2].getId() + ", c4=" + Coin[3].getId() + ", Surface du sol à couvrir : " + surface() + "m²" + '}';
    }

    public int getId() {
        return id;
    }

    public Mur[] getMur() {
        return Mur;
    }

    public Niveau getN() {
        return n;
    }

    public static Piece creerPieceDepuisMur(int I, int II, int III, int IV) {

        System.out.println(HelloApplication.ListMur);
        if(Verification.possiblePiece(I, II, III, IV) != false){
            Mur [] mur = {Objfromid.MurFromId(I), Objfromid.MurFromId(II), Objfromid.MurFromId(III), Objfromid.MurFromId(IV)};
            //création d'un Set contenant les coins des murs
            Set<Coin> SetCoin = new HashSet<>();
            SetCoin.clear();
            for(int i=0; i<4; i++){
                SetCoin.add(mur[i].getDebut());
                SetCoin.add(mur[i].getFin());
            }
            Coin[] coin = SetCoin.toArray(new Coin[4]); //conversion du set en tableau
            idpiece++;
            Piece p = new Piece (mur, coin, HelloApplication.ListNiveau.get(HelloApplication.niv_actu - 1), idpiece);
            Sol sol = new Sol (mur, coin, HelloApplication.ListNiveau.get(HelloApplication.niv_actu - 1), idpiece, null, null);
            Plafond plafond = new Plafond (mur, coin, HelloApplication.ListNiveau.get(HelloApplication.niv_actu - 1), idpiece, null, null);
            HelloApplication.ListPiece.add(p);
            HelloApplication.ListSol.add(sol);
            HelloApplication.ListPlafond.add(plafond);
            //ajout de la piece dans la liste des pièces du niveau.
            HelloApplication.ListNiveau.get(HelloApplication.niv_actu - 1).addPiece(p);
            //système de renommage automatique
            p.setNom("Piece " + p.getId());
            System.out.println(p);
            return p;
        }else{
            System.out.println("Votre pièce ne peut pas exister.");
            return null;
        }
    }

    public void dessine(GraphicsContext gc, double zoomLevel, Translate translate) {
        Rectangle rectangle = new Rectangle();
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        for (Coin corner : Coin) {
            double x = corner.getX();
            double y = corner.getY();
            if (x < minX) {
                minX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y > maxY) {
                maxY = y;
            }
        }
        rectangle.setX(minX * zoomLevel + translate.getX());
        rectangle.setY(minY * zoomLevel + translate.getY());
        rectangle.setWidth((maxX - minX) * zoomLevel);
        rectangle.setHeight((maxY - minY) * zoomLevel);
        gc.setFill(Color.rgb(243, 128, 128, 0.5)); // Pink color with opacity 50%
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 12));
        Text text = new Text(getNom());
        text.setFont(gc.getFont());
        Bounds bounds = text.getBoundsInLocal();
        double textX = rectangle.getX() + rectangle.getWidth() / 2;
        double textY = rectangle.getY() + rectangle.getHeight() / 2 + bounds.getHeight() / 2;
        gc.fillText(getNom(), textX, textY);
    }
}
