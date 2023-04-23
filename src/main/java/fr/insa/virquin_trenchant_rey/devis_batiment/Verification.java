/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

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
        if(!DvBat1.ListCoin.contains(debut) || !DvBat1.ListCoin.contains(fin)) return false;
        // les coins appartiennent au meme niveau
        if(debut.getNiv() != Objfromid.NiveauFromId(DvBat1.niv_actu) || fin.getNiv() != Objfromid.NiveauFromId(DvBat1.niv_actu)) return false;
        // les coins sont différents
        if(debut == fin) return false;
        // les coins sont alignés
        if(debut.getX()!= fin.getX() && debut.getY()!= fin.getY()) return false;
        // les coins ne constituent pas déjà le même mur
        for (Mur m : DvBat1.ListMur){
            if (m.getDebut()==debut && m.getFin()==fin || m.getDebut()==fin && m.getFin()==debut){
                return false; 
            }
        }
        /*// Empêcher deux murs de se traverser/ couper (version RUDY)
        for (Mur m : DvBat1.ListMur){
            if (m.getDebut().getX() == m.getFin().getX() && debut.getX() == fin.getX()){ // si les deux murs sont verticaux
                if (m.getDebut().getX() == debut.getX()){ // si les deux murs sont sur la même colonne
                    if (m.getDebut().getY() < debut.getY() && m.getFin().getY() > fin.getY() || m.getDebut().getY() > debut.getY() && m.getFin().getY() < fin.getY()){ // si les deux murs se croisent
                        return false;
                    }
                }
            }
            if (m.getDebut().getY() == m.getFin().getY() && debut.getY() == fin.getY()){ // si les deux murs sont horizontaux
                if (m.getDebut().getY() == debut.getY()){ // si les deux murs sont sur la même ligne
                    if (m.getDebut().getX() < debut.getX() && m.getFin().getX() > fin.getX() || m.getDebut().getX() > debut.getX() && m.getFin().getX() < fin.getX()){ // si les deux murs se croisent
                        return false;
                    }
                }
            }
        }*/
        //// Empêcher deux murs de se traverser/couper perpendiculairement (version Evan)
        for (Mur m : DvBat1.ListMur){
            // si le  mur(m) est verticale et celui en construction horizontale.
            if (m.getDebut().getX() == m.getFin().getX() && debut.getY() == fin.getY()){ 
                // si un coin du mur(m) est au-dessus du mur construit et l'autre coin en dessous.(les murs ont une chance de se croiser)
                if (m.getDebut().getY()<debut.getY() && m.getFin().getY()>debut.getY() || m.getDebut().getY()>debut.getY() && m.getFin().getY()<debut.getY()){
                    //si un coin du mur(m) est à gauche du mur construit et l'autre coin en dessous.(les murs ont une chance de se croiser)
                    if(m.getDebut().getX()<debut.getX() && m.getDebut().getX()>fin.getX() || m.getDebut().getX()>debut.getX() && m.getDebut().getX()<fin.getX()){
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
        if(!DvBat1.ListMur.contains(Mur[0]) || !DvBat1.ListMur.contains(Mur[1]) || !DvBat1.ListMur.contains(Mur[2]) || !DvBat1.ListMur.contains(Mur[3])){
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
            System.out.println("Les murs sélectionnés ont plus que 4 coins en commun.");
            return false;
        }
        //vérification que les murs ne constituent pas déjà une pièce
        for (Piece p : DvBat1.ListPiece){
            int compt=0;
            for(i=0; i<4; i++){
                if (p.getMur()[i]==Mur[i]){
                    compt++;
                }
            }
            if (compt==4){
                System.out.println("Les murs sélectionnés constituent déjà une pièce.");
                return false;
            }
        }
        //vérification que la pièce n'est pas à l'intérieur d'une autre pièce (si un coin se trouve déjà dans une pièce)
        for (Piece p : DvBat1.ListPiece){
            //on prend les 4 coins de la pièce p
            Coin[] coin_p = p.getCoin();
            //on regarde si l'abscisse de la pièce en création est comprise entre les abscisses des coins de la pièce p
            if (coin_id[0].getX()>=coin_p[0].getX() && coin_id[0].getX()<=coin_p[1].getX() || coin_id[0].getX()<=coin_p[0].getX() && coin_id[0].getX()>=coin_p[1].getX()){
                //on regarde si l'ordonnée de la pièce en création est comprise entre les ordonnées des coins de la pièce p
                if (coin_id[0].getY()>=coin_p[0].getY() && coin_id[0].getY()<=coin_p[2].getY() || coin_id[0].getY()<=coin_p[0].getY() && coin_id[0].getY()>=coin_p[2].getY()){
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
        if(!DvBat1.ListPiece.contains(p)) return false;
        // n'appartiens pas déjà un autre appart
        for(Appartement a : DvBat1.ListAppartement){
            for(Piece p1 : a.getListpiece()){
                if(p1==p){
                    return false;
                } 
            }
        }
        return true;
    }

    //vérification que tous les murs, sols et plafonds aient des revêtements, si ce n'est pas le cas, on renvoit faux
    public static boolean possibleDevis(){
        for(Mur m : DvBat1.ListMur){
            if(m.getR()==null){
                return false;
            }
        }
        for(Sol s : DvBat1.ListSol){
            if(s.getR()==null){
                return false;
            }
        }
        for(Plafond p : DvBat1.ListPlafond){
            if(p.getR()==null){
                return false;
            }
        }
        return true;
    }
}
