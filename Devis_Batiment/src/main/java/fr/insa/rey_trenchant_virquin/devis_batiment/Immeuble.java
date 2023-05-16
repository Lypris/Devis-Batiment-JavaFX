package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Auteurs TRENCHANT Evan & VIRQUIN Rudy
 */
public class Immeuble extends Batiment {
    static int id_immeuble = 0;
    protected static List<Appartement> ListAppartements = new ArrayList<>();

    public Immeuble(int id, String nom, List<Niveau> ListNiveau, List<Appartement> ListAppartements, Toit toit){
        super(id, nom, ListNiveau, toit);
        this.ListAppartements = ListAppartements;
    }

    public static Immeuble creerImmeuble(String nom_immeuble, String nom_personne, String prenom_personne, String adresse, String code_postale, String ville){
        //pour générer le devis, on demande les coordonnées de la personne dans StartPageController
        id_immeuble++;
        Immeuble immeuble = new Immeuble(id_immeuble, nom_immeuble, HelloApplication.ListNiveau, HelloApplication.ListAppartement, HelloApplication.toit);
        System.out.println(immeuble); //à supprimer
        return immeuble;
    }
}
