package fr.insa.rey_trenchant_virquin.devis_batiment.gui;


import fr.insa.rey_trenchant_virquin.devis_batiment.Gestion;
import fr.insa.rey_trenchant_virquin.devis_batiment.Niveau;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.util.List;

public class TabNavigation {
    @FXML
    private Tab addTab;
    @FXML
    public TabPane tabPane;

    public void initialize() throws IOException {
        // Ajoute un onglet par défaut avec un niveau correspondant
        Niveau defaultNiveau = new Niveau(1, 2.5);
        Gestion.ListNiveau.add(defaultNiveau);
        Tab defaultTab = new Tab("Niveau 1");
        DessinCanvas defaultCanvas = new DessinCanvas(0, 0); // set initial width and height to 0
        defaultTab.setContent(defaultCanvas);
        defaultTab.setUserData(defaultNiveau);
        tabPane.getTabs().add(0, defaultTab);
        tabPane.getSelectionModel().select(defaultTab);

        // Redraw the grid when the default tab is shown
        defaultTab.setOnSelectionChanged(event -> {
            if (defaultTab.isSelected()) {
                defaultCanvas.setWidth(tabPane.getWidth());
                defaultCanvas.setHeight(tabPane.getHeight());
                defaultCanvas.drawGrid();
            }
        });

        // Récupère la liste des onglets
        List<Tab> tabs = tabPane.getTabs();

        // Ajoute un événement de sélection sur le Tab "+"
        addTab.setOnSelectionChanged(event ->  {
            int tabIndex = tabs.size() - 1;
            // on crée le nouveau niveau avec la hauteur saisie par l'utilisateur
            Niveau newNiveau = Niveau.creerNiveau(tabIndex + 1);
            System.out.println(Gestion.ListNiveau);
            // on crée le nouveau tab avec un DessinCanvas
            Tab newTab = new Tab("Niveau " + newNiveau.getId());
            DessinCanvas canvas = new DessinCanvas(tabPane.getWidth(), tabPane.getHeight());
            newTab.setContent(canvas);
            canvas.drawGrid();
            newTab.setUserData(newNiveau);
            // on ajoute le nouveau tab au tabPane
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
                Gestion.niv_actu = tabPane.getSelectionModel().getSelectedIndex()+1;
                System.out.println("Niveau actuel: " + Gestion.niv_actu);
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
        tabPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                //afficher un petit menu déroulant avec le choix: modifier la hauteur du niveau
                ContextMenu contextMenu = new ContextMenu();
                MenuItem changeHeightMenuItem = new MenuItem("Changer la hauteur du niveau");
                changeHeightMenuItem.setOnAction(e -> {
                    // Appel de la méthode pour changer la hauteur du niveau
                    changeLevelHeight();
                });
                contextMenu.getItems().add(changeHeightMenuItem);

                // Add a context menu to each tab
                for (Tab tab : tabPane.getTabs()) {
                    tab.setContextMenu(contextMenu);
                }

            }
        });

    }

    public TabPane getTabPane() {
        return tabPane;
    }
    // Method to change the height of the level
    private void changeLevelHeight() {
        try {
            // on ouvre la fenêtre pour saisir la hauteur du nouveau niveau
            Niveau.openNiveauPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // on attend que l'utilisateur ait saisi une hauteur et ait cliqué sur le bouton de création
        while (NewNiveauController.getHauteur() == 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // on récupère la hauteur saisie
        double h = NewNiveauController.getHauteur();
        // on récupère le niveau correspondant à l'onglet sélectionné
        Niveau niveau = (Niveau) tabPane.getSelectionModel().getSelectedItem().getUserData();
        // on met à jour la hauteur du niveau
        niveau.setH(h);
        System.out.println("Hauteur du niveau " + niveau.getId() + " : " + niveau.getH());
    }
}