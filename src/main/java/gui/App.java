package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.WebController;
import logic.equations.EquationManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException{
        WebController server = new WebController();
        VisualizationPresenter visPresenter = new VisualizationPresenter(stage, server);
        EquationManager eqnManager = new EquationManager(visPresenter);


        // Start WebSocket Server
        try {
            server.startServer(eqnManager); } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LandingPage landing = new LandingPage();
        stage.setTitle("SeeMath");
      
        stage.setScene(landing.getLandingPage(stage));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}