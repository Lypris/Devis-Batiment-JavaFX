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
    public static List<Mur> ListMur = new ArrayList<>();
    public String nom, type, nomClient, prenomClient, adresse, ville, postal;
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

    public void setType(String type) {
        this.type = type;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public static List<Niveau> getListNiveau() {
        return ListNiveau;
    }

    @Override
    public String toString() {
        String result = "Type de bâtiment : " + this.getClass().getSimpleName() + "\n";
        // Ajouter d'autres informations spécifiques au bâtiment
        return result;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getNomClient() {
        return nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville;
    }

    public String getPostal() {
        return postal;
    }
}
