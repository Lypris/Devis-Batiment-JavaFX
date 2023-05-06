package fr.insa.rey_trenchant_virquin.devis_batiment.gui;


import fr.insa.rey_trenchant_virquin.devis_batiment.Gestion;
import fr.insa.rey_trenchant_virquin.devis_batiment.Niveau;
import fr.insa.rey_trenchant_virquin.devis_batiment.Piece;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabNavigation {
    @FXML
    private Tab addTab;
    @FXML
    private TabPane tabPane;

    public void initialize() throws IOException {
        // Ajoute un onglet par défaut avec un niveau correspondant
        Niveau defaultNiveau = new Niveau(1, 2.5);
        Gestion.ListNiveau.add(defaultNiveau);
        Tab defaultTab = new Tab("Niveau 1");
        DessinCanvas defaultCanvas = new DessinCanvas(tabPane.getWidth(), tabPane.getHeight());
        defaultTab.setContent(defaultCanvas);
        defaultCanvas.drawGrid();
        defaultTab.setUserData(defaultNiveau);
        tabPane.getTabs().add(0, defaultTab);
        tabPane.getSelectionModel().select(defaultTab);

        // Récupère la liste des onglets
        List<Tab> tabs = tabPane.getTabs();

        // Ajoute un événement de sélection sur le Tab "+"
        addTab.setOnSelectionChanged(e -> {
            int tabIndex = tabs.size() - 1;

            Niveau newNiveau = Niveau.creerNiveau(tabIndex + 1);
            System.out.println(newNiveau); //à supprimer
            System.out.println(Gestion.ListNiveau); //à supprimer
            Tab newTab = new Tab("Niveau " + newNiveau.getId());
            //TODO Modifier ligne suivante pour implémenter DessinCanvas; zone de dessin
            //on ajoute un DessinCanvas au tabpane qui prend la taille du tabpane même lorsqu'on redimensionne le tabpane
            DessinCanvas canvas = new DessinCanvas(tabPane.getWidth(), tabPane.getHeight());
            newTab.setContent(canvas);
            canvas.drawGrid();

            newTab.setUserData(newNiveau);
            tabPane.getTabs().add(tabIndex, newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        //Ajout d'un écouteur sur la suppression et la mise à jour d'un onglet
        tabPane.getTabs().addListener((javafx.collections.ListChangeListener.Change<? extends Tab> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    // Supprime le niveau correspondant
                    for (Tab tab : change.getRemoved()) {
                        Gestion.ListNiveau.remove(tab.getUserData());
                    }

                    // Met à jour les identifiants des niveaux
                    int i = 1;
                    for (Tab tab : tabPane.getTabs()) {
                        Niveau niveau = (Niveau) tab.getUserData();
                        if (niveau != null){
                            niveau.changeId(i++);
                            tab.setText("Niveau " + niveau.getId());
                            tab.setContent(new TextArea("Niveau " + niveau.getId()));
                            System.out.println(Gestion.ListNiveau); //à supprimer
                        }
                    }
                } else if (change.wasUpdated()) {
                    // Met à jour l'identifiant du niveau correspondant
                    for (int i = 0; i < change.getList().size(); i++) {
                        Tab tab = change.getList().get(i);
                        if (tab.getUserData() != null) {
                            Niveau niveau = (Niveau) tab.getUserData();
                            niveau.changeId(i + 1);
                            tab.setText("Niveau " + niveau.getId());
                            tab.setContent(new TextArea("Niveau " + niveau.getId()));
                        }
                    }
                }
            }
        });
        //Ajoute un écouteur sur la sélection d'un onglet: met à jour le niveau actuel niv_actu
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Gestion.niv_actu = tabPane.getSelectionModel().getSelectedIndex();
            }
        });

        //Ajouter un écouteur sur le redimensionnement du tabpane: redimensionne les canvas des onglets
        tabPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            for (Tab tab : tabPane.getTabs()) {
                Node tabContent = tab.getContent();
                if (tabContent instanceof DessinCanvas) {
                    ((DessinCanvas) tabContent).setWidth(newValue.doubleValue());
                    ((DessinCanvas) tabContent).drawGrid();
                }
            }
        });
        //TODO: ajouter un écouteur sur le clique droit de la souris sur un tab
        //ajout d'une action lors d'un clique droit de la souris sur un tab: propose dans l'interface de définir une hauteur pour le niveau correspondant

    }

}

