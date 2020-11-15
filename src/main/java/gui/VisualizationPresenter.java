package gui;

import javafx.stage.Stage;
import logic.equations.Equation;

public class VisualizationPresenter implements VisualizationCreator {

    private Stage stage;

    public VisualizationPresenter(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void updateVisualization(Equation eqn) { }
}
