/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

/**
 *
 * @author evant
 */
public class Tremi extends Ouverture {
    
    public static int idouv=0;
    
    public Tremi(double longueur, double largeur, int id){
        super(longueur, largeur, id);
    }
    
    public static void creerTremi(){
        
                System.out.println("Dans quelle piece voulez vous créer une tremie ?");
                int id = Lire.i();
                if (DvBat1.ListPiece.contains(Objfromid.PieceFromId(id))){
                    idouv++;
                    System.out.println("Quelle est la longueur de la tremie ?");
                    double longueur = Lire.d();
                    System.out.println("Quelle est la largeur de la tremie ?");
                    double largeur = Lire.d();
                    Tremi tremi = new Tremi(longueur, largeur, idouv);
                    System.out.println("Voulez-vous créer cette tremie au plafond ou au sol ?");
                    String n=Lire.S();
                    switch (n) {
                        case "sol" -> {
                            if(DvBat1.ListSol.contains(Objfromid.SolFromId(id)) && Objfromid.SolFromId(id).surface() > tremi.surface()) {
                                Objfromid.SolFromId(id).setTremi(tremi);//ne fonctionne pas
                                System.out.println("La tremie a été ajoutée au sol indiqué.");
                            }
                            else {
                                System.out.println("Le sol indiqué n'existe pas ou n'a pas de surface suffisante pour y ajouter une tremie.");
                                idouv = idouv - 1;
                                tremi = null;
                            }
                            break;
                        }
                        case "plafond" -> {
                            if(DvBat1.ListPlafond.contains(Objfromid.PlafondFromId(id)) && Objfromid.PlafondFromId(id).surface() > tremi.surface()) {
                                Objfromid.PlafondFromId(id).setTremi(tremi);
                                System.out.println("La tremie a été ajoutée au plafond indiqué."); 
                            }
                            else {
                                System.out.println("Le plafond indiqué n'existe pas ou n'a pas de surface suffisante pour y ajouter une tremie.");
                                idouv = idouv - 1;
                                tremi = null;
                            }
                            break;
                        }
                        default -> System.out.println("Entrée inconnue (sol ou plafond).");
                    }
                }else System.out.println("La pièce n'existe pas.");
    }
}
