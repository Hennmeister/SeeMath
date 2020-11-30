package gui;

import gui.vis.AdditionVisualizer;
import gui.vis.GraphVisualizer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.scene.text.Font;
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
     * @param left expression tree for left-hand side
     * @param equality string representation of the equality operator
     * @param right expression tree for right-hand side
     * @return the visualization of expression if valid, or an error message
     */
    public Pane makeVisualization(Expression left, String equality, Expression right){
        HBox layout = new HBox();
        layout.setSpacing(0);
        layout.setAlignment(Pos.TOP_LEFT);
        if (left.isValid() && right.isValid()) {
            AdditionVisualizer vis = new AdditionVisualizer();
            layout.getChildren().addAll(left.visualization(), vis.drawString(equality), right.visualization());
        }
        else{
            Label label = new Label("unsupported visualization");
            layout.getChildren().add(label);
        }
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

            // StackPane so we can stack new equations as they get visualized
            StackPane layout = new StackPane();

            // displays equation id
            Label label = new Label("Equation ID: " + eqn.getProblemId());
            label.setFont(new Font("Arial", 24));

            Pane drawEqn;

            if (eqn.isAlgebraic()){
                GraphVisualizer vis = new GraphVisualizer();
                //drawEqn = vis.drawExpression(eqn.getLeftTree(), eqn.getRightTree());
                drawEqn = new Pane(); //place holder for ^^
            } else{
                drawEqn = makeVisualization(eqn.getLeftTree(), eqn.getEqualityOperator(), eqn.getRightTree());
            }

            layout.getChildren().addAll(label, drawEqn);

            BorderPane ui = (BorderPane) stage.getScene().getRoot();
            VBox visPane = (VBox) ui.getCenter();
            visPane.getChildren().add(layout);
            stage.setScene(stage.getScene());
            stage.show();
        });
    }
}
