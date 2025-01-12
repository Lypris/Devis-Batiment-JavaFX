package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

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
        for (Coin c : HelloApplication.ListCoin){
            if (c.getX()==x && c.getY()==y && c.getNiv()==NiveauFromId(HelloApplication.niv_actu)){
                doublons=true;
                break;
            }
        }
        if (doublons==true){
            System.out.println("Ce point existe déjà.");
            return null;
        }else{
            idcoin++;
            Coin c = new Coin(x,y, NiveauFromId(HelloApplication.niv_actu),idcoin);
            HelloApplication.ListCoin.add(c);
            System.out.println(c);
            return c;
        }
        
    }

    public void dessine(GraphicsContext gc, double zoomLevel, Translate translate) {
        double x = this.getX() * zoomLevel + translate.getX();
        double y = this.getY() * zoomLevel + translate.getY();
        double radius = 5.0;
        gc.setFill(Color.BLACK);
        gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
    
    

