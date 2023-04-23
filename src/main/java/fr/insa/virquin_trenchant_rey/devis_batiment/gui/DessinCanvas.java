/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author rudyv
 */
public class DessinCanvas extends Pane {
    
    private MainPane main;
    
    private Canvas realCanvas;
    
    public DessinCanvas(MainPane main){
        this.main = main;
        this.realCanvas = new Canvas(this.getWidth(), this.getHeight());
        this.getChildren().add(this.realCanvas); //on ajoute le Canvas (sous-composant) dans le Pane
        this.realCanvas.heightProperty().bind(this.heightProperty()); //on lie la hauteur du Canvas avec celle du Pane qui le contient
        this.realCanvas.heightProperty().addListener((o) -> { //permet de redessiner lorsque la hauteur est modifée
            this.redrawAll();
        });
        this.realCanvas.widthProperty().bind(this.widthProperty()); //on lie la largeur du Canvas avec celle du Pane qui le contient
        this.realCanvas.widthProperty().addListener((o) -> { //permet de redessiner lorsque la largeur est modifée
            this.redrawAll();
        });
        this.redrawAll();
    }
    public void redrawAll(){
        GraphicsContext context = this.realCanvas.getGraphicsContext2D();
        Batiment model = this.main.getModel();
        context
    }
}
