package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author evant
 */
public abstract class Ouverture {
    private double largeur;
    private double longueur;
    private int id;
    
    public Ouverture(double longueur, double largeur, int id){ // position renvoie la distance entre le coin du mur et le point
        this.longueur=longueur;
        this.largeur = largeur;  
        this.id=id;
    }
    
    public double surface(){
        return largeur*longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getLongueur() {
        return longueur;
    }

    
    
    public int getId() {
        return id;
    }
    
    public static void creerOuverture(){
        System.out.println("Quelle ouverture voulez vous créer ? (porte, fenetre, tremie)");
        String n=Lire.S();
        switch (n) {
            case "porte" -> {
                Porte.creerPorte();
            }
            case "fenetre" -> {
                Fenetre.creerFenetre();
            }
            case "tremie" -> {
                Tremi.creerTremi();
            }
            default -> System.out.println("Entrée inconnue (porte, fenêtre ou tremie).");
        }
    }

    @Override
    public String toString() {
        return "fr.insa.rey_trenchant_virquin.devis_batiment.Ouverture{" + "largeur=" + largeur + ", longueur=" + longueur + ", id=" + id + '}';
    }

    //méthode pour dessiner l'ouverture
    public abstract void dessine(GraphicsContext context);
    
}
