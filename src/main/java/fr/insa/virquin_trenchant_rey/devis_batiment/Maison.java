/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Auteurs TRENCHANT Evan & VIRQUIN Rudy
 */
public class Maison extends Batiment {
    static int id_maison = 0;

    public Maison(int id, List<Niveau> ListNiveau, Toit toit) {
        super(id, ListNiveau, toit);
    }

    //méthode creerBatiment de la classe mère Batiment héritée par la classe Maison
    public static void creerMaison() {
        //pour générer le devis, on demande les coordonnées de la personne puis on ajoute ces informations à l'objet instancié précédemment
        System.out.println("Veuillez entrer votre nom:");
        String nom_pers = Lire.S();
        System.out.println("Veuillez entrer votre prénom:");
        String prenom_pers = Lire.S();
        System.out.println("Veuillez entrer votre adresse:");
        String adresse = Lire.S();
        System.out.println("Veuillez entrer votre code postal:");
        String code_postale = Lire.S();
        System.out.println("Veuillez entrer votre ville:");
        String ville = Lire.S();
        id_maison++;
        Maison maison = new Maison(id_maison, null, null);
    }
    //ajouter méthode de récupération des coordonnées
}
