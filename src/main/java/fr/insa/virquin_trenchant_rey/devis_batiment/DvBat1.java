/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package fr.insa.virquin_trenchant_rey.devis_batiment;

import static fr.insa.virquin_trenchant_rey.devis_batiment.Batiment.creerBatiment;
import static fr.insa.virquin_trenchant_rey.devis_batiment.Ouverture.creerOuverture;
import static fr.insa.virquin_trenchant_rey.devis_batiment.Appartement.creerAppartement;
import static fr.insa.virquin_trenchant_rey.devis_batiment.Coin.creerCoin;
import static fr.insa.virquin_trenchant_rey.devis_batiment.Mur.creerMur;
import static fr.insa.virquin_trenchant_rey.devis_batiment.Niveau.creerNiveau;
import static fr.insa.virquin_trenchant_rey.devis_batiment.Piece.creerPieceDepuisMur;
import static fr.insa.virquin_trenchant_rey.devis_batiment.Revetement.appliquerRevetement;
import static fr.insa.virquin_trenchant_rey.devis_batiment.SuppressionObj.suppressionObjet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author evant
 */

public class DvBat1 {
    
    public static int idm=0;// A relocaliser dans sa class
    
    public static int niv_actu=0; //id du niveau sur lequel on travaille
    public static List<Coin> ListCoin = new ArrayList<>();
    public static List<Mur> ListMur = new ArrayList<>();
    public static List<Piece> ListPiece = new ArrayList<>();
    public static List<Plafond> ListPlafond = new ArrayList<>();
    public static List<Sol> ListSol = new ArrayList<>();
    public static List<Niveau> ListNiveau = new ArrayList<>();
    public static List<Appartement> ListAppartement = new ArrayList<>();
    //public static List<Porte> ListPorte = new ArrayList<>();
    //private static List<Fenetre> ListFenetre = new ArrayList<>();
    //private static List<Tremi> ListTremi = new ArrayList<>();
    
    
    public static void main(String[] args) throws IOException {

        creerBatiment();
        creerNiveau();
        BouclePrincipale:
        while (true) {
            menu();
            String entre = Lire.S();
            
            switch (entre) {
            case "c" -> creerCoin();
            case "m" -> creerMur();
            case "p" -> creerPieceDepuisMur();
            case "r" -> appliquerRevetement();
            case "n" -> creerNiveau();
            case "ouv" -> creerOuverture();
            case "appart" -> creerAppartement();
            case "sup" -> suppressionObjet();
            case "e" -> creerSauvegarde();
            case "i" -> importerSauvegarde();
            case "q" -> {
                System.out.println("Fermeture du programme.");
                break BouclePrincipale;
            }
            default -> System.out.println("Entrée inconnue.");            
            }
        }
    }
    
   public static double SurfaceMurExterieur (int nivId){ // à finir
       /*  for(int i = 0; i<ListMur.size(); i++){
                Mur m1 = ListMur.get(i);
                for(int j = i+1; j<ListMur.size(); j++){
                    Mur m2 = ListMur.get(j);
                    
                    if(m2.getDebut().getX() == m1.getDebut().getX() && m2.getFin().getX()==m1.getFin().getX() && nbOuvertureM2==0 && nbOuvertureM1==0){     //nbOuvertureM2/M1 à modifier
                        double a = Math.min(m1.getDebut().getX(), m1.getFin().getX());
                        double b = Math.max(m1.getDebut().getX(), m1.getFin().getX());
                        double a1 = Math.min(m2.getDebut().getX(), m2.getFin().getX());
                        double b1 = Math.max(m2.getDebut().getX(), m2.getFin().getX());
                       if(a<=a1 && a1<=b && b<=b1){
                           double surfcom = (b-a1)*NiveauFromId(niv_actu).getH();
                       }
                       if(a1<=a && a<=b1 && b1<=b){
                           double surfcom = (b1-a)*NiveaufromId(niv_actu).getH();
                       }
                       if(a<=a1 && a1<=b1 && b1<=b){
                           double surfcom = m2.surface();
                       }
                       if(a1<=a && a<=b && b<=b1){
                           double surfcom = m1.surface();
                       }
                    }
                    if(m2.getDebut().getX() == m1.getDebut().getX() && m2.getFin().getX()==m1.getFin().getX() ){
                        double a = Math.min(m1.getDebut().getX(), m1.getFin().getX());
                        double b = Math.max(m1.getDebut().getX(), m1.getFin().getX());
                        double a1 = Math.min(m2.getDebut().getX(), m2.getFin().getX());
                        double b1 = Math.max(m2.getDebut().getX(), m2.getFin().getX());
                        int n= nb de fenêtre du mur 1;
                        int m= nb de fenêtre du mur 2;
                        int o=nb de porte du mur 1;
                        int p=nb de porte du mur 2;
                        
                        
                        
                       if(a<=a1 && a1<=b && b<=b1){
                           double surfcom = (b-a1)*NiveauFromId(niv_actu).getH();
                       }
                       if(a1<=a && a<=b1 && b1<=b){
                           double surfcom = (b1-a)*NiveaufromId(niv_actu).getH();
                       }
                       if(a<=a1 && a1<=b1 && b1<=b){
                           double surfcom = m2.surface();
                       }
                       if(a1<=a && a<=b && b<=b1){
                           double surfcom = m1.surface();
                       }
                    }
                    
                    
                    
                }
        }*/
        return 1;
    }
        
    public static void creerSauvegarde(){
        String nom;
        System.out.println("Quelle nom voulez-vous donner à votre sauvegarde?");
        nom = Lire.S();
        Enregistrement enregistrement = new Enregistrement(ListCoin, ListMur, ListPiece, ListNiveau);
         // Appel de la méthode d'enregistrement des configurations
        enregistrement.enregistrerConfigurations(nom);
    }
    
    public static void importerSauvegarde(){
        String nom;
        System.out.println("Quelle nom de votre sauvegarde?");
        nom = Lire.S();
        Importation importation = new Importation(ListNiveau, ListCoin, ListMur, ListPiece);
         // Appel de la méthode d'importation des configurations
        importation.importerConfigurations(nom);
    }
      
    public static void menu(){
        System.out.println("Taper 'n' pour créer un nouveau niveau ou changer de niveau."); 
        System.out.println("Taper 'c' pour créer un coin.");
        System.out.println("Taper 'm' pour créer un mur.");
        System.out.println("Taper 'p' pour créer une pièce.");
        System.out.println("Taper 'r' pour appliquer un revêtement.");
        System.out.println("Taper 'ouv' pour créer une ouverture.");
        System.out.println("Taper 'sup' pour supprimer un objet.");
        System.out.println("Taper 'appart' pour créer un nouvel Appartement.");
        System.out.println("Taper 'q' pour fermer le programme.");
        System.out.println("Taper 'e' pour enregistrer votre configuration actuelle.");
        System.out.println("Taper 'i' pour importer une sauvegarde.");
    }
}

