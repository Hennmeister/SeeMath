package gui.vis;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import logic.equations.expression_tree.Expression;

import java.util.Objects;
import static java.lang.Math.abs;

public class AdditionVisualizer extends Visualizer{
    /**
     * Creates a FlowPane containing {@code num} Rectangles. If {@code num} is positive Rectangles will be green,
     * if {@code num} is negative Rectangles will be red.
     * @param num The amount of things to be visualized.
     * @return A FlowPane containing the shapes of the visualization.
     */

    public Pane drawInt(int num){
        FlowPane pane = new FlowPane();
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setAlignment(Pos.CENTER);
        pane.setPrefWrapLength(nodeSize);
        pane.setMaxSize(nodeSize, nodeSize);

        int absNum = abs(num);

        for (int i=0; i<absNum; i++){
            Rectangle node;

            // Construct a rectangle of the appropriate size, given <num>
            /*
            if (num <= 30){
                node = new Rectangle(25, 25);
            } else if (num <= 60) {
                node = new Rectangle(10, 10);
            } else {
                node = new Rectangle(5, 5);
            }*/

            node = new Rectangle(25, 25);

            // If <num> is 0 or positive color the node green, otherwise color it red
            if (num >= 0){
                node.setFill(positiveColor);
            } else {
                node.setFill(negativeColor);
            }
            pane.getChildren().add(node);
        }
        if (num >= 0){
            pane.setEffect(getDropShadow());
        } else {
            pane.setEffect(getInnerShadow());
        }

        //pane.setStyle("-fx-border-color: black"); // for debug
        // Set up a StackPane to handle mouse-over behaviour on top of the visualization
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setMaxSize(nodeSize, nodeSize);
        stackPane.getChildren().add(pane);

        // Code for mouse-over behaviour:
        stackPane.setOnMouseEntered((EventHandler<Event>) event -> {
            //stackPane.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5); -fx-background-radius: 10;");
            Pane strPane = drawString(Integer.toString(num));
            strPane.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5); -fx-background-radius: 10;");
            stackPane.getChildren().add(strPane);
        });
        stackPane.setOnMouseExited((EventHandler<Event>) e -> {
            stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 10;");
            stackPane.getChildren().remove(1);
        });

        return stackPane;
    }

    /**
     * Given an ExpressionTree, create a nested HBox structure visualizing every node within the ExpressionTree as
     * an addition/subtraction equation.
     * Assumes that all interior nodes will be strings representing operators and all leaves will be integers.
     * @param tree The root ExpressionTree to be visualized.
     * @return A FlowPane containing a visualization of {@code tree}.
     */
    @Override
    public Pane drawExpression(Expression tree){

        if (tree.isLeaf()){
            return drawInt(Integer.parseInt(tree.getValue()));
        }

        else {

            // Set up a Pane to hold the visualization
            FlowPane masterPane = new FlowPane();
            masterPane.setAlignment(Pos.CENTER);
            //masterPane.setStyle("-fx-border-color: black"); // for debug
            masterPane.setMaxHeight(nodeSize);

            // Add the visualization of the left ExpressionTree to the masterPane
            if (!Objects.isNull(tree.getLeft())){
                Pane leftPane = tree.getLeft().visualization();
                masterPane.getChildren().add(leftPane);
            }

            // Add the visualization of the root value
            masterPane.getChildren().add(drawString(tree.getValue()));

            // Add the visualization of the right ExpressionTree to the masterPane
            if (!Objects.isNull(tree.getRight())){
                Pane rightPane = tree.getRight().visualization();
                masterPane.getChildren().add(rightPane);
            }

            return masterPane;
        }

    }

}
