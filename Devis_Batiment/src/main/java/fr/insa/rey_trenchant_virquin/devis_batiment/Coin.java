package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 *
 * @author evant
 */
public class Coin {

    private double x;
    private double y;
    private int id;
    private Niveau niv;
    private static int idcoin=0;
    public static double RAYON_DESSIN = 5;
    
    public Coin(double x, double y, Niveau niv, int id){
        this.niv =niv;
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    
    public int getId(){
        return id;
    }

    public Niveau getNiv() {
        return niv;
    }
    
    @Override
    public String toString() {
        return "Coin{" + "x=" + x + ", y=" + y + ", id=" + id + ", niv=" + niv.getId() + '}';
    }

    public static void creerCoin(){
        System.out.println("Saisissez la valeur en abscisse de votre point:");
        double x=Lire.d();
        System.out.println("Saisissez la valeur en ordonnée de votre point:");
        double y=Lire.d();
        boolean doublons = false;
        
        for (Coin c : Gestion.ListCoin){ //vérifie si le coin existe déjà dans ce niveau
            if (c.getX()==x && c.getY()==y ){
                doublons=true;
                break;
            }
        }
        if (doublons==true){
            System.out.println("Ce point existe déjà.");
        }else{
            idcoin++;
            Coin c = new Coin(x,y,Objfromid.NiveauFromId(Gestion.niv_actu),idcoin);
            Gestion.ListCoin.add(c);
            System.out.println(c);
        }
        
    }

    public void dessine(GraphicsContext context){
        context.setFill(Color.BLACK);
        context.fillOval(this.x-RAYON_DESSIN, this.y-RAYON_DESSIN,2*RAYON_DESSIN, 2*RAYON_DESSIN);
    }
}
    
    

