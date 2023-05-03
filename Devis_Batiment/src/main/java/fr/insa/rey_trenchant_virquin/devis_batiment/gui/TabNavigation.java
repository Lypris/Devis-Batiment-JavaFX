package fr.insa.rey_trenchant_virquin.devis_batiment.gui;


import fr.insa.rey_trenchant_virquin.devis_batiment.Gestion;
import fr.insa.rey_trenchant_virquin.devis_batiment.Niveau;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.List;

public class TabNavigation {
    @FXML
    private Tab addTab;
    @FXML
    private TabPane tabPane;

    public void initialize() throws IOException {
        // Ajoute un onglet par défaut avec un niveau correspondant
        Niveau defaultNiveau = Niveau.creerNiveau(1);
        Tab defaultTab = new Tab("Niveau 1");
        defaultTab.setContent(new TextArea("Niveau 1"));
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
            //TODO Modifier ligne suivante pour implémenter Canvas; zone de dessin
            newTab.setContent(new TextArea("Niveau " + newNiveau.getId()));
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
        //ajout d'une action lors d'un clique droit de la souris sur un tab: propose dans l'interface de définir une hauteur pour le niveau correspondant

    }

}

