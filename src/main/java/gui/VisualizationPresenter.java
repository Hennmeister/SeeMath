package gui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.equations.Equation;

public class VisualizationPresenter implements VisualizationCreator {

    private Stage stage;

    public VisualizationPresenter(Stage stage) {
        this.stage = stage;
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
        });
        System.out.println("VisualizationPresenter" + eqn);

    }
}
