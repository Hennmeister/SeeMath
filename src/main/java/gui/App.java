package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        stage.setTitle("SeeMath");

        StackPane layout = new StackPane();

        Label label = new Label("HELLO, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        label.setFont(new Font("Arial", 24));

        Button button = new Button("Click");
        button.setOnAction(e -> label.setText("Have you ever had upsexy for dinner?"));

        layout.getChildren().addAll(label, button);
        layout.setAlignment(button, Pos.BOTTOM_CENTER);

        Scene scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}