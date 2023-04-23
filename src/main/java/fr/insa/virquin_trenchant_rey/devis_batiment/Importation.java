/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

/**
 *
 * @author VIRQUIN Rudy
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Importation {
    
    private List<Coin> ListCoin;
    private List<Mur> ListMur;
    private List<Piece> ListPiece;
    private List<Niveau> ListNiveau;

    public Importation(List<Niveau> ListNiveau, List<Coin> ListCoin, List<Mur> ListMur, List<Piece> ListPiece) {
        this.ListCoin = ListCoin;
        this.ListMur = ListMur;
        this.ListPiece = ListPiece;
        this.ListNiveau = ListNiveau;
    }
    
    public void importerConfigurations(String nomFichier) {
        try {
            BufferedReader b = new BufferedReader(new FileReader(nomFichier + ".txt"));
            String ligne;
            while ((ligne = b.readLine()) != null) {
                
                switch (ligne) {
                    case "<Niveaux>" -> {
                        //lecture et création des niveaux
                        while ((ligne = b.readLine()) != null && !ligne.equals("</Niveaux>")) {
                            String[] valeurs = ligne.split(";");
                            int id = Integer.parseInt(valeurs[0]);
                            double h = Double.parseDouble(valeurs[1]);
                            ListNiveau.add(new Niveau(id, h));
                        }
                    }
                    case "<Coins>" -> {
                        //lecture et création des coins
                        while ((ligne = b.readLine()) != null && !ligne.equals("</Coins>")) {
                            String[] valeurs = ligne.split(";");
                            double x = Double.parseDouble(valeurs[0]);
                            double y = Double.parseDouble(valeurs[1]);
                            int id = Integer.parseInt(valeurs[2]);
                            int idNiveau = Integer.parseInt(valeurs[3]);
                            ListCoin.add(new Coin(x, y, Objfromid.NiveauFromId(idNiveau), id));
                        }
                    }
                    case "<Murs>" -> {
                        //lecture et création des murs
                        while ((ligne = b.readLine()) != null && !ligne.equals("</Murs>")) {
                            String[] valeurs = ligne.split(";");
                            int idMur = Integer.parseInt(valeurs[0]);
                            int idCoinDebut  = Integer.parseInt(valeurs[1]);
                            int idCoinFin  = Integer.parseInt(valeurs[2]);
                            int idNiveau = Integer.parseInt(valeurs[3]);
                            String revetement = (valeurs[4]);
                            Coin coinDebut = Objfromid.CoinFromId(idCoinDebut);
                            Coin coinFin = Objfromid.CoinFromId(idCoinFin);
                            Mur mur = new Mur(idMur, coinDebut, coinFin, Objfromid.NiveauFromId(idNiveau));
                            if(ligne.equals("<Fenêtres>")) {
                                List<Fenetre> fenetres = new ArrayList<>();
                                while ((ligne = b.readLine()) != null && ligne.startsWith("    ") && !ligne.equals("</Fenêtres>")) {
                                    String[] valeursFenetre = ligne.trim().split(";");
                                    int id_fenetre = Integer.parseInt(valeursFenetre[0]);
                                    double largeur_fenetre = Double.parseDouble(valeursFenetre[1]);
                                    double longueur_fenetre = Double.parseDouble(valeursFenetre[2]);
                                    fenetres.add(new Fenetre(longueur_fenetre, largeur_fenetre, id_fenetre));

                                }
                                mur.setListFenetre(fenetres);
                            }

                            if(ligne.equals("<Portes>")) {
                                List<Porte> portes = new ArrayList<>();
                                while ((ligne = b.readLine()) != null && ligne.startsWith("    ") && !ligne.equals("</Portes>")) {
                                    String[] valeursPorte = ligne.trim().split(";");
                                    int id_porte = Integer.parseInt(valeursPorte[0]);
                                    double largeur_porte = Double.parseDouble(valeursPorte[1]);
                                    double longueur_porte = Double.parseDouble(valeursPorte[2]);
                                    portes.add(new Porte(longueur_porte, largeur_porte, id_porte));
                                }
                                mur.setListPorte(portes);
                            }

                            Revetement R = null;
                            if (revetement != null){ //si un nom de revêtement est détecté on le créer à partir de la liste
                                R = Revetement.RevetementfromNom (revetement);
                                mur.setR(R);
                            }
                            ListMur.add(mur);
                        }
                    }
                    case "<Pièces>" -> {
                        while ((ligne = b.readLine()) != null && !ligne.equals("</Pièces>")) {
                            String[] valeurs = ligne.split(" ");
                            int id = Integer.parseInt(valeurs[0]);
                            int idMur1 = Integer.parseInt(valeurs[1]);
                            int idMur2 = Integer.parseInt(valeurs[2]);
                            int idMur3 = Integer.parseInt(valeurs[3]);
                            int idMur4 = Integer.parseInt(valeurs[4]);
                            int idNiveau = Integer.parseInt(valeurs[5]);
                            //on récupère les murs à partir de leur id
                            Mur [] mur = {Objfromid.MurFromId(idMur1), Objfromid.MurFromId(idMur2), Objfromid.MurFromId(idMur3), Objfromid.MurFromId(idMur4)};
                            //création d'un Set contenant les coins des murs
                            Set<Coin> SetCoin = new HashSet<>();
                            SetCoin.clear();
                            for(int i=0; i<4; i++){
                                SetCoin.add(mur[i].getDebut());
                                SetCoin.add(mur[i].getFin());
                            }
                            Coin[] coin = SetCoin.toArray(new Coin[4]); //conversion du set en tableau
                            Piece piece = new Piece(mur, coin, Objfromid.NiveauFromId(idNiveau), id);
                            ListPiece.add(piece);
                        }
                    }
                    default -> {
                    }
                }
                            }
            b.close();
            System.out.println("Fichier " + nomFichier + ".txt importé avec succès !");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'importation.");
        }
    }
}

