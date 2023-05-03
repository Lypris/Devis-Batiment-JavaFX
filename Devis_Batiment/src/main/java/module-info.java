module com.example.demo3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.insa.rey_trenchant_virquin.devis_batiment to javafx.fxml;
    exports fr.insa.rey_trenchant_virquin.devis_batiment;
    exports fr.insa.rey_trenchant_virquin.devis_batiment.gui;
    opens fr.insa.rey_trenchant_virquin.devis_batiment.gui to javafx.fxml;
}