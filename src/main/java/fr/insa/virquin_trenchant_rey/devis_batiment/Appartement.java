/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author evant
 */
public class Appartement {
    private int id;
    private static List<Piece> Listpiece = new ArrayList<>();
    private static int idappart=0;

    public Appartement(int id, List<Piece> Listpiece){
        this.id=id;
        this.Listpiece=Listpiece;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static List<Piece> getListpiece() {
        return Listpiece;
    }


    public static void creerAppartement(){
        int x = 1;
        while( x!=0){
            System.out.println("Saisissez la valeur de l'id de vos pièces l'une après l'autre.");
            System.out.println("Taper 0 pour finir ");
            x=Lire.i();
            if(x!=0){
                if(Verification.possibleAppartement(x)){
                    Listpiece.add(Objfromid.PieceFromId(x));
                }
                idappart++;
                Appartement a = new Appartement(0, Listpiece);
                DvBat1.ListAppartement.add(a);
            }
        }

    }
}
