package fr.insa.rey_trenchant_virquin.devis_batiment;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.scene.canvas.GraphicsContext;
import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;

/**
 *
 * @author evant
 */
public class Fenetre extends Ouverture {
    
    static int idfenetre=0;
    
    public Fenetre(double longueur, double largeur, int id){
        super(longueur, largeur, id);
    }

    @Override
    public void dessine(GraphicsContext context) {

    }

    public static void creerFenetre(){
        System.out.println("Donnez la hauteur de votre fenêtre:");
                double h2 = Lire.d();
                System.out.println("Donnez la largeur de votre fenêtre:");
                double l2 = Lire.d();
                idfenetre++;
                Fenetre fenetre = new Fenetre(h2, l2, idfenetre);
                System.out.println("Dans quel mur voulez vous ajouter cette fenêtre ?");
                int id = Lire.i();
                if(Gestion.ListMur.contains(Objfromid.MurFromId(id)) && Objfromid.MurFromId(id).surface() > fenetre.surface() && Objfromid.MurFromId(id).getNiv().getH() > h2 && Objfromid.MurFromId(id).longueur() > l2) {
                    //on demande les coordonnées de la fenêtre par rapport aux coins du mur
                    System.out.println("Donnez la distance entre le coin gauche du mur et le coin gauche de la fenêtre:");
                    double x = Lire.d();
                    //on récupère la distance entre le coin droit du mur et le coin droit de la fenêtre
                    double x2 = Objfromid.MurFromId(id).longueur() - l2 - x;
                    Objfromid.MurFromId(id).getListFenetre().add(fenetre);
                    System.out.println("La fenêtre a été ajoutée au mur indiqué.");
                }
                else {
                    System.out.println("Le mur indiqué n'existe pas ou n'a pas de surface suffisante pour y ajouter une fenêtre.");
                    idfenetre = idfenetre - 1;
                    fenetre = null; 
                }
    }

    
}
