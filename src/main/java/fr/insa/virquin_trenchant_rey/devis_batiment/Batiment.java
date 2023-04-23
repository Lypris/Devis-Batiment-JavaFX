/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author rudyv
 */
public abstract class Batiment {
    private int id;
    private Toit toit;
    private static List<Niveau> ListNiveau = new ArrayList<>();

    private String nom;
    private String type;
    private String adresse;
    public Batiment(int id, List<Niveau> ListNiveau, Toit toit) {
        this.id = id;
        this.ListNiveau= ListNiveau;
        this.toit = toit;
    }
    public static void creerBatiment() {
        System.out.println("Veuillez entrer le type du bâtiment (immeuble ou maison):");
        String type = Lire.S();
        switch (type) {
            case "immeuble" -> {
                Immeuble.creerImmeuble();
            }
            case "maison" -> {
                Maison.creerMaison();
            }
            default -> System.out.println("Entrée inconnue (immeuble ou maison).");
        }
    }

    @Override
    public String toString() {
        return "Batiment: " + type + ", " + nom + " , composé de " + ListNiveau + "niveaux." ;
    }
    
    
    public abstract void dessine(GraphicsContext context){
        for()
    }
}
