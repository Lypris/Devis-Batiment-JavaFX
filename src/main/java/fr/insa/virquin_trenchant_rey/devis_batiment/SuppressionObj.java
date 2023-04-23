/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

/**
 *
 * @author evant
 */
public class SuppressionObj {
    
    public static void suppressionCoin (int Id){
        Coin c1 = Objfromid.CoinFromId(Id);
        
        for (Mur m : DvBat1.ListMur){
            if(m.getDebut()==c1 || m.getDebut()==c1){
                suppressionMur(m.getId());
            }
        }
        DvBat1.ListCoin.remove(c1);
        c1 = null;
    }

    public static void suppressionMur (int Id){
        Mur m1 = Objfromid.MurFromId(Id);
        for (Piece p : DvBat1.ListPiece){
            if(p.getMur()[0]==m1 || p.getMur()[1]==m1 || p.getMur()[2]==m1 || p.getMur()[3]==m1){
                suppressionPiece(p.getId());
            }
        }
        DvBat1.ListMur.remove(m1);
        m1 = null;
    }
    
    public static void suppressionPiece (int Id){
        Piece p1 = Objfromid.PieceFromId(Id);
        DvBat1.ListPiece.remove(p1);
        p1 = null;
        Sol s1 = Objfromid.SolFromId(Id);
        DvBat1.ListSol.remove(s1);
        s1 = null;
        Plafond pl1 = Objfromid.PlafondFromId(Id);
        DvBat1.ListPlafond.remove(pl1);
        pl1 = null;
    }
    
    public static void suppressionObjet (){
        BouclePrincipale:
        while (true) {
            System.out.println("Taper 'n' pour supprimer un niveau"); 
            System.out.println("Taper 'c' pour supprimer un coin.");
            System.out.println("Taper 'm' pour supprimer un mur.");
            System.out.println("Taper 'p' pour supprimer une pièce.");
            System.out.println("Taper 'r' pour appliquer un revêtement.");
            System.out.println("Taper 'ouv' pour supprimer une ouverture.");
            System.out.println("Taper 'q' pour annuler");
            String entre = Lire.S();
            
            switch (entre) { //A finir
            case "c" -> {
                System.out.println("Taper l'id de votre coin");
                int id = Lire.i();
                if(DvBat1.ListPiece.contains(Objfromid.CoinFromId(id))){
                    System.out.println("Ce coin est utilisé par une piece.");
                    System.out.println("Voulez-vous quand même supprimer le coin et la pièce associée ? (oui/non)");
                    String entre2 = Lire.S();
                    if (entre2.equals("oui")){
                        suppressionCoin(id);
                        suppressionMur(Objfromid.MurFromId(id).getId());
                        suppressionPiece(Objfromid.PieceFromId(id).getId());
                    }
                    else if (entre2.equals("non")) break BouclePrincipale;
                    else System.out.println("Entrée inconnue.");

                }
                else if (DvBat1.ListMur.contains(Objfromid.CoinFromId(id))){
                    System.out.println("Ce coin est utilisé par un mur.");
                    System.out.println("Voulez-vous quand même supprimer le coin et le mur associé ? (oui/non)");
                    String entre2 = Lire.S();
                    if (entre2.equals("oui")){
                        suppressionCoin(id);
                        suppressionMur(Objfromid.MurFromId(id).getId());
                    }
                    else if (entre2.equals("non")) break BouclePrincipale;
                    else System.out.println("Entrée inconnue.");

                }

                else if (DvBat1.ListCoin.contains(Objfromid.CoinFromId(id))) suppressionCoin(id);
                else System.out.println("Ce coin n'existe pas");
            }
            case "m" -> {
                System.out.println("Taper l'id de votre mur");
                int id = Lire.i();
                //vérifier que le mur ne fait pas partie d'une piece
                if(DvBat1.ListPiece.contains(Objfromid.MurFromId(id))){
                    System.out.println("Ce mur est utilisé par une piece.");
                    System.out.println("Voulez-vous quand même supprimer le mur et la pièce associée ? (oui/non)");
                    String entre2 = Lire.S();
                    if (entre2.equals("oui")){
                        suppressionMur(id);
                        suppressionPiece(Objfromid.PieceFromId(id).getId());
                    }
                    else if (entre2.equals("non")) break BouclePrincipale;
                    else System.out.println("Entrée inconnue.");
                }
                else if (DvBat1.ListMur.contains(Objfromid.MurFromId(id))) suppressionMur(id);
                else System.out.println("Ce mur n'existe pas");
            }
            case "p" -> {
                System.out.println("Taper l'id de votre piece");
                int id = Lire.i();
                if (DvBat1.ListPiece.contains(Objfromid.PieceFromId(id))) suppressionPiece(id);
                else System.out.println("Cette piece n'existe pas");
            }
                
            case "q" -> { break BouclePrincipale;  } 
            default -> System.out.println("Entrée inconnue.");            
            }
        }
    }
    
}
