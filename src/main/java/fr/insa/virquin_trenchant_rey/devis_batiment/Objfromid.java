/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

/**
 *
 * @author evant
 */
public class Objfromid {
    
    public static Mur MurFromId(int id) {
        for (Mur m : DvBat1.ListMur) {
            if (m.getId() == id) {
                return m;
            }
        }
        System.out.println("Aucun mur ne correspond à l'identifiant " + id);
        return null;
    }
    
    public static Coin CoinFromId(int id){ //permet de retrouver un coin depuis son id
        for (Coin c : DvBat1.ListCoin){
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }
    
    public static Niveau NiveauFromId(int id){ //permet de retrouver un niv depuis son id
        for (Niveau niv : DvBat1.ListNiveau){
            if(niv.getId()==id){
                return niv;
            }
        }
        return null;
    }
    
    public static Piece PieceFromId(int id){ //permet de retrouver un mur depuis son id
        for (Piece p : DvBat1.ListPiece){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }
    
    public static Plafond PlafondFromId(int id){
        for (Plafond plafond : DvBat1.ListPlafond){
            if(plafond.getId()==id){
                return plafond;
            }
        }
        return null;
    }
    
    public static Sol SolFromId(int id){
        for (Sol sol : DvBat1.ListSol){
            if(sol.getId()==id){
                return sol;
            }
        }
        return null;
    }   
    
    
    public static Porte PorteFromId(int id) {
        for (Mur m : DvBat1.ListMur) {
            for (Porte por : m.getListPorte())
                if (por.getId() == id) {
                    return por;
                }
        }
        System.out.println("Aucune porte ne correspond à l'identifiant " + id);
        return null;
    }
    
    public static Mur MurFromPorteId(int id) {
        for (Mur m : DvBat1.ListMur) {
            for (Porte por : m.getListPorte()){
                if (por.getId() == id) {
                    return m;
                }
            }
        }
        System.out.println("Aucune porte ne correspond à l'identifiant " + id);
        return null;
    }
    
}
