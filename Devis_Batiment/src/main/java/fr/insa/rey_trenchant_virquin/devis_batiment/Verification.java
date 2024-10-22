package fr.insa.rey_trenchant_virquin.devis_batiment;

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @authors VIRQUIN Rudy, TRENCHANT Evan
 */
public class Verification {
    
    public static boolean possibleMur(Coin debut, Coin fin){
        // cette methode vérifie que les coins debut et fin sont en capacité de créer un mur
        
        // les coins existe
        if(!HelloApplication.ListCoin.contains(debut) || !HelloApplication.ListCoin.contains(fin)){
            System.out.println("Les coins n'existent pas");
            return false;
        }
        // les coins appartiennent au meme niveau
        if(debut.getNiv() != Objfromid.NiveauFromId(HelloApplication.niv_actu) || fin.getNiv() != Objfromid.NiveauFromId(HelloApplication.niv_actu)){
            System.out.println("Les coins n'appartiennent pas au même niveau");
            return false;
        }
        // les coins sont différents
        if(debut == fin){
            System.out.println("Les coins sont les mêmes");
            return false;
        }
        // les coins sont alignés
        if(debut.getX()!= fin.getX() && debut.getY()!= fin.getY()){
            System.out.println("Les coins ne sont pas alignés");
            return false;
        }
        // les coins ne constituent pas déjà le même mur
        for (Mur m : HelloApplication.ListMur){
            if (m.getDebut()==debut && m.getFin()==fin || m.getDebut()==fin && m.getFin()==debut){
                System.out.println("Les coins constituent déjà le même mur");
                return false;
            }
        }
        // Empêcher deux murs de se superposer (version RUDY)
        for (Mur m : HelloApplication.ListMur){
            if (m.getDebut().getX() == m.getFin().getX() && debut.getX() == fin.getX()){ // si les deux murs sont verticaux
                if (m.getDebut().getX() == debut.getX() && debut.getNiv()==m.getNiv()){ // si les deux murs sont sur la même colonne et sur le même niveau
                    if (m.getDebut().getY() < debut.getY() && m.getFin().getY() > fin.getY() || m.getDebut().getY() > debut.getY() && m.getFin().getY() < fin.getY()){ // si les deux murs se croisent
                        System.out.println("Les murs se superposent");
                        return false;
                    }
                }
            }
            if (m.getDebut().getY() == m.getFin().getY() && debut.getY() == fin.getY()){ // si les deux murs sont horizontaux
                if (m.getDebut().getY() == debut.getY() && debut.getNiv()==m.getNiv()){ // si les deux murs sont sur la même ligne et sur le même niveau
                    if (m.getDebut().getX() < debut.getX() && m.getFin().getX() > fin.getX() || m.getDebut().getX() > debut.getX() && m.getFin().getX() < fin.getX()){ // si les deux murs se croisent
                        System.out.println("Les murs se superposent");
                        return false;
                    }
                }
            }
        }
        //// Empêcher deux murs de se traverser/couper perpendiculairement (version Evan)
        for (Mur m : HelloApplication.ListMur){
            // si le  mur(m) est verticale et celui en construction horizontale.
            if (m.getDebut().getX() == m.getFin().getX() && debut.getY() == fin.getY()){ 
                // si un coin du mur(m) est au-dessus du mur construit et l'autre coin en dessous.(les murs ont une chance de se croiser)
                if (m.getDebut().getY()<debut.getY() && m.getFin().getY()>debut.getY() || m.getDebut().getY()>debut.getY() && m.getFin().getY()<debut.getY()){
                    //si un coin du mur(m) est à gauche du mur construit et l'autre coin en dessous.(les murs ont une chance de se croiser)
                    if(m.getDebut().getX()<debut.getX() && m.getDebut().getX()>fin.getX() || m.getDebut().getX()>debut.getX() && m.getDebut().getX()<fin.getX()){
                        System.out.println("Les murs se croisent");
                        return false;
                    }
                }
            }
            // si le  mur(m) est horizontale et celui en construction verticale.
            if (m.getDebut().getY() == m.getFin().getY() && debut.getX() == fin.getX()){ // Si le mur(m) est verticale et celui en construction horizontale.
                // si un coin du mur(m) est au-dessus du mur construit et l'autre coin en dessous.(les murs ont une chance de se croiser)
                if (m.getDebut().getY()<debut.getY() && m.getFin().getY()>debut.getY() || m.getDebut().getY()>debut.getY() && m.getFin().getY()<debut.getY()){
                    //si un coin du mur(m) est à gauche du mur construit et l'autre coin en dessous.(les murs ont une chance de se croiser)
                    if(m.getDebut().getX()<debut.getX() && m.getDebut().getX()>fin.getX() || m.getDebut().getX()>debut.getX() && m.getDebut().getX()<fin.getX()){
                        System.out.println("Les murs se croisent");
                        return false;
                    }
                }
            }
            
        }
        return true;
    }
 
    public static boolean possiblePiece(int I, int II, int III, int IV) {
        
        Mur [] Mur = new Mur [4];   //création d'un tableau contenant les quatre murs de la pièce
        Set<Coin> SetIdCoin = new HashSet<>();  //création d'un Set contenant les coins des murs
        SetIdCoin.clear();  // vide le set
        int i=0;
        //Récupération des murs à partir de leurs id
        Mur[0]=Objfromid.MurFromId(I); 
        Mur[1]=Objfromid.MurFromId(II);
        Mur[2]=Objfromid.MurFromId(III);
        Mur[3]=Objfromid.MurFromId(IV);
        
        // vérification de l'existence et de l'appartenance au même niveau
        if(!HelloApplication.ListMur.contains(Mur[0]) || !HelloApplication.ListMur.contains(Mur[1]) || !HelloApplication.ListMur.contains(Mur[2]) || !HelloApplication.ListMur.contains(Mur[3])){
            System.out.println("Les murs sélectionnés n'existent pas.");
            return false;
        }
        if(Mur[0].getNiv() != Mur[1].getNiv() || Mur[1].getNiv() != Mur[2].getNiv() || Mur[2].getNiv() != Mur[3].getNiv()){
            System.out.println("Les murs sélectionnés ne se situent pas dans le même niveau.");
            return false;
        }
        
        //remplissage du set avec les id des 8 coins appartenant aux murs de la pièce
        for(i=0; i<4; i++){
            SetIdCoin.add(Mur[i].getDebut());
            SetIdCoin.add(Mur[i].getFin());
        }
        Coin[] coin_id = SetIdCoin.toArray(new Coin[4]); //conversion du set en tableau
        //vérification que les coins des murs sont exactement 4 
        if(SetIdCoin.size()!=4){
            System.out.println("Les murs sélectionnés ont plus ou moins que 4 coins en commun.");
            return false;
        }
        //vérification que les murs ne constituent pas déjà une pièce
        for (Piece p : HelloApplication.ListPiece){
            int compt=0;
            for(i=0; i<4; i++){
                if (p.getMur()[0]==Mur[i] || p.getMur()[1]==Mur[i]){
                    compt++;
                }

            }
            if (compt>1){
                System.out.println("Les murs sélectionnés constituent déjà une pièce.");
                return false;
            }
        }
        //vérification que la pièce n'est pas à l'intérieur d'une autre pièce (si un coin se trouve déjà dans une pièce)
        for (Piece p : HelloApplication.ListPiece){
            //on prend les 4 coins de la pièce p
            Coin[] coin_p = p.getCoin();
            //on regarde si l'abscisse de la pièce en création est comprise entre les abscisses des coins de la pièce p
            if (coin_id[0].getX()>coin_p[0].getX() && coin_id[0].getX()<coin_p[1].getX() || coin_id[0].getX()<coin_p[0].getX() && coin_id[0].getX()>coin_p[1].getX()){
                //on regarde si l'ordonnée de la pièce en création est comprise entre les ordonnées des coins de la pièce p
                if (coin_id[0].getY()>coin_p[0].getY() && coin_id[0].getY()<coin_p[2].getY() || coin_id[0].getY()<coin_p[0].getY() && coin_id[0].getY()>coin_p[2].getY()){
                    System.out.println("La pièce est à l'intérieur d'une autre pièce.");
                    return false;
                }
            }

        }

        return true; 
    }
    
    public static boolean possibleAppartement(int id){//verification de l'ajout d'une pièce à un appartement
        
        Piece p = Objfromid.PieceFromId(id);
        // la piece existe
        if(!HelloApplication.ListPiece.contains(p)) return false;
        // n'appartiens pas déjà un autre appart si le bâtiment créer est un immeuble
        if(HelloApplication.bâtiment instanceof Immeuble){
            Immeuble immeuble = (Immeuble) HelloApplication.bâtiment;
            for(Appartement a : immeuble.ListAppartements){
                for(Piece p1 : a.getListpiece()){
                    if(p1==p){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //vérification que tous les murs, sols et plafonds aient des revêtements, si ce n'est pas le cas, on renvoit faux
    public static boolean possibleDevis(){
        for(Mur m : HelloApplication.ListMur){
            if(m.getR()==null){
                return false;
            }
        }
        for(Sol s : HelloApplication.ListSol){
            if(s.getR()==null){
                return false;
            }
        }
        for(Plafond p : HelloApplication.ListPlafond){
            if(p.getR()==null){
                return false;
            }
        }
        return true;
    }
}
