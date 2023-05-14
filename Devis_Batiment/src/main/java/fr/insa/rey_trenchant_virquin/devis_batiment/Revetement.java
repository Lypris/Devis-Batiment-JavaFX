package fr.insa.rey_trenchant_virquin.devis_batiment;

import fr.insa.rey_trenchant_virquin.devis_batiment.gui.HelloApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author VIRQUIN Rudy
 */
public class Revetement {
    private int id, mur, sol, plafond;
    private String type;
    private double prix;

    public Revetement(int identifiant, String nom_materiaux, double tarif, int m, int s, int p) {
        this.id = identifiant;
        this.type = nom_materiaux;
        this.prix = tarif;
        this.mur = m;
        this.sol = s;
        this.plafond = p;
    }
    
    public Revetement(int identifiant, String nom_materiaux, double tarif) {
        this.id = identifiant;
        this.type = nom_materiaux;
        this.prix = tarif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return type;
    }

    public void setNom(String type) {
        this.type = type;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getMur() {
        return mur;
    }

    public void setMur(int mur) {
        this.mur = mur;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public int getPlafond() {
        return plafond;
    }

    public void setPlafond(int plafond) {
        this.plafond = plafond;
    }
    
    public static Revetement RevetementfromNom(String recherche) throws IOException { //fonction qui créer un revêtement à partir d'un nom
        Revetement R = null;
        String identifiant, type, prix, mur, sol, plafond;
        try {
            BufferedReader flux=new BufferedReader(new FileReader("Revetements.txt")); //lecture ligne par ligne
            String lignelue;
            flux.readLine();

            while((lignelue=flux.readLine())!=null) {
                StringTokenizer mot=new StringTokenizer(lignelue, ";"); 
                identifiant=mot.nextToken();
                type=mot.nextToken();
                prix=mot.nextToken();
                mur=mot.nextToken();
                sol=mot.nextToken();
                plafond=mot.nextToken();
                int id_bis = Integer.valueOf(identifiant); //conversion de string vers int
                double tarif = Double.valueOf(prix); //conversion de string vers double
                int pourMur = Integer.valueOf(mur);
                int pourSol = Integer.valueOf(sol);
                int pourPlafond = Integer.valueOf(plafond);
                if (type.equals(recherche)){
                    R = new Revetement(id_bis, recherche, tarif, pourMur, pourSol, pourPlafond);
                    break; // sortir de la boucle dès qu'on trouve le revêtement
                }
            }
            flux.close();
        }
        catch(FileNotFoundException err){
            System.out.println("Erreur :le fichier n’existe pas!\n "+err);
        }
        return R;
    }
    
    public static void appliquerRevetement() throws IOException {
        int id;
        String recherche;
        System.out.println("Quel revêtement souhaitez-vous appliquer?");
        recherche=Lire.S();
        Revetement R = RevetementfromNom(recherche);
        if (R == null) {
            System.out.println("Le revêtement demandé n'a pas été trouvé.");
            return;
        }
        System.out.println("A quel objet voulez-vous appliquer ce revêtement?");
        String objet=Lire.S();
        boolean choixValide = true; // initialisation de la variable de choixValide à vrai
        do {
            choixValide = true; // réinitialisation de choixValide à vrai
            switch (objet) {
                case "mur" -> {
                    if (R.getMur() == 1) {
                        System.out.println("Donnez l'identifiant du mur sur lequel appliquer ce revêtement:");
                        id = Lire.i();
                        Mur m = Objfromid.MurFromId(id);
                        if (m != null) {
                            m.setR(R);
                            System.out.println("Le revêtement a été appliqué avec succès sur le mur " + id + ".");
                        } else {
                            System.out.println("Le mur demandé n'existe pas.");
                            choixValide = false;
                        }
                    } else {
                        System.out.println("Le revêtement choisi ne peut pas être appliqué sur un mur.");
                        choixValide = false;
                    }
                }
                case "sol" -> {
                    if (R.getSol() == 1){
                        System.out.println("Donnez l'identifiant du sol sur lequel appliquer ce revêtement:");
                        id = Lire.i();
                        Sol s = Objfromid.SolFromId(id);
                        if (s != null) {
                            s.setR(R);
                            System.out.println("Le revêtement a été appliqué avec succès sur le mur " + id + ".");
                        } else {
                            System.out.println("Le sol demandé n'existe pas.");
                            choixValide = false;
                        }
                    }
                    else {
                        System.out.println("Le revêtement choisi ne peut pas être appliqué sur un sol.");
                        choixValide = false; // choix invalide, on répète la boucle
                    }
                }
                case "plafond" -> {
                    if ( R.getPlafond() == 1){
                        System.out.println("Donnez l'identifiant du plafond sur lequel appliquer ce revêtement:");
                        id = Lire.i();
                        Objfromid.PlafondFromId(id).setR(R);
                    }
                    else {
                        System.out.println("Le revêtement choisi ne peut pas être appliqué sur un plafond.");
                        choixValide = false; // choix invalide, on répète la boucle
                    }
                }
                default -> {
                    System.out.println("Entrée inconnue.");
                    choixValide = false; // choix invalide, on répète la boucle
                }
            }

            if (!choixValide) { // si choix invalide, redemander le type d'objet
                System.out.println("Veuillez choisir un objet valide (mur, sol ou plafond):");
                objet = Lire.S();
            }
        } while (!choixValide); // répéter tant que choix invalide
    }

    public static double calculPrixRevetement(Revetement r) {
        double prix = 0;
        //on parcourt les Listes de murs, sols et plafonds qui ont ce revêtement
        for (Mur m : HelloApplication.ListMur){
            if (m.getR() == r){
                prix += r.getPrix() * m.surface();
            }
        }
        for (Sol s : HelloApplication.ListSol){
            if (s.getR() == r){
                prix += r.getPrix() * s.surface();
            }
        }
        for (Plafond p : HelloApplication.ListPlafond){
            if (p.getR() == r){
                prix += r.getPrix() * p.surface();
            }
        }
        return prix;
    }

    //méthode de calcul de la surface totale couverte par un revêtement
    public static double calculSurfaceRevetement(Revetement r) {
        double surface = 0;
        //on parcourt les Listes de murs, sols et plafonds qui ont ce revêtement
        for (Mur m : HelloApplication.ListMur){
            if (m.getR() == r){
                surface += m.surface();
            }
        }
        for (Sol s : HelloApplication.ListSol){
            if (s.getR() == r){
                surface += s.surface();
            }
        }
        for (Plafond p : HelloApplication.ListPlafond){
            if (p.getR() == r){
                surface += p.surface();
            }
        }
        return surface;
    }
    
    
    @Override
    public String toString() {
        return "Revêtement" + " id=" + id + ", type de revêtement=" + type + ", prix au m²=" + prix + '}';
    }
}
