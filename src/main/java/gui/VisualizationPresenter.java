package gui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.equations.Equation;
import logic.equations.expression_tree.Expression;

public class VisualizationPresenter implements VisualizationCreator {

    private Stage stage;

    public VisualizationPresenter(Stage stage) {
        this.stage = stage;
    }

    /**
     * Creates an appropriate visualization for equation by calling the correct visualizer
     * @param exp equation to be visualized
     * @return the visualization of expression if valid or an error message
     */
    public Pane makeVisualization(Expression exp){
        if (exp.isValid()){
            return exp.visualization();
        }
        HBox layout = new HBox();
        Label label = new Label("Unsupported visualization");
        layout.getChildren().add(label);
        return layout;
    }

    /**
     * Update the UI with the new equation visualization
     * @param eqn The new equation that whose visualization should be shown
     */
    @Override
    public void updateVisualization(Equation eqn) {
        // Runs the UI related logic on the javaFX thread
        Platform.runLater(() -> {

            // TODO: For some reason my intelliJ does not compile Labels, so please test these out in our own machines
            // And let me know if that's just a problem on my end

            //Label label = new Label(eqn.toString());
            //label.setFont(new Font("Arial", 24));

            // StackPane so we can stack new equations as they get visualized
            StackPane layout = new StackPane();

            // TODO: Refactor equation so that equality operator is included in Expression Tree
            // and equation now only holds a reference to the tree as a whole (no more left, right subtrees)
            Pane drawEqn = makeVisualization(eqn.getTree());

            layout.getChildren().add(drawEqn);

            Scene scene = new Scene(layout, 640, 480);
            stage.setScene(scene);
            stage.show();
        });
    }
}
