package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static fr.insa.rey_trenchant_virquin.devis_batiment.Objfromid.NiveauFromId;


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

    public static Coin creerCoin(double x, double y){
        boolean doublons = false;
        //vérifie si le coin existe déjà dans ce niveau
        for (Coin c : Gestion.ListCoin){
            if (c.getX()==x && c.getY()==y && c.getNiv()==NiveauFromId(Gestion.niv_actu)){
                doublons=true;
                break;
            }
        }
        if (doublons==true){
            System.out.println("Ce point existe déjà.");
            return null;
        }else{
            idcoin++;
            Coin c = new Coin(x,y, NiveauFromId(Gestion.niv_actu),idcoin);
            Gestion.ListCoin.add(c);
            System.out.println(c);
            return c;
        }
        
    }
}
    
    

