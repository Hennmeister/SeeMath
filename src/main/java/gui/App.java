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
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();


        // TODO: Maybe move the following to main class - do we only need access to stage to make UI updates?

        VisualizationPresenter visPresenter = new VisualizationPresenter(stage);
        EquationManager eqnManager = new EquationManager(visPresenter);


        // Start WebSocket Server
        try {
            WebController server = new WebController(/* eqnManager */);
            server.startServer(eqnManager); } catch (InterruptedException e) {
            e.printStackTrace();
        }


        stage.setTitle("SeeMath");

        StackPane layout = new StackPane();

        Label label = new Label("HELLO, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        label.setFont(new Font("Arial", 24));


        Scene scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}