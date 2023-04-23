/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment;

/**
 *
 * @author evant
 */
public class Porte extends Ouverture {
    
    double dist;
    static int idporte=0;
    
    public Porte(double longueur, double largeur, int id, double dist){ // disr fait réference à la distance entre le coin debut et le coté de la porte le plus proche
        super(longueur, largeur, id);
        this.dist = dist;
    }
    
    public Porte(double longueur, double largeur, int id){ // disr fait réference à la distance entre le coin debut et le coté de la porte le plus proche
        super(longueur, largeur, id);
    }
    
    public static void creerPorte(){
                // System.out.println("Donnez la hauteur de votre porte:");
                double h = 2.4 ;// Lire.d();
                // System.out.println("Donnez la largeur de votre porte:");
                double l = 0.9 ;// Lire.d();
                System.out.println("Dans quel mur voulez vous ajouter cette porte ?");
                int id = Lire.i();
                System.out.println("a quelle distance du coin debut voulez vouz placer la porte ?");
                double dist = Lire.i();
                // a faire ajout de vérification
                if(!DvBat1.ListMur.contains(Objfromid.MurFromId(id))) {
                    System.out.println("Le mur indiqué n'existe pas");
                }
                else{
                    idporte++;
                    Porte porte = new Porte(h, l, idporte, dist);
                    Objfromid.MurFromId(id).getListPorte().add(porte);
                    System.out.println("La porte a été ajoutée au mur indiqué.");
                }
    }
    
    public double [] getCoordonePorteVer(int id){ //coo. des extremite de la porte projeté d=sur l'axe verticale.
        double x1;
        double x2;
        
        Porte porte = Objfromid.PorteFromId(id);
        
        Mur mur = Objfromid.MurFromPorteId(id);
        
        // Si le mur est verticale et le point debut du mur est le plus petit
        if (mur.getDebut().getX() == mur.getFin().getX() && mur.getDebut().getY()<mur.getFin().getY()) {
            x1 = mur.getDebut().getX() + porte.getDist();
            x2 = x1 + porte.getLargeur();
            double [] coo = {x1, x2 };//new double [2];
            return coo;
        }
        //sinon plus loin
        else if (mur.getDebut().getX() == mur.getFin().getX() && mur.getDebut().getY()>mur.getFin().getY()) {
            x1 = mur.getDebut().getX() - porte.getDist();
            x2 = x1 - porte.getLargeur();
            double [] coo = {x1, x2 };//new double [2];
            return coo;
        }
        System.out.println("Le mur n'est pas verticale");
        return null;
    }
    
    public double [] getCoordonePorteHor(int id){ //coo. des extremite de la porte projeté d=sur l'axe horizontal.
        double x1;
        double x2;
        
        Porte porte = Objfromid.PorteFromId(id);
        
        Mur mur = Objfromid.MurFromPorteId(id);
        
        // Si le mur est horizontale et le point debut du mur est le plus bas
        if (mur.getDebut().getY() == mur.getFin().getY() && mur.getDebut().getY()<mur.getFin().getY()) {
            x1 = mur.getDebut().getX() + porte.getDist();
            x2 = x1 + porte.getLargeur();
            double [] coo = {x1, x2 };//new double [2];
            return coo;
        }
        //sinon plus haut
        else if (mur.getDebut().getY() == mur.getFin().getY() && mur.getDebut().getY()>mur.getFin().getY()) {
            x1 = mur.getDebut().getX() - porte.getDist();
            x2 = x1 - porte.getLargeur();
            double [] coo = {x1, x2 };//new double [2];
            return coo;
        }
        System.out.println("Le mur n'est pas horizontale");
        return null;
    }
    

    public double getDist() {
        return dist;
    }

    public static int getIdporte() {
        return idporte;
    }
    
    
}
