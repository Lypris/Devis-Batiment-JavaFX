
package fr.insa.virquin_trenchant_rey.devis_batiment.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author rudyv
 */
public class DessinCanvas1 extends Canvas {
    
    public DessinCanvas1(double w, double h) {
        super(w,h);
        GraphicsContext context = this.getGraphicsContext2D();
        context.setFill(Color.RED);
        context.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
