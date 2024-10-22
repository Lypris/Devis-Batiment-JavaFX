package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 *
 * @author evant
 */
public class Mur {
    private int id;    
    private Coin debut;
    private Coin fin;
    private Niveau niv;
    private List<Porte> ListPorte = new ArrayList<>();
    private List<Fenetre> ListFenetre = new ArrayList<>();
    private Revetement R;
    public static int idm=0;
    
    
    public Mur(Coin debut, Coin fin, Niveau niv, List<Porte> ListPorte, List<Fenetre> ListFenetre, int id, Revetement rev){
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.niv = niv;
        this.ListPorte = ListPorte;
        this.ListFenetre = ListFenetre;
        this.R = rev;
    }
    public Mur(int id, Coin debut, Coin fin, Niveau niv) { //constructeur utilisé pour l'importation
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.niv = niv;
    }
    
    public double longueur(){
        double l = Math.sqrt(Math.pow(fin.getX() - debut.getX(), 2) + Math.pow(fin.getY() - debut.getY(), 2));
        return l;
    }
    
    public double surface(){
        double surfaceOuvertures = 0;
        for (Porte por : ListPorte){
            surfaceOuvertures = surfaceOuvertures + por.surface(); 
        }
        for (Fenetre fen : ListFenetre){
            surfaceOuvertures = surfaceOuvertures + fen.surface(); 
        }
        return (longueur() * niv.getH() - surfaceOuvertures);
    }

    public int getId() {
        return id;
    }
    
    public Coin getDebut() {
        return debut;
    }

    public Coin getFin() {
        return fin;
    }     

    public Niveau getNiv() {
        return niv;
    }

    public void setNiv(Niveau niv) {
        this.niv = niv;
    }

    public Revetement getR() {
        return R;
    }

    public void setR(Revetement R) {
        this.R = R;
    }

    public List<Porte> getListPorte() {
        return ListPorte;
    }

    public void setListPorte(List<Porte> ListPorte) {
        this.ListPorte = ListPorte;
    }

    public List<Fenetre> getListFenetre() {
        return ListFenetre;
    }

    public void setListFenetre(List<Fenetre> ListFenetre) {
        this.ListFenetre = ListFenetre;
    }


    
    public static Mur creerMur(Coin c1, Coin c2){
        if(Verification.possibleMur(c1,c2)) {
            idm++;
            List<Porte> ListPorte = new ArrayList<>();
            List<Fenetre> ListFenetre = new ArrayList<>();
            Mur m = new Mur(c1, c2, Objfromid.NiveauFromId(HelloApplication.niv_actu), ListPorte, ListFenetre, idm, null);
            HelloApplication.ListMur.add(m);
            System.out.println(m);
            return m;
        }else{
            System.out.println("Impossible de créer un mur entre ces deux coins.");
            return null;
        }
    }
    
    public static void ajoutPorte(int id){ // permet d'ajouter une porte dans un mur si on crée une porte dans un autre mur
        
        for (Mur m : HelloApplication.ListMur){
            if (m.getDebut().getX() == m.getFin().getX()){ // si le mur m est vertical
                // si le mur de l'id ouverture est vertical
                if(Objfromid.MurFromPorteId(id).getDebut().getX() == Objfromid.MurFromPorteId(id).getFin().getX()){
                    //s'ils sont sur la meme colonne
                    if(Objfromid.MurFromPorteId(id).getDebut().getY() == m.getFin().getX()){
                        
                        double x1 = Objfromid.PorteFromId(id).getCoordonePorteVer(id)[0];
                        double x2 = Objfromid.PorteFromId(id).getCoordonePorteVer(id)[1];
                        //si les coordonne projetées de la porte sont sur le mur
                        if(Math.min(m.getDebut().getX(),m.getFin().getX())<=Math.min(x1,x2) && Math.max(m.getDebut().getX(),m.getFin().getX())>=Math.max(x1,x2)){
                            Porte p = new Porte(Objfromid.PorteFromId(id).getLongueur(), Objfromid.PorteFromId(id).getLargeur(), Porte.getIdporte());// creer une nouvelle porte dans la mur
                            if (!Objfromid.MurFromId(id).getListPorte().contains(p)){
                                Objfromid.MurFromPorteId(id).getListPorte().add(p);
                            }
                        }
                    }
  
                }
            }
            
        }
    }
    
    @Override
    public String toString() {
        return "Mur{" + "id=" + id + ", debut=" + debut.getId() + ", fin=" + fin.getId() + ", niveau=" + niv.getId() + ", Surface du mur à couvrir : " + surface() + "m²" + '}';
    }

    public boolean estVertical() {
        return this.debut.getX() == this.fin.getX();
    }

    public void dessine(GraphicsContext gc, double zoomLevel, Translate translate) {
        double x1 = debut.getX() * zoomLevel + translate.getX();
        double y1 = debut.getY() * zoomLevel + translate.getY();
        double x2 = fin.getX() * zoomLevel + translate.getX();
        double y2 = fin.getY() * zoomLevel + translate.getY();
        double lineWidth = 3.0;

        gc.setStroke(Color.rgb(8, 31, 45));
        gc.setLineWidth(lineWidth);
        gc.strokeLine(x1, y1, x2, y2);
    }
    public static Mur[] creer4Murs(Coin c1, Coin c2) {
        double x1 = c1.getX();
        double y1 = c1.getY();
        double x2 = c2.getX();
        double y2 = c2.getY();
        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);
        double maxX = Math.max(x1, x2);
        double maxY = Math.max(y1, y2);
        Coin c3 = Coin.creerCoin(minX, maxY);
        Coin c4 = Coin.creerCoin(maxX, minY);
        Mur m1 = Mur.creerMur(c1, c3);
        Mur m2 = Mur.creerMur(c3, c2);
        Mur m3 = Mur.creerMur(c2, c4);
        Mur m4 = Mur.creerMur(c4, c1);
        Mur[] murs = {m1, m2, m3, m4};
        return murs;
    }

}
