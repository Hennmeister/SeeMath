package gui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import javafx.stage.Stage;
import logic.WebController;
import logic.equations.Equation;

import java.util.Base64;

public class VisualizationPresenter implements VisualizationCreator {

    private Stage stage;
    private WebController serverController;

    public VisualizationPresenter(Stage stage, WebController serverController) {
        this.stage = stage;
        this.serverController = serverController;
    }

    /**
     * Update the UI
     * @param eqn The new equation that whose visualization should be shown
     */
    @Override
    public void updateVisualization(Equation eqn) {
        // Runs the UI related logic on the javaFX thread
        Platform.runLater(() -> {
            // Sample of changing UI - to be changed
            Label label = new Label(eqn.toString());
            label.setFont(new Font("Arial", 24));
            StackPane layout = new StackPane();

            layout.getChildren().addAll(label);

            Scene scene = new Scene(layout, 640, 480);
            stage.setScene(scene);
            stage.show();
            serverController.sentVisualHint(eqn, "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUA\n" +
                    "AAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO\n" +
                    "    9TXL0Y4OHwAAAABJRU5ErkJggg==");
        });
    }
}
