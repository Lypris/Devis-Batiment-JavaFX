package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rudyv
 */
import java.util.List;

public class Devis {
    private String titre;
    private Batiment batiment;
    private List<Revetement> revetements;

    public Devis(String titre, Batiment batiment, List<Revetement> revetements) {
        this.titre = titre;
        this.batiment = batiment;
        this.revetements = revetements;
    }

    //méthode de calcul des prix : on récupère chaque pièce d'un appartement, on récupère chaque revêtement, on calcule le prix pour chaque revêtement et on affiche le résultat
    public void calculPrixAppartement() {
        for (Piece piece : Appartement.getListpiece()) {
            System.out.println("Prix pour la pièce " + piece.getNom() + " :");
            for (Revetement revetement : this.revetements) {
                System.out.println(revetement.getNom() + " : " + "Surface totale" + " : " + Revetement.calculSurfaceRevetement(revetement) +" m²" +", Coût total:" + revetement.calculPrixRevetement(revetement) + " euros");
            }
        }
    }

    //méthode du devis pour un bâtiment
   /* public void calculPrixBatiment() {
        double prix_total = 0;
        //on génère un fichier excel avec la librairie GemBox.Spreadsheet
        ExcelFile excelFile = new ExcelFile();
        excelFile.createFile();
        excelFile.addSheet("fr.insa.rey_trenchant_virquin.devis_batiment.Devis");
        excelFile.addHeader("fr.insa.rey_trenchant_virquin.devis_batiment.Devis pour le bâtiment " + this.batiment.getNom());
        excelFile.addHeader("Informations sur le bâtiment :");
        excelFile.addHeader("Nom du bâtiment : " + this.batiment.getNom());
        excelFile.addHeader("Adresse : " + this.batiment.getAdresse());

        //Liste des appartements, affichés avec leurs informations, puis on affiche chaque pièce avec son nom
        // Chaque pièce a sa liste de revêtements utilisés sur les murs, sols et plafonds, avec la surface totale de chaque revêtement dans la pièce, et le coût correspondant
        excelFile.addHeader("Liste des appartements :");
        for (fr.insa.rey_trenchant_virquin.devis_batiment.Appartement fr.insa.rey_trenchant_virquin.devis_batiment.Appartement : this.batiment.getListAppartement()) {
            excelFile.addHeader("fr.insa.rey_trenchant_virquin.devis_batiment.Appartement n°" + fr.insa.rey_trenchant_virquin.devis_batiment.Appartement.getId());
            excelFile.addHeader("Informations sur l'appartement :");
            excelFile.addHeader("Nombre de pièces : " + fr.insa.rey_trenchant_virquin.devis_batiment.Appartement.getListpiece().size());
            excelFile.addHeader("Liste des pièces :");
            for (fr.insa.rey_trenchant_virquin.devis_batiment.Piece piece : fr.insa.rey_trenchant_virquin.devis_batiment.Appartement.getListpiece()) {
                excelFile.addHeader("Pièce n°" + piece.getId() + " : " + piece.getNom());
                excelFile.addHeader("Liste des revêtements utilisés :");
                for (fr.insa.rey_trenchant_virquin.devis_batiment.Revetement revetement : this.revetements) {
                    excelFile.addHeader("Revêtement : " + revetement.getNom());
                    excelFile.addHeader("Surface totale : " + fr.insa.rey_trenchant_virquin.devis_batiment.Revetement.calculSurfaceRevetement(revetement) + " m²");
                    excelFile.addHeader("Coût total : " + revetement.calculPrixRevetement(revetement) + " euros");
                    prix_total += revetement.calculPrixRevetement(revetement);
                }
            }
        }
    }

    */
}
