package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.WebController;
import logic.equations.EquationManager;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        VisualizationPresenter visPresenter = new VisualizationPresenter(stage);
        EquationManager eqnManager = new EquationManager(visPresenter);


        // Start WebSocket Server
        try {
            WebController server = new WebController();
            server.startServer(eqnManager); } catch (InterruptedException e) {
            e.printStackTrace();
        }


        stage.setTitle("SeeMath");

        StackPane layout = new StackPane();

//        Label label = new Label("SeeMath Hypatia App");
//        label.setFont(new Font("Arial", 24));
//        layout.getChildren().addAll(label);

        Scene scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}