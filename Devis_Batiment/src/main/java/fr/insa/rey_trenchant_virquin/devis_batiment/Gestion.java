package fr.insa.rey_trenchant_virquin.devis_batiment;

import java.util.ArrayList;
import java.util.List;

public class Gestion {
    public static int idm=0;// A relocaliser dans sa class
    public static int niv_actu=0; //id du niveau sur lequel on travaille
    public static List<Coin> ListCoin = new ArrayList<>();
    public static List<Mur> ListMur = new ArrayList<>();
    public static List<Piece> ListPiece = new ArrayList<>();
    public static List<Plafond> ListPlafond = new ArrayList<>();
    public static List<Sol> ListSol = new ArrayList<>();
    public static List<Niveau> ListNiveau = new ArrayList<>();
    public static List<Appartement> ListAppartement = new ArrayList<>();
}
