package gui.vis;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import logic.equations.expression_tree.Expression;
import java.util.ArrayList;
import static java.lang.Math.abs;


public class MultiplicationVisualizer extends Visualizer {
    /**
     * Creates a FlowPane containing {@code num} copies of {@code discreteShape}.
     * @param num The amount of things.
     * @return A FlowPane containing the things.
     */
    @Override
    public Pane drawInt(int num){
        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setAlignment(Pos.BASELINE_CENTER);

        for (int i=0; i<abs(num); i++){
            Circle shape = new Circle(10); //using a circle to differentiate from addition
            pane.getChildren().add(shape);

            shape.setFill(Color.DARKCYAN);
        }
        return pane;
    };
    /**
     * Given an ExpressionTree, create a nested HBox structure visualizing every node within the ExpressionTree.
     * Assumes that all interior nodes will be strings representing operators and all leaves will be integers.
     * @param tree The root ExpressionTree to be visualized.
     * @return A FlowPane containing a visualization of {@code tree}.
     */
    @Override
    public Pane drawExpression(Expression tree) {
        Pane vis = mouseOver(tree); //get the visualization
        GridPane border = new GridPane(); //create a GridPane to add the borders of the visualization
        //the string representation of the number that is being repeated
        Pane left = drawString(findLeftMostLeaf(tree).getValue());
        Pane top;
        if (tree.isLeaf()) { //if tree is leaf, then its equivalent to multiplying by 1
            top = drawString("* 1");
        }
        else { //otherwise, get string containing all the multiplication factors
            top = getMultiplicationFactors(tree);
        }
        //the following lines of code will set up the GridPane to hold the visualization and the borders
        //NOTE: indexes .setConstraints are col, row
        GridPane.setConstraints(vis, 1, 1); //put visualization in centre
        GridPane.setConstraints(left, 0, 1); //put left in the cell to the left of vis
        GridPane.setConstraints(top, 1, 0); //put top in the cell right above vis
        GridPane.setFillHeight(left, false); //this is for formatting
        GridPane.setHalignment(top, HPos.CENTER); //this is for formatting
        border.getChildren().addAll(vis, left, top); //add everything to the GridPane
        return border;
        }

    /**
     * Private helper to find the base number that is being repeated in the visualization. Was used to help with borders
     * @param tree - the expression from drawExpression
     * @return the leftmost leaf's value
     */
    private Expression findLeftMostLeaf(Expression tree) {
        if (tree.isLeaf()) {
            return tree;
        }
        else {
            return findLeftMostLeaf(tree.getLeft());
        }
    }
    private Pane getMultiplicationFactors(Expression tree) {
        Expression left = findLeftMostLeaf(tree);
        ArrayList<Double> factors = tree.getLeaves();
        factors.remove(left.evaluate());
        String result = "";
        //double result = 1;
        for (Double d: factors) {
            result += "* " + d.intValue() + " ";
            //result = result * d;
        }
        //return drawString("* " + (int) result);
        return drawString(result);
    }
    private Pane drawRecursive(Expression tree) {
        if (tree.isLeaf()){
            return drawInt(Integer.parseInt(tree.getValue()));
        }
        else {
            // Set up a Pane to hold the visualization
            HBox masterPane = new HBox();
            masterPane.setSpacing(12);
            masterPane.setAlignment(Pos.BASELINE_CENTER);
            //draw result:
            //repeat the vis for left subtree, right subtree amount of times
            for(int i = 1; i <= abs(tree.getRight().evaluate()); i++) {
                masterPane.getChildren().add(drawRecursive(tree.getLeft()));
            }

            return masterPane;
        }
    }
    private Pane mouseOver(Expression tree) {
        // Set up a StackPane to handle mouse-over behaviour on top of the visualization
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setMaxSize(nodeSize, nodeSize);
        stackPane.getChildren().add(drawRecursive(tree));
        if (tree.evaluate() < 0) {
            // Code for mouse-over behaviour:
            stackPane.setOnMouseEntered((EventHandler<Event>) event -> {
                stackPane.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-background-radius: 10;");
                stackPane.getChildren().add(drawString(""));
            });
        }
        else {
            // Code for mouse-over behaviour:
            stackPane.setOnMouseEntered((EventHandler<Event>) event -> {
                stackPane.setStyle("-fx-background-color: rgba(0, 204, 0, 0.5); -fx-background-radius: 10;");
                stackPane.getChildren().add(drawString(""));
            });
        }
        stackPane.setOnMouseExited((EventHandler<Event>) e -> {
            stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 10;");
            stackPane.getChildren().remove(1);
        });

        return stackPane;
    }
}
