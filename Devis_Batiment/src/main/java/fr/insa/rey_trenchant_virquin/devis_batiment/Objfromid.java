package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;

/**
 *
 * @author evant
 */
public class Objfromid {
    
    public static Mur MurFromId(int id) {
        for (Mur m : Gestion.ListMur) {
            if (m.getId() == id) {
                return m;
            }
        }
        System.out.println("Aucun mur ne correspond à l'identifiant " + id);
        return null;
    }
    
    public static Coin CoinFromId(int id){ //permet de retrouver un coin depuis son id
        for (Coin c : Gestion.ListCoin){
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }
    
    public static Niveau NiveauFromId(int id){ //permet de retrouver un niv depuis son id
        //on vérifie que la liste de niveaux n'est pas vide
        if(Gestion.ListNiveau.isEmpty()){
            System.out.println("La liste de niveaux est vide");
            return null;
        }
        else{
            for (Niveau niv : Gestion.ListNiveau){
                if(niv.getId()==id){
                    return niv;
                }
            }
            System.out.println("Aucun niveau ne correspond à l'identifiant " + id);
            return null;
        }
    }
    
    public static Piece PieceFromId(int id){ //permet de retrouver un mur depuis son id
        for (Piece p : Gestion.ListPiece){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }
    
    public static Plafond PlafondFromId(int id){
        for (Plafond plafond : Gestion.ListPlafond){
            if(plafond.getId()==id){
                return plafond;
            }
        }
        return null;
    }
    
    public static Sol SolFromId(int id){
        for (Sol sol : Gestion.ListSol){
            if(sol.getId()==id){
                return sol;
            }
        }
        return null;
    }   
    
    
    public static Porte PorteFromId(int id) {
        for (Mur m : Gestion.ListMur) {
            for (Porte por : m.getListPorte())
                if (por.getId() == id) {
                    return por;
                }
        }
        System.out.println("Aucune porte ne correspond à l'identifiant " + id);
        return null;
    }
    
    public static Mur MurFromPorteId(int id) {
        for (Mur m : Gestion.ListMur) {
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
