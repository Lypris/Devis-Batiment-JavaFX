/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author evant
 */
public class Niveau {
    private int id;
    private static List<Piece> Listpiece = new ArrayList<>();
    private double h; // hauteur sous le plafond   
    
    public Niveau(int id, List<Piece> Listpiece, double h){
        this.id=id;
        this.Listpiece=Listpiece;
        this.h=h;
    }
    public Niveau(int id, double h){
        this.id=id;
        this.h=h;
    }

    public List<Piece> getPiece() {
        return Listpiece;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getId() {
        return id;
    }    

    public static void setListpiece(List<Piece> Listpiece) {
        Niveau.Listpiece = Listpiece;
    }

    public void addPiece(Piece p){
        this.Listpiece.add(p);
    }
    public void removePiece(Piece p){
        this.Listpiece.remove(p);
    }
    
    public List<Mur> getMur(int niveau) {
        List<Mur> Listmur = new ArrayList<>();
        for (Mur m : DvBat1.ListMur){
            if(m.getNiv().getId()== niveau){
                Listmur.add(m);
            }
        }
        return Listmur;
    }
    
    public List<Coin> getCoin(int niveau) {
        List<Coin> Listcoin = new ArrayList<>();
        for (Coin c : DvBat1.ListCoin){
            if(c.getNiv().getId()== niveau){
                Listcoin.add(c);
            }
        }
        return Listcoin;
    }

    public void showObj(){ //méthode pour afficher les objets du niveau
        System.out.println("Niveau "+this.id);
        System.out.println("Liste des pièces contenues dans ce niveau:");
        for (Piece p : Listpiece){
            for (Mur m : p.getMur()){
                System.out.println(p);
                System.out.println(m);
            }
        }

    }
    
    public static void creerNiveau() {
        
        System.out.println("Choisissez un identifiant de niveau:");
        DvBat1.niv_actu = Lire.i();
        
        if(Objfromid.NiveauFromId(DvBat1.niv_actu)==null){
            System.out.println("Quelle est la hauteur sous plafond de ce niveau ?");
            double h = Lire.d();
            List<Piece> Listpiece = new ArrayList<>();
            Niveau niv = new Niveau (DvBat1.niv_actu, Listpiece, h);
            DvBat1.ListNiveau.add(niv);
            System.out.println(niv);
        }
        else{
            System.out.println("Ce niveau existe déjà : vous travaillez maintenant sur le niveau : " + DvBat1.niv_actu);
        }
    }
    
    @Override
    public String toString() {
        return "Niveau{" + "id= " + id + ", h= " + h + '}';
    }
    
}
