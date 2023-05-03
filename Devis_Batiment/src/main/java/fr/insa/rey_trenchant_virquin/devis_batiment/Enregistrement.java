package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author VIRQUIN Rudy
 */
public class Enregistrement {

    private Batiment Batiment;

    public Enregistrement(Batiment batiment) {
        this.Batiment = batiment;
    }
    
    
    public void enregistrerConfigurations(String nomFichier) {
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(nomFichier+".txt", true));
            b.write("Ceci est un fichier de sauvegarde de la configuration du bâtiment:");
            b.newLine();
            b.write(Batiment.getNom());
            b.newLine();
            //enregistrement de tous les niveaux
            b.write("Format d'écriture pour un niveau: id;hauteur");
            b.newLine();
            b.write("<Niveaux>");
            b.newLine();
            for (Niveau n : Batiment.ListNiveau) {
                b.write(n.getId() + ";" + n.getH());
                b.newLine();
            }
            b.write("</Niveaux>");
            b.newLine();
            //enregistrement de tous les coins
            b.write("Format d'écriture pour un coin: abscisse;ordonnée;id;id_niveau");
            b.newLine();
            b.write("<Coins>");
            b.newLine();
            for (Coin c : Batiment.ListCoin) {
                b.write(c.getX() + ";" + c.getY() + ";" + c.getId() + ";" + c.getNiv().getId());
                b.newLine();
            }
            b.write("</Coins>");
            b.newLine();
            //enregistrement de tous les murs
            b.write("""
                    Format d'\u00e9criture pour un mur: id_mur;id_coin_debut;id_coin_fin;id_niveau;nom_revetement
                        id_ouverture;largeur_ouverture;longueur_ouverture""");
            b.newLine();
            b.write("<Murs>");
            b.newLine();
            for (Mur m : Batiment.ListMur) {
                if (m.getR() != null) { //si le mur a un revêtement, on l'enregistre
                    String nomRevetement = m.getR().getNom();
                     b.write(m.getId() + ";" + m.getDebut().getId() + ";" + m.getFin().getId() + ";" + m.getNiv().getId() + ";" + nomRevetement);
                } else {
                    b.write(m.getId() + ";" + m.getDebut().getId() + ";" + m.getFin().getId() + ";" + m.getNiv().getId());
                }
                b.newLine();
                b.write("<Fenêtres>");
                for (Fenetre fen : m.getListFenetre()){
                        b.newLine();
                        b.write("    ");
                        b.write(fen.getId() + ";" + fen.getLargeur() + ";" + fen.getLongueur());
                }
                b.newLine();
                b.write("</Fenêtres>");
                b.newLine();
                b.write("<Portes>");
                for (Porte porte : m.getListPorte()){
                    b.newLine();
                    b.write("    ");
                    b.write(porte.getId() + ";" + porte.getLargeur() + ";" + porte.getLongueur());
                }
                b.newLine();
                b.write("</Portes>");
            }
            b.newLine();
            b.write("</Murs>");
            b.newLine();
            //enregistrement de toutes les pièces
            b.write("Format d'\u00e9criture pour une pièce: id_pièce;id_mur_1;id_mur_2;id_mur_3;id_mur_4;id_niveau");
            b.newLine();
            b.write("<Pièces>");
            b.newLine();
            for (Piece p: Batiment.ListPiece) {
                b.newLine();
                b.write(p.getId() + ";" + p.getMur()[0].getId() + ";" + p.getMur()[1].getId() + ";" + p.getMur()[2].getId() + ";" + p.getMur()[3].getId() + ";" + p.getN().getId());
            }
            b.newLine();
            b.write("</Pièces>");
            b.newLine();
            //enregistrement de tous les sols et plafonds de pièces
            b.write("Format d'\u00e9criture pour un sol ou un plafond: id;id_niveau;nom_revetement");
            b.newLine();
            b.write("<fr.insa.rey_trenchant_virquin.devis_batiment.Sol>");
            b.newLine();
            //enregistrement de tous les sols si la liste n'est pas vide
            if (!Batiment.ListSol.isEmpty()) {
                for (Sol s: Batiment.ListSol) {
                    b.write(s.getId() + ";" + s.getN().getId() + ";" + s.getR().getNom());
                    b.newLine();
                }
            }
            b.newLine();
            b.write("</fr.insa.rey_trenchant_virquin.devis_batiment.Sol>");
            b.newLine();
            b.write("<fr.insa.rey_trenchant_virquin.devis_batiment.Plafond>");
            b.newLine();
            //enregistrement de tous les plafonds si la liste n'est pas vide
            if (!Batiment.ListPlafond.isEmpty()) {
                for (Plafond p: Batiment.ListPlafond) {
                    b.write(p.getId() + ";" + p.getN().getId() + ";" + p.getR().getNom());
                    b.newLine();
                }
            }
            b.write("</fr.insa.rey_trenchant_virquin.devis_batiment.Plafond>");
            b.newLine();
            b.write("Fin du fichier.");
            b.newLine();
            b.close();
            System.out.println("Configuration sauvegard\u00e9e avec succès !");
        } 
        catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement des configurations : " + e.getMessage());
        }
    }
}    