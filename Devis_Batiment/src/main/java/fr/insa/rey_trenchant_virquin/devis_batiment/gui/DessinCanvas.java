package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.Coin;
import fr.insa.rey_trenchant_virquin.devis_batiment.Gestion;
import fr.insa.rey_trenchant_virquin.devis_batiment.Mur;
import fr.insa.rey_trenchant_virquin.devis_batiment.Niveau;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

public class DessinCanvas extends Canvas {
    private double zoomLevel = 1.0;
    private Scale scale;
    private Translate translate;

    private final double MAX_ZOOM_LEVEL = 5.0;
    private final double MIN_ZOOM_LEVEL = 0.5;
    // Add a list to keep track of selected corners
    private List<Coin> selectedCorners = new ArrayList<>();

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
            drawGrid();
            event.consume();
        });

        // Add the mouse drag event listener for panning
        addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            translate.setX(translate.getX() + event.getX() - getWidth() / 2);
            translate.setY(translate.getY() + event.getY() - getHeight() / 2);
            drawGrid();
            event.consume();
        });

        // Add the mouse click event listener for drawing corners
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MainPageController.create_coin) {
                dessineCoinSmart(event);
            }
        });

        // Add a listener to handle corner selection and deselection
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MainPageController.create_mur) {
                // Get the clicked corner
                Coin clickedCorner = getClickedCorner(event);

                if (clickedCorner != null) {
                    if (selectedCorners.contains(clickedCorner)) {
                        // Deselect the corner if it's already selected
                        selectedCorners.remove(clickedCorner);
                    } else {
                        // Select the corner
                        selectedCorners.add(clickedCorner);
                        drawSelectedCorners();

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
                            // Redraw the corners
                            for (Coin corner : Gestion.ListCoin) {
                                if(corner.getNiv().getId() == Gestion.niv_actu) {
                                    dessineCoin(corner);
                                }
                            }
                        }
                    }
                }
            }
        });

// Add a listener to handle the ESCAPE key press
        addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                selectedCorners.clear();
            }
        });
    }

    // Method that returns the clicked corner, or null if no corner was clicked
    private Coin getClickedCorner(MouseEvent event) {
        double clickRadius = 5.0 / zoomLevel;
        for (Coin corner : Gestion.ListCoin) {
            double x = corner.getX() * zoomLevel + translate.getX();
            double y = corner.getY() * zoomLevel + translate.getY();
            double distance = Math.sqrt(Math.pow(x - event.getX(), 2) + Math.pow(y - event.getY(), 2));
            if (distance <= clickRadius) {
                return corner;
            }
        }
        return null;
    }

    // Method that draws the selected corners in red
    private void drawSelectedCorners() {
        for (Coin corner : selectedCorners) {
            double x = corner.getX() * zoomLevel + translate.getX();
            double y = corner.getY() * zoomLevel + translate.getY();
            double radius = 5.0;
            this.getGraphicsContext2D().setFill(Color.RED);
            this.getGraphicsContext2D().fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }
    }

    // Method that draws a corner on the canvas
    public void dessineCoin(Coin coin) {
        double x = coin.getX() * zoomLevel + translate.getX();
        double y = coin.getY() * zoomLevel + translate.getY();
        double radius = 5.0;
        this.getGraphicsContext2D().setFill(Color.BLACK);
        this.getGraphicsContext2D().fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
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

    public void dessineMur(Mur m) {
        double x1 = m.getDebut().getX() * zoomLevel + translate.getX();
        double y1 = m.getDebut().getY() * zoomLevel + translate.getY();
        double x2 = m.getFin().getX() * zoomLevel + translate.getX();
        double y2 = m.getFin().getY() * zoomLevel + translate.getY();
        double lineWidth = 2.0;
        this.getGraphicsContext2D().setStroke(Color.rgb(232, 155, 38));
        this.getGraphicsContext2D().setLineWidth(lineWidth);
        this.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
    }

    // Method that draws the grid on the canvas
    public void drawGrid() {
        double gridSize = 100 * zoomLevel;
        double subGridSize = gridSize / 10;
        double left = -translate.getX() / zoomLevel - getWidth() / 2 / zoomLevel;
        double top = -translate.getY() / zoomLevel - getHeight() / 2 / zoomLevel;
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
        for (Coin c : Gestion.ListCoin) {
            if (c.getNiv() == niv){
                dessineCoin(c);
            }
        }
        for (Mur m : Gestion.ListMur) {
            if(m.getNiv() == niv){
                dessineMur(m);
            }
        }
    }
}