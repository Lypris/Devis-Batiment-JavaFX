package fr.insa.rey_trenchant_virquin.devis_batiment.gui;

import fr.insa.rey_trenchant_virquin.devis_batiment.*;
import fr.insa.rey_trenchant_virquin.devis_batiment.Batiment;
import fr.insa.rey_trenchant_virquin.devis_batiment.Immeuble;
import fr.insa.rey_trenchant_virquin.devis_batiment.Importation;
import fr.insa.rey_trenchant_virquin.devis_batiment.Maison;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {
    @FXML
    private Button ButtonSelectFile, ButtonFinish;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private TextField FilePath, nomBatiment, nomClient, prenomClient, adresse, ville, postal;
    @FXML
    private RadioButton ButtonImmeuble,ButtonMaison;
    @FXML
    private Separator separator;
    @FXML
    private HBox HBoxOpen, HBoxFinish;
    @FXML
    private VBox VBoxCreate;

    private String choice2 = "Ouvrir un projet";
    private String choice1 ="Créer un nouveau projet";

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        /* La méthode initialize est utilisée pour initialiser
        les éléments de l'interface utilisateur et pour les lier aux événements
        qui se produisent dans l'application.
         */
        myChoiceBox.getItems().addAll(choice1, choice2);
        myChoiceBox.setValue(choice1);
        HBoxOpen.setVisible(false);
        createProject();
        // Ajout d'un écouteur sur le choix de l'utilisateur
        myChoiceBox.setOnAction(this::getChoice);
    }

    public void getChoice(ActionEvent event) {

        String choice = myChoiceBox.getValue();
        if (myChoiceBox.getValue().equals(choice1)){
            HBoxOpen.setVisible(false);
            VBoxCreate.setVisible(true);
            createProject();
        } else {
            VBoxCreate.setVisible(false);
            HBoxOpen.setVisible(true);
            openProject();
        }
    }
    public void createProject(){
        //on vide les champs de texte
        nomBatiment.clear();
        nomClient.clear();
        prenomClient.clear();
        adresse.clear();
        ville.clear();
        postal.clear();
        //on désactive les boutons radio
        ButtonImmeuble.setSelected(false);
        ButtonMaison.setSelected(false);
        //on change le texte du bouton
        ButtonFinish.setText("Créer");
        //on désactive le bouton créer
        ButtonFinish.setDisable(true);

        ButtonFinish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (ButtonImmeuble.isSelected()){
                    //créer un immeuble
                    HelloApplication.bâtiment = Immeuble.creerImmeuble( nomBatiment.getText(), nomClient.getText(), prenomClient.getText(), adresse.getText(), ville.getText(), postal.getText());
                    //on ouvre la fenêtre MainPage à l'aide de la méthode openMainPage
                    try{
                        openMainPage(actionEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (ButtonMaison.isSelected()){
                    //créer une maison
                    HelloApplication.bâtiment = Maison.creerMaison( nomBatiment.getText(), nomClient.getText(), prenomClient.getText(), adresse.getText(), ville.getText(), postal.getText());
                    //on ouvre la fenêtre MainPage à l'aide de la méthode openMainPage
                    try {
                        openMainPage(actionEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //ajouter la possibilité de passer d'un label à l'autre avec la touche entrée
        nomBatiment.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                nomClient.requestFocus();
            }
        });
        nomClient.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                prenomClient.requestFocus();
            }
        });
        prenomClient.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                adresse.requestFocus();
            }
        });
        adresse.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                postal.requestFocus();
            }
        });
        postal.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                ville.requestFocus();
            }
        });
        // Ajout d'un écouteur sur le champ de saisie du nom du bâtiment
        nomBatiment.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButtonCreateState(null);
        });

        // Ajout d'un écouteur sur les boutons radio
        ButtonImmeuble.setOnAction(this::updateButtonCreateState);
        ButtonMaison.setOnAction(this::updateButtonCreateState);
    }

    public void updateButtonCreateState(ActionEvent ev) {
        if (ButtonImmeuble.isSelected() || ButtonMaison.isSelected()) {
            if (!nomBatiment.getText().isEmpty()) {
                ButtonFinish.setDisable(false);
            } else {
                ButtonFinish.setDisable(true);
            }
        } else {
            ButtonFinish.setDisable(true);
        }
    }

    public void openProject(){
        //on change le texte du bouton
        ButtonFinish.setText("Ouvrir");
        ButtonFinish.setDisable(true);
        //on vide le champ FilePath
        FilePath.setText(null);
        ButtonSelectFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Ouvrir un projet");
                fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier Texte", "*.txt"));
                Stage stage = (Stage) ButtonSelectFile.getScene().getWindow();
                File file = fc.showOpenDialog(stage);
                if(file != null){
                    FilePath.setText(file.getPath());
                }
            }
        });
        ButtonFinish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                checkFile(actionEvent);
                //ouvrir le MainPage si le fichier est valide
                if(!ButtonFinish.isDisable()){
                    try {
                        openMainPage(actionEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //ouvrir le projet
                    System.out.println(FilePath.getText());

                }
            }
        });

        //Ajout d'un écouteur sur le champ de saisie du Fichier
        FilePath.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButtonOpenState(null);
        });
    }
    public void updateButtonOpenState(ActionEvent event) {
        String textFieldValue = FilePath.getText();
        if (textFieldValue == null || textFieldValue.isEmpty()) {
            ButtonFinish.setDisable(true);
        } else {
            ButtonFinish.setDisable(false);
        }
    }
    public void checkFile(ActionEvent event) {
        String textFieldValue = FilePath.getText();
        if (textFieldValue == null || textFieldValue.isEmpty()) {
            ButtonFinish.setDisable(true);
            return;
        }

        File file = new File(textFieldValue);
        if (file.isFile() && file.exists()) {
            ButtonFinish.setDisable(false);
        } else {
            ButtonFinish.setDisable(true);
            //créer une fenêtre d'alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le fichier n'existe pas");
            alert.setContentText("Veuillez sélectionner un fichier existant");
            alert.showAndWait();
        }
    }

    public void openMainPage(ActionEvent event) throws IOException {
        //on récupère la taille de l'écran de l'utilisateur
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //on récupère le stage
        root = FXMLLoader.load(HelloApplication.class.getResource("main-page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        scene.getStylesheets().add(HelloApplication.class.getResource("main-page-style.CSS").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //on affiche le nom du projet sans son chemin de localisation dans le titre de la fenêtre si on ouvre un projet
        if(ButtonFinish.getText().equals("Ouvrir")){
            stage.setTitle("Devis Bâtiment - " + FilePath.getText().substring(FilePath.getText().lastIndexOf("\\")+1));
        }
        //on affiche le nom du projet dans le titre de la fenêtre si on crée un nouveau projet
        else{
            stage.setTitle("Devis Bâtiment - " + nomBatiment.getText());
        }
        stage.setResizable(true);
        stage.show();
    }
    public static void importerSauvegarde(String nom){
        Importation importation = new Importation(Batiment.ListNiveau, Batiment.ListCoin, Batiment.ListMur, Batiment.ListPiece);
        // Appel de la méthode d'importation des configurations
        importation.importerConfigurations(nom);
    }

}

