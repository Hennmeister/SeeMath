package gui;

import gui.vis.AdditionVisualizer;
import gui.vis.GraphVisualizer;
import gui.vis.Visualizer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.equations.Equation;
import logic.equations.expression_tree.Expression;

import java.util.ArrayList;
import java.util.Collections;

public class VisualizationPresenter implements VisualizationCreator {

    private Stage stage;
    private ArrayList<Node> visList;

    public VisualizationPresenter(Stage stage) {
        this.stage = stage;
        this. visList = new ArrayList<Node>();
    }

    /**
     * Creates an appropriate visualization for equation by calling the correct visualizer
     * @param left expression tree for left-hand side
     * @param equality string representation of the equality operator
     * @param right expression tree for right-hand side
     * @return the visualization of expression if valid, or an error message
     */
    public Pane makeVisualization(Expression left, String equality, Expression right){
        FlowPane layout = new FlowPane();
        layout.setPrefWrapLength(900);
        layout.setVgap(10);
        layout.setAlignment(Pos.TOP_CENTER);
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

            // displays equation id
            Label label = new Label("Equation: " + eqn.toStringLabel() + " - ID: " + eqn.getProblemId());
            label.setFont(new Font("Arial", 24));

            Pane drawEqn;

            if (eqn.isAlgebraic()){
                GraphVisualizer vis = new GraphVisualizer();
                //drawEqn = vis.drawExpression(eqn.getLeftTree(), eqn.getRightTree());
                drawEqn = new Pane(); //place holder for ^^
            } else{
                drawEqn = makeVisualization(eqn.getLeftTree(), eqn.getEqualityOperator(), eqn.getRightTree());
            }

            // Store the Visualization and Label for later access, add a Line between equations for visual clarity
            if (visList.size() > 0){
                Line line = new Line(0, 0, 500, 0);
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(3.0);
                dropShadow.setOffsetX(3.0);
                dropShadow.setOffsetY(3.0);
                dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
                line.setEffect(dropShadow);
                visList.add(line);
            }
            visList.add(drawEqn);
            visList.add(label);

            // Navigate through the UI objects to get to the visPane
            BorderPane ui = (BorderPane) stage.getScene().getRoot();
            ScrollPane sp = (ScrollPane) ui.getCenter();
            VBox visPane = (VBox) sp.getContent();

            // Empty the visPane, before adding in all the stored visualizations
            visPane.getChildren().clear();
            for (int i = visList.size() - 1; i >= 0; i -= 1) {
                 visPane.getChildren().add(visList.get(i));
            }
            stage.setScene(stage.getScene());
            stage.show();
        });
    }
}
