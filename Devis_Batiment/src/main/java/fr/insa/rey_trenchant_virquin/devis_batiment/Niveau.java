package fr.insa.rey_trenchant_virquin.devis_batiment;

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;

import java.util.ArrayList;
import java.util.List;

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
    public Niveau(int id) {
        this.id = id;
    } //constructeur utilisé par TabNavigation
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
    public void setId(int id) {
        this.id = id;
    }
    public void changeId(int newId) {
        this.id = newId;
    }
    @Override
    public String toString() {
        return "Niveau{" + "id= " + id + ", h= " + h + '}';
    }

    public static Niveau creerNiveau(int id_niv) {
        if (Objfromid.NiveauFromId(id_niv) == null) {
            System.out.println("Quelle est la hauteur sous plafond de ce niveau ?");
            double h = Lire.d();
            List<Piece> Listpiece = new ArrayList<>();
            Niveau niv = new Niveau(id_niv, Listpiece, h);
            Gestion.ListNiveau.add(niv);
            System.out.println(niv);
            return niv;
        } else {
            System.out.println("Ce niveau existe déjà : vous travaillez maintenant sur le niveau : " + id_niv);
            return Objfromid.NiveauFromId(id_niv);
        }
    }

}
