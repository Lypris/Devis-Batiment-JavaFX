package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.Coin;
import fr.insa.rey_trenchant_virquin.devis_batiment.Gestion;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class DessinCanvas extends Canvas {
    private double zoomLevel = 1.0;
    private Scale scale;
    private Translate translate;

    private final double MAX_ZOOM_LEVEL = 5.0;
    private final double MIN_ZOOM_LEVEL = 0.5;

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
                double x = event.getX() / zoomLevel - translate.getX() / zoomLevel;
                double y = event.getY() / zoomLevel - translate.getY() / zoomLevel;
                Coin coin = Coin.creerCoin(x, y);
                Gestion.ListCoin.add(coin);
                dessineCoin(coin);
            }
        });
    }

    // Method that draws a corner on the canvas
    public void dessineCoin(Coin coin) {
        double x = coin.getX() * zoomLevel + translate.getX();
        double y = coin.getY() * zoomLevel + translate.getY();
        double radius = 5.0;
        this.getGraphicsContext2D().setFill(Color.BLACK);
        this.getGraphicsContext2D().fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    // Method that draws the grid on the canvas
    public void drawGrid() {
        double gridSize = 100 * zoomLevel;
        double subGridSize = gridSize / 10;
        double left = -translate.getX() / zoomLevel;
        double top = -translate.getY() / zoomLevel;
        double right = (getWidth() - translate.getX()) / zoomLevel;
        double bottom = (getHeight() - translate.getY()) / zoomLevel;
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
}