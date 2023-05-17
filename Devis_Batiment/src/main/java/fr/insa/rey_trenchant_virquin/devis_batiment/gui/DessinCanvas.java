package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

import static fr.insa.rey_trenchant_virquin.devis_batiment.Objfromid.NiveauFromId;

public class DessinCanvas extends Canvas {
    private double zoomLevel = 1.0;
    private Scale scale;
    private Translate translate;

    private final double MAX_ZOOM_LEVEL = 5.0;
    private final double MIN_ZOOM_LEVEL = 0.5;
    // Add a list to keep track of selected corners
    public static List<Coin> selectedCorners = new ArrayList<>();
    public static List<Mur> selectedMurs = new ArrayList<>();

    public DessinCanvas(double width, double height) {
        super(width, height);
        widthProperty().addListener(e -> drawGrid());
        heightProperty().addListener(e -> drawGrid());

        // Initialize the scale and translate transformations
        scale = new Scale(zoomLevel, zoomLevel, 0, 0);
        translate = new Translate(0, 0);
        getTransforms().addAll(scale, translate);

        // Add the mouse wheel event listener for zooming
        addEventHandler(ScrollEvent.SCROLL, event -> {
            double oldZoomLevel = zoomLevel;
            if (event.getDeltaY() > 0) {
                zoomLevel *= 1.1;
            } else {
                zoomLevel /= 1.1;
            }
            // Limit the zoom level to the maximum and minimum values
            zoomLevel = Math.max(MIN_ZOOM_LEVEL, Math.min(MAX_ZOOM_LEVEL, zoomLevel));
            // Calculate the translation needed to keep the center of the canvas in the same position
            double dx = (getWidth() / 2 - translate.getX()) * (zoomLevel / oldZoomLevel - 1);
            double dy = (getHeight() / 2 - translate.getY()) * (zoomLevel / oldZoomLevel - 1);
            // Update the scale and translate transformations
            scale.setX(zoomLevel);
            scale.setY(zoomLevel);
            translate.setX(translate.getX() - dx);
            translate.setY(translate.getY() - dy);
            redrawAll(Objfromid.NiveauFromId(HelloApplication.niv_actu));
            event.consume();
        });

        // Add the mouse drag event listener for panning
        addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            translate.setX(translate.getX() + event.getX() - getWidth() / 2);
            translate.setY(translate.getY() + event.getY() - getHeight() / 2);
            redrawAll(Objfromid.NiveauFromId(HelloApplication.niv_actu));
            event.consume();
        });

        // Add the mouse click event listener for drawing corners
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MainPageController.create_coin) {
                dessineCoinSmart(event);
            }
        });

        // Ajout d'un écouteur pour prendre en charge la sélection et la désélection des coins
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MainPageController.create_mur) {
                // Get the clicked corner
                //à supprimer
                //affichage de contrôle dans la console: on affiche tous les coins de ce niveau
                System.out.println("Coins du niveau " + HelloApplication.niv_actu + " :");
                for (Coin c : HelloApplication.ListCoin){
                    if (c.getNiv()==NiveauFromId(HelloApplication.niv_actu)) {
                        System.out.println(c);
                    }
                }

                Coin clickedCorner = getClickedCorner(event);

                if (clickedCorner != null) {
                    if (selectedCorners.contains(clickedCorner)) {
                        // Deselect the corner if it's already selected
                        selectedCorners.remove(clickedCorner);
                    } else {
                        // Select the corner
                        selectedCorners.add(clickedCorner);
                        drawSelectedCoins();

                        // Check if two corners are selected
                        if (selectedCorners.size() == 2) {
                            // Create a wall with the selected corners
                            Mur m = Mur.creerMur(selectedCorners.get(0), selectedCorners.get(1));
                            if (m!= null){
                                dessineMur(m);
                            } else {
                                System.out.println("Erreur : mur non créé");
                            }
                            selectedCorners.clear();
                            redrawAll(Objfromid.NiveauFromId(HelloApplication.niv_actu));
                        }
                    }
                }
            }
        });
        //Ajout d'un écouteur pour prendre en charge la sélection et la désélection des murs
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MainPageController.create_piece) {
                // Get the clicked wall
                Mur clickedWall = getSelectedWall(event);

                if (clickedWall != null) {
                    if (selectedMurs.contains(clickedWall)) {
                        // Deselect the wall if it's already selected
                        selectedMurs.remove(clickedWall);
                    } else {
                        // Select the wall
                        selectedMurs.add(clickedWall);
                        drawSelectedMurs();

                        // On vérifie si 4 murs sont sélectionnés
                        if (selectedMurs.size() == 4) {
                            // On créer la pièce avec les murs sélectionnés
                            Piece p = Piece.creerPieceDepuisMur(selectedMurs.get(0).getId(), selectedMurs.get(1).getId(), selectedMurs.get(2).getId(), selectedMurs.get(3).getId());
                            if (p!= null){
                                dessinePiece(p);
                            } else {
                                System.out.println("Erreur : pièce non créée");
                            }
                            selectedMurs.clear();
                            // On redessine tout
                            redrawAll(Objfromid.NiveauFromId(HelloApplication.niv_actu));
                            System.out.println("Pièce créée");
                        }
                    }
                }
            }
        });

        // Add a listener to handle the ESCAPE key press
        addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                selectedCorners.clear();
                selectedMurs.clear();
            }
        });
    }

    // Méthode qui renvoie le coin cliqué ou null si aucun coin n'est cliqué
    private Coin getClickedCorner(MouseEvent event) {
        double clickRadius = 5.0 / zoomLevel;
        for (Coin corner : HelloApplication.ListCoin) {
            if (corner.getNiv()==NiveauFromId(HelloApplication.niv_actu)) {
                double x = corner.getX() * zoomLevel + translate.getX();
                double y = corner.getY() * zoomLevel + translate.getY();
                double distance = Math.sqrt(Math.pow(x - event.getX(), 2) + Math.pow(y - event.getY(), 2));
                if (distance <= clickRadius) {
                    return corner;
                }
            }
        }
        return null;
    }
    //Méthode qui renvoie le mur cliqué ou null si aucun mur n'est cliqué
    public Mur getSelectedWall(MouseEvent event) {
        double clickRadius = 10.0 / zoomLevel; // Increase the click radius to 10 pixels
        for (Mur mur : HelloApplication.ListMur) {
            if(mur.getNiv() == NiveauFromId(HelloApplication.niv_actu)) {
                double x1 = mur.getDebut().getX() * zoomLevel + translate.getX();
                double y1 = mur.getDebut().getY() * zoomLevel + translate.getY();
                double x2 = mur.getFin().getX() * zoomLevel + translate.getX();
                double y2 = mur.getFin().getY() * zoomLevel + translate.getY();
                double distance1 = Math.sqrt(Math.pow(x1 - event.getX(), 2) + Math.pow(y1 - event.getY(), 2));
                double distance2 = Math.sqrt(Math.pow(x2 - event.getX(), 2) + Math.pow(y2 - event.getY(), 2));
                double wallLength = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); // Calculate the length of the wall
                if (distance1 <= clickRadius || distance2 <= clickRadius || distance1 + distance2 <= wallLength + clickRadius) {
                    System.out.println("Mur sélectionné : " + mur.getId());
                    return mur;
                }
            }
        }
        return null;
    }


    // Method that draws the selected corners in red
    private void drawSelectedCoins() {
        for (Coin corner : selectedCorners) {
            double x = corner.getX() * zoomLevel + translate.getX();
            double y = corner.getY() * zoomLevel + translate.getY();
            double radius = 5.0;
            this.getGraphicsContext2D().setFill(Color.RED);
            this.getGraphicsContext2D().fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }
    }
    private void drawSelectedMurs(){
        for (Mur mur : selectedMurs) {
            double lineWidth = 2.0;
            this.getGraphicsContext2D().setStroke(Color.BLUE);
            this.getGraphicsContext2D().setLineWidth(lineWidth);
            double x1 = mur.getDebut().getX() * zoomLevel + translate.getX();
            double y1 = mur.getDebut().getY() * zoomLevel + translate.getY();
            double x2 = mur.getFin().getX() * zoomLevel + translate.getX();
            double y2 = mur.getFin().getY() * zoomLevel + translate.getY();
            this.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
        }
    }
   // Méthode intelligent qui dessine un coin en fonction de la grille
    public void dessineCoinSmart(MouseEvent event) {
        double gridSize = 100 * zoomLevel;
        double subGridSize = gridSize / 10;
        double x = (event.getX() - translate.getX()) / zoomLevel;
        double y = (event.getY() - translate.getY()) / zoomLevel;
        double snappedX = Math.round(x / subGridSize) * subGridSize;
        double snappedY = Math.round(y / subGridSize) * subGridSize;
        Coin coin = Coin.creerCoin(snappedX, snappedY);
        if(coin != null) {
            dessineCoin(coin);
        } else {
            System.out.println("Erreur : coin non créé");
        }
    }
    public void dessineCoin(Coin coin) {
        coin.dessine(this.getGraphicsContext2D(), zoomLevel, translate);
    }
    public void dessineMur(Mur m) {
        m.dessine(this.getGraphicsContext2D(), zoomLevel, translate);
    }
    public void dessinePiece(Piece p) {
        p.dessine(this.getGraphicsContext2D(), zoomLevel, translate);
    }

    // Method that draws the grid on the canvas
    public void drawGrid() {
        double gridSize = 100 * zoomLevel;
        double subGridSize = gridSize / 10;
        double left = -translate.getX() / zoomLevel;
        double top = -translate.getY() / zoomLevel;
        double right = left + getWidth() / zoomLevel;
        double bottom = top + getHeight() / zoomLevel;
        this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());

        // Draw large tiles with black outlines
        this.getGraphicsContext2D().setStroke(Color.BLACK);
        this.getGraphicsContext2D().setLineWidth(1);
        for (double i = Math.floor(left / gridSize) * gridSize; i < right; i += gridSize) {
            for (double j = Math.floor(top / gridSize) * gridSize; j < bottom; j += gridSize) {
                this.getGraphicsContext2D().strokeRect(i * zoomLevel + translate.getX(), j * zoomLevel + translate.getY(), gridSize * zoomLevel, gridSize * zoomLevel);
            }
        }

        // Draw sub-tiles with grey lines
        this.getGraphicsContext2D().setStroke(Color.rgb(128, 128, 128, 0.5));
        this.getGraphicsContext2D().setLineWidth(0.5);
        for (double i = Math.floor(left / subGridSize) * subGridSize; i < right; i += subGridSize) {
            for (double j = Math.floor(top / subGridSize) * subGridSize; j < bottom; j += subGridSize) {
                this.getGraphicsContext2D().strokeRect(i * zoomLevel + translate.getX(), j * zoomLevel + translate.getY(), subGridSize * zoomLevel, subGridSize * zoomLevel);
            }
        }
    }
    //méthode pour tout redessiner sur un niveau
    public void redrawAll(Niveau niv) {
        drawGrid();
        for (Coin c : HelloApplication.ListCoin) {
            if (c.getNiv() == niv){
                dessineCoin(c);
            }
        }
        for (Mur m : HelloApplication.ListMur) {
            if(m.getNiv() == niv){
                dessineMur(m);
            }
        }
        for(Piece p : HelloApplication.ListPiece){
            if(p.getN() == niv){
                dessinePiece(p);
            }
        }
    }
    //Méthode permettant de créer un mur depuis un seul coin en le sélectionnant et en glissant la souris jusqu'à l'endroit où l'on veut placer le deuxième coin
    /*
    public void dessineMurEnCours(MouseEvent event) {
        if (selectedCorners.size() == 1) {
            double x1 = selectedCorners.get(0).getX() * zoomLevel + translate.getX();
            double y1 = selectedCorners.get(0).getY() * zoomLevel + translate.getY();
            //on récupère les coordonnées de la souris tant qu'on ne relâche pas le clic
            double x2 = event.getX();
            double y2 = event.getY();
            //on dessine le mur en cours en affichant la longueur du mur en temps réel
            this.getGraphicsContext2D().setStroke(Color.BLUE);
            this.getGraphicsContext2D().setLineWidth(2.0);
            this.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
            this.getGraphicsContext2D().setFill(Color.BLACK);
            this.getGraphicsContext2D().fillText("Longueur : " + Math.round(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) / zoomLevel * 100) / 100.0, x2, y2);
            //on regarde si le canvas est cliqué quelque part et si oui on crée le coin correspondant et on créer le mur
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                double gridSize = 100 * zoomLevel;
                double subGridSize = gridSize / 10;
                double x = (event.getX() - translate.getX()) / zoomLevel;
                double y = (event.getY() - translate.getY()) / zoomLevel;
                double snappedX = Math.round(x / subGridSize) * subGridSize;
                double snappedY = Math.round(y / subGridSize) * subGridSize;
                Coin coin = Coin.creerCoin(snappedX, snappedY);
                if (coin != null) {
                    dessineCoin(coin);
                    Mur mur = Mur.creerMur(selectedCorners.get(0), coin);
                    if (mur != null) {
                        dessineMur(mur);
                    } else {
                        System.out.println("Erreur : mur non créé");
                    }
                } else {
                    System.out.println("Erreur : coin non créé");
                }
                selectedCorners.clear();
                selectedMurs.clear();
            }
        }
    }
     */
}