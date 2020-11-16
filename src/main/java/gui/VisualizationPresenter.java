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

    @Override
    public void updateVisualization(Equation eqn) {
        System.out.println("VisualizationPresenter" + eqn);

    }
}
