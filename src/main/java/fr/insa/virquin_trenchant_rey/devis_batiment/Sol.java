/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

/**
 *
 * @author evant
 */
public class Sol extends Piece {
    private Tremi tremi;
    private Revetement R;
    
    public Sol(Mur [] Mur, Coin [] Coin, Niveau niv, int id, Tremi tremi, Revetement R){
        super(Mur, Coin,  niv, id);
        this.tremi=tremi;
        this.R=R;
    }
    public double surface(){
        return super.surface()-tremi.surface();
    }

    public Tremi getTremi() {
        return tremi;
    }

    public void setTremi(Tremi tremi) {
        this.tremi = tremi;
    }
    

    public Revetement getR() {
        return R;
    }

    public void setR(Revetement R) {
        this.R = R;
    }
    
}
