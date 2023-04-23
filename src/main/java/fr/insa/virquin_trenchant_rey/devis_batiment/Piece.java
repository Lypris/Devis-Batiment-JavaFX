/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author evant
 */
public class Piece {
    private int id;
    private Mur [] Mur = new Mur [4];
    private Coin [] Coin = new Coin [4];
    private Niveau n;
    private String nom;
    public static int idpiece=0;
    
    public Piece(Mur [] Mur, Coin [] Coin, Niveau niv, int id){
        this.id = id;
        this.Mur = Mur;
        this.Coin = Coin;
        this.n = niv;
        
    }
    
    public double surface(){
        
        double largeur = Math.min(Math.min(Mur[0].longueur(), Mur[1].longueur()),Mur[2].longueur());
        double longueur = Math.max(Math.max(Mur[0].longueur(), Mur[1].longueur()),Mur[2].longueur());
        return largeur*longueur;
    }

    public Coin[] getCoin() {
        return Coin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Piece{" + "id=" + id + ", I=" + Mur[0].getId() + ", II=" + Mur[1].getId() + ", III=" + Mur[2].getId() + ", IV=" + Mur[3].getId() + ", c1=" + Coin[0].getId() + ", c2=" + Coin[1].getId() + ", c3=" + Coin[2].getId() + ", c4=" + Coin[3].getId() + ", Surface du sol à couvrir : " + surface() + "m²" + '}';
    }

    public int getId() {
        return id;
    }

    public Mur[] getMur() {
        return Mur;
    }

    public Niveau getN() {
        return n;
    }
    
    public static void creerPieceDepuisMur() {
        
        System.out.println(DvBat1.ListMur);
        System.out.println("Saisissez l'identifiant du premier mur de votre pièce:");
        int I = Lire.i();
        System.out.println("Saisissez l'identifiant du deuxième mur de votre pièce:");
        int II = Lire.i();
        System.out.println("Saisissez l'identifiant du troisième mur de votre pièce:");
        int III = Lire.i();
        System.out.println("Saisissez l'identifiant du quatrième mur de votre pièce:");
        int IV = Lire.i();
        
        if(Verification.possiblePiece(I ,II, III, IV)){
            Mur [] mur = {Objfromid.MurFromId(I), Objfromid.MurFromId(II), Objfromid.MurFromId(III), Objfromid.MurFromId(IV)};
            //création d'un Set contenant les coins des murs
            Set<Coin> SetCoin = new HashSet<>();  
            SetCoin.clear(); 
            for(int i=0; i<4; i++){
                SetCoin.add(mur[i].getDebut());
                SetCoin.add(mur[i].getFin());
            }
            Coin[] coin = SetCoin.toArray(new Coin[4]); //conversion du set en tableau
            idpiece++;
            Piece p = new Piece (mur, coin, DvBat1.ListNiveau.get(DvBat1.niv_actu), idpiece);
            Sol sol = new Sol (mur, coin, DvBat1.ListNiveau.get(DvBat1.niv_actu), idpiece, null, null);
            Plafond plafond = new Plafond (mur, coin, DvBat1.ListNiveau.get(DvBat1.niv_actu), idpiece, null, null);
            DvBat1.ListPiece.add(p);
            DvBat1.ListSol.add(sol);
            DvBat1.ListPlafond.add(plafond);
            //ajout de la piece dans la liste des pièces du niveau.
            DvBat1.ListNiveau.get(DvBat1.niv_actu).addPiece(p);
            System.out.println(p);
            System.out.println("Quel nom souhaitez vous donner à votre pièce ?");
            p.setNom(Lire.S());
        }else{
            System.out.println("Votre pièce ne peut pas exister.");
        }
    }
    
    
}
