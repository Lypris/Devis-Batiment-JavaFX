package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.List;

/**
 *
 * @Auteurs TRENCHANT Evan & VIRQUIN Rudy
 */
public class Maison extends Batiment {
    static int id_maison = 0;

    public Maison(int id, String nom, List<Niveau> ListNiveau, Toit toit) {
        super(id, nom, ListNiveau, toit);
    }

    //méthode creerBatiment de la classe mère héritée
    public static void creerMaison(String nom_maison, String nom_personne, String prenom_personne, String adresse, String code_postale, String ville) {
        //pour générer le devis, on demande les coordonnées de la personne puis on ajoute ces informations à l'objet instancié précédemment
        id_maison++;
        Maison maison = new Maison(id_maison, nom_maison, null, null);
    }
}
