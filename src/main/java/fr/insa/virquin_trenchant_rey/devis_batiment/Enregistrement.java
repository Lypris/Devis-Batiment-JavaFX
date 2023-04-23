/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author VIRQUIN Rudy
 */
public class Enregistrement {
    private List<Coin> ListCoin;
    private List<Mur> ListMur;
    private List<Piece> ListPiece;
    private List<Niveau> ListNiveau;
    private List<Sol> ListSol;
    private List<Plafond> ListPlafond;

    public Enregistrement(List<Coin> ListCoin, List<Mur> ListMur, List<Piece> ListPiece, List<Niveau> ListNiveau) {
        this.ListCoin = ListCoin;
        this.ListMur = ListMur;
        this.ListPiece = ListPiece;
        this.ListNiveau = ListNiveau;
    }
    
    
    public void enregistrerConfigurations(String nomFichier) {
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(nomFichier+".txt", true));
            b.write("Ceci est un fichier de sauvegarde de la configuration du bâtiment.");
            b.newLine();
            //enregistrement de tous les niveaux
            b.write("Format d'écriture pour un niveau: id;hauteur");
            b.newLine();
            b.write("<Niveaux>");
            b.newLine();
            for (Niveau n : ListNiveau) {
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
            for (Coin c : ListCoin) {
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
            for (Mur m : ListMur) {
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
            for (Piece p: ListPiece) {
                b.newLine();
                b.write(p.getId() + ";" + p.getMur()[0].getId() + ";" + p.getMur()[1].getId() + ";" + p.getMur()[2].getId() + ";" + p.getMur()[3].getId() + ";" + p.getN().getId());
            }
            b.newLine();
            b.write("</Pièces>");
            b.newLine();
            //enregistrement de tous les sols et plafonds de pièces
            b.write("Format d'\u00e9criture pour un sol ou un plafond: id;id_niveau;nom_revetement");
            b.newLine();
            b.write("<Sol>");
            b.newLine();
            //enregistrement de tous les sols si la liste n'est pas vide
            if (!ListSol.isEmpty()) {
                for (Sol s: ListSol) {
                    b.write(s.getId() + ";" + s.getN().getId() + ";" + s.getR().getNom());
                    b.newLine();
                }
            }
            b.newLine();
            b.write("</Sol>");
            b.newLine();
            b.write("<Plafond>");
            b.newLine();
            //enregistrement de tous les plafonds si la liste n'est pas vide
            if (!ListPlafond.isEmpty()) {
                for (Plafond p: ListPlafond) {
                    b.write(p.getId() + ";" + p.getN().getId() + ";" + p.getR().getNom());
                    b.newLine();
                }
            }
            b.write("</Plafond>");
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
