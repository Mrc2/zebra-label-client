package com.oldworldind.app.gui.zebralabel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteFxLabelApp extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(RouteFxLabelApp.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        LOG.info("Starting Hello JavaFX and Maven demonstration application");

        String fxmlFile = "/com/oldworldind/app/gui/zebralabel/LabelPrintManagerLayout.fxml";
        LOG.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        LOG.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 800, 400);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Route Label App");
        stage.setScene(scene);
        stage.show();
    }
}
