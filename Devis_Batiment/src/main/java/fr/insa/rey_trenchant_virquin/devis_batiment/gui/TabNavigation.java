package fr.insa.rey_trenchant_virquin.devis_batiment.gui;


import fr.insa.rey_trenchant_virquin.devis_batiment.Gestion;
import fr.insa.rey_trenchant_virquin.devis_batiment.Niveau;
import fr.insa.rey_trenchant_virquin.devis_batiment.Objfromid;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class TabNavigation {
    @FXML
    private Tab addTab;
    @FXML
    public TabPane tabPane;

    public void initialize() throws IOException {
        //on distingue 2 cas: l'ouverture d'un fichier et la création d'un nouveau fichier

        //si on ouvre un fichier
        if(StartPageController.project_open == true){
            //on récupère la liste des niveaux
            List<Niveau> listNiveau = Gestion.ListNiveau;
            //on parcourt la liste des niveaux
            for (Niveau niveau : listNiveau) {
                //on crée un nouveau tab
                Tab newTab = new Tab("Niveau " + listNiveau.indexOf(niveau) + 1);
                //on crée un nouveau canvas
                DessinCanvas newCanvas = new DessinCanvas(0, 0);
                //on définit la taille du canvas
                newCanvas.setWidth(tabPane.getWidth());
                newCanvas.setHeight(tabPane.getHeight());
                //on crée un nouveau pane
                Pane newPane = new Pane(newCanvas);
                //on définit le contenu du tab
                newTab.setContent(newPane);
                //on définit le niveau du tab
                newTab.setUserData(niveau);
                //on dessine la grille
                newCanvas.drawGrid();
                newCanvas.redrawAll(niveau);
                //on ajoute le tab au tabPane
                tabPane.getTabs().add(newTab);
            }
        }
        else {
            // Ajoute un onglet par défaut avec un niveau correspondant
            Niveau defaultNiveau = new Niveau(1, 2.5);
            Gestion.ListNiveau.add(defaultNiveau);
            System.out.println(Gestion.ListNiveau);
            Tab defaultTab = new Tab("Niveau 1");
            DessinCanvas defaultCanvas = new DessinCanvas(0, 0);
            defaultCanvas.setWidth(tabPane.getWidth());
            defaultCanvas.setHeight(tabPane.getHeight());
            Pane defaultPane = new Pane(defaultCanvas);
            defaultTab.setContent(defaultPane);
            defaultTab.setUserData(defaultNiveau);
            defaultCanvas.drawGrid();
            tabPane.getTabs().add(0, defaultTab);
        }
        //Dans tous les cas, on ajoute un contextMenu à tous les tabs déjà existants
        ContextMenu contextMenu = new ContextMenu();
        MenuItem changeHeightMenuItem = new MenuItem("Changer la hauteur du niveau");
        contextMenu.getItems().add(changeHeightMenuItem);
        for (Tab tab : tabPane.getTabs()) {
            tab.setContextMenu(contextMenu);
        }
        //On sélectionne le premier tab
        tabPane.getSelectionModel().select(0);

        // Ajoute un événement de sélection sur la sélection du menu contextuel
        changeHeightMenuItem.setOnAction(e -> {
            // Appel de la méthode pour changer la hauteur du niveau
            changeLevelHeight();
        });

        // Ajoute un événement de sélection sur le Tab "+"
        addTab.setOnSelectionChanged(event ->  {
            int tabIndex = tabPane.getTabs().size() - 1;
            // on crée le nouveau niveau avec la hauteur saisie par l'utilisateur
            Niveau newNiveau = Niveau.creerNiveau(tabIndex + 1);
            System.out.println(Gestion.ListNiveau);
            // on crée le nouveau tab avec un DessinCanvas
            Tab newTab = new Tab("Niveau " + newNiveau.getId());
            DessinCanvas canvas = new DessinCanvas(tabPane.getWidth(), tabPane.getHeight());
            canvas.drawGrid();
            newTab.setContent(new Pane(canvas));
            newTab.setContextMenu(contextMenu);
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
                        Niveau niveau = (Niveau) tab.getUserData();
                        Gestion.ListNiveau.remove(niveau);
                    }
                    // Met à jour les identifiants des niveaux
                    int i = 1;
                    for (Tab tab : tabPane.getTabs()) {
                        Niveau niveau = (Niveau) tab.getUserData();
                        if (niveau != null){
                            niveau.changeId(i++);
                            tab.setText("Niveau " + niveau.getId());
                            //TODO : mettre à jour le dessin du canvas en récupérant les données du niveau
                            tab.setContent(new DessinCanvas(0, 0));
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
                            //TODO : mettre à jour le dessin du canvas en récupérant les données du niveau
                            tab.setContent(new DessinCanvas(0,0));
                        }
                    }
                }
            }
        });
        // Listener pour le changement d'onglet: on met à jour le niveau actuel et on redessine le canvas
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getContent() instanceof Pane) {
                Pane pane = (Pane) newValue.getContent();
                if (!pane.getChildren().isEmpty()) { // Check if the list is not empty
                    Node node = pane.getChildren().get(0);
                    if (node instanceof DessinCanvas) {
                        //TODO : récupérer les données du niveau et les passer en paramètre de redrawAll
                        int id = ((Niveau) newValue.getUserData()).getId();
                        ((DessinCanvas) node).redrawAll(Objfromid.NiveauFromId(id));
                        ((DessinCanvas) node).setWidth(tabPane.getWidth());
                        ((DessinCanvas) node).setHeight(tabPane.getHeight());
                    }
                }
            }
            // On met à jour le niveau actuel
            Gestion.niv_actu = tabPane.getSelectionModel().getSelectedIndex() + 1;
            System.out.println("Niveau actuel: " + Gestion.niv_actu);
        });

        //Ajouter un écouteur sur le redimensionnement du tabpane: redimensionne les canvas des onglets
        tabPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) { // Check if the new value is different from the old value
                for (Tab tab : tabPane.getTabs()) {
                    if (tab.getContent() instanceof Pane) {
                        Pane pane = (Pane) tab.getContent();
                        if (!pane.getChildren().isEmpty()) { // Check if the list is not empty
                            Node node = pane.getChildren().get(0);
                            if (node instanceof DessinCanvas) {
                                ((DessinCanvas) node).setWidth(newValue.doubleValue());
                                //TODO: récupérer les données du niveau et les passer en paramètre de redrawAll
                                int id = ((Niveau) tab.getUserData()).getId();
                                ((DessinCanvas) node).redrawAll(Objfromid.NiveauFromId(id));
                            }
                        }
                        System.out.println("Width: " + newValue.doubleValue()); //TODO: supprimer
                    }
                }
            }
        });

        tabPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(oldValue)) { // Check if the new value is different from the old value
                for (Tab tab : tabPane.getTabs()) {
                    if (tab.getContent() instanceof Pane) {
                        Pane pane = (Pane) tab.getContent();
                        if (!pane.getChildren().isEmpty()) { // Check if the list is not empty
                            Node node = pane.getChildren().get(0);
                            if (node instanceof DessinCanvas) {
                                ((DessinCanvas) node).setHeight(newValue.doubleValue());
                                //TODO: récupérer les données du niveau et les passer en paramètre de redrawAll
                                int id = ((Niveau) tab.getUserData()).getId();
                                ((DessinCanvas) node).redrawAll(Objfromid.NiveauFromId(id));
                            }
                        }
                        System.out.println("Height: " + newValue.doubleValue()); //TODO: supprimer
                    }
                }
            }
        });
        // Add a listener to the tab pane to handle double-click events
        tabPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                // Get the selected tab
                Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

                // Check if the content of the selected tab is an instance of DessinCanvas
                if (selectedTab.getContent() instanceof DessinCanvas) {
                    // Call the method to change the level height
                    changeLevelHeight();
                }
            }
        });
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