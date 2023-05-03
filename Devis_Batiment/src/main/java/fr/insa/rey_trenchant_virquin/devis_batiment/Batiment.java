package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rudyv
 */
public abstract class Batiment {
    public int id;
    public Toit toit;
    public static List<Niveau> ListNiveau = new ArrayList<>();
    public static List<Coin> ListCoin = new ArrayList<>();
    public static List<Mur> ListMur = new ArrayList<>();
    public static List<Piece> ListPiece = new ArrayList<>();
    public static List<Sol> ListSol = new ArrayList<>();
    public static List<Plafond> ListPlafond = new ArrayList<>();
    public String nom, type;
    public Batiment(int id, String nom, List<Niveau> ListNiveau, Toit toit) {
        this.id = id;
        this.nom = nom;
        this.ListNiveau= ListNiveau;
        this.toit = toit;
    }
    //méthode pour savoir si le batiment est un immeuble ou une maison
    public String getType() {
        if (this instanceof Immeuble) {
            type = "Immeuble";
        } else if (this instanceof Maison) {
            type = "Maison";
        }
        return type;
    }
    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        String result = "Type de bâtiment : " + this.getClass().getSimpleName() + "\n";
        // Ajouter d'autres informations spécifiques au bâtiment
        return result;
    }

}
