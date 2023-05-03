package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author evant
 */
public class Toit {
    private int id;    
    private double largeur;
    private double longueur;
    private double pente;
    private Revetement R;
    private int nbp;
    
    public Toit(double pente, double longueur, double largeur, Revetement rev){
        this.longueur = longueur;
        this.largeur = largeur;
        this.pente = pente;
        this.R = rev;
    }
}
