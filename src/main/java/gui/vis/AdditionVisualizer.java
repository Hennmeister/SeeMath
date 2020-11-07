package gui.vis;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import logic.equations.expression_tree.ExpressionTree;

import java.awt.*;
import java.util.Objects;
import static java.lang.Math.abs;

public class AdditionVisualizer extends Visualizer {
    /**
     * Creates a FlowPane containing {@code num} copies of {@code discreteShape}.
     * @param num The amount of things.
     * @return A FlowPane containing the things.
     */
    @Override
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
            if (num <= 9){
                node = new Rectangle(25, 25);
            } else {
                node = new Rectangle(10, 10);
            }

            // If <num> is 0 or positive color the node green, otherwise color it red
            if (num >= 0){
                node.setFill(javafx.scene.paint.Color.GREEN);
            } else {
                node.setFill(javafx.scene.paint.Color.RED);
            }
            pane.getChildren().add(node);
        }
        //pane.setStyle("-fx-border-color: black"); // for debug
        return pane;
    }

    /**
     * Given an ExpressionTree, create a nested FlowPane structure visualizing every node within the ExpressionTree.
     * Assumes that all interior nodes will be strings representing operators and all leaves will be integers.
     * @param tree The root ExpressionTree to be visualized.
     * @return A FlowPane containing a visualization of {@code tree}.
     */
    @Override
    public Pane drawExpression(ExpressionTree tree){

        if (Objects.isNull(tree.getLeft()) && Objects.isNull(tree.getRight())){
            return drawInt((Integer) tree.getRoot().evaluate());
        }

        else {
            // Set up a Pane to hold the visualization
            HBox masterPane = new HBox();
            masterPane.setSpacing(0);
            masterPane.setAlignment(Pos.TOP_LEFT);
            //masterPane.setStyle("-fx-border-color: black"); // for debug
            masterPane.setMaxHeight(nodeSize);

            // Add the visualization of the left ExpressionTree to the masterPane
            if (!Objects.isNull(tree.getLeft())){
                Pane leftPane = drawExpression(tree.getLeft());
                masterPane.getChildren().add(leftPane);
            }

            // Add the visualization of the root value
            masterPane.getChildren().add(drawString((String) tree.getRoot().evaluate()));

            // Add the visualization of the right ExpressionTree to the masterPane
            if (!Objects.isNull(tree.getRight())){
                Pane rightPane = drawExpression(tree.getRight());
                masterPane.getChildren().add(rightPane);
            }

            return masterPane;
        }

    }

}
