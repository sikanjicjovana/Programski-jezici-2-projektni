module net.etf.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens net.etf.project to javafx.fxml;
    //exports net.etf.project;
    exports net.etf.project.model.vehicles;
    exports net.etf.project.model.rental;
    exports net.etf.project.financial;
    exports net.etf.project.statistics;
    exports net.etf.project.serialization;
    exports net.etf.project.util;
    exports net.etf.project.simulation;
    exports net.etf.project.gui;
    opens net.etf.project.gui to javafx.fxml;
}