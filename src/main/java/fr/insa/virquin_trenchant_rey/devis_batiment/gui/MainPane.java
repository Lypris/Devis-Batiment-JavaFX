/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.virquin_trenchant_rey.devis_batiment.gui;

import fr.insa.virquin_trenchant_rey.devis_batiment.Batiment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author rudyv
 */
public class MainPane extends BorderPane {
    
    private Batiment model;
    private Controleur controleur;
    
    private RadioButton rbSelect;
    private RadioButton rbPoints;
    private RadioButton rbMurs;
    
    private Button bSouris;
            
    private DessinCanvas cDessin;
    
    public MainPane(){
        this(new Batiment());
    }
    public MainPane(Batiment model) {
        this.model = model;
        this.controleur = new Controleur(this);
        
        this.rbSelect = new RadioButton("Select");
        this.rbPoints = new RadioButton("Points");
        this.rbMurs = new RadioButton("Murs");
        
        VBox vbGauche = new VBox(this.rbSelect, this.rbPoints, this.rbMurs);
        this.setLeft(vbGauche);
        
        this.bSouris = new Button("Souris");
        this.bSouris.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.out.println("bouton souris cliqué");
            }
        });
                
        VBox vbDroit = new VBox(this.bSouris);
        this.setRight(vbDroit);
        
        this.cDessin = new DessinCanvas(this);
        this.setCenter(this.cDessin);
    }
    
    public Batiment getModel() {
        return model;
    }

    public Controleur getControleur() {
        return controleur;
    }
}
