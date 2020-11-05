package gui.vis;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import logic.equations.expression_tree.ExpressionTree;
import java.util.ArrayList;


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

        for (int i=0; i<num; i++){
            Node discreteShapeCopy = new Circle(8); //using a circle to differentiate from addition
            pane.getChildren().add(discreteShapeCopy);
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
    public Pane drawExpression(ExpressionTree tree) {
        if (tree.isLeaf()){
            return drawInt((Integer) tree.getRoot().evaluate());
        }
        else {
            // Set up a Pane to hold the visualization
            //FlowPane masterPane = new FlowPane();
            HBox masterPane = new HBox();
            masterPane.setSpacing(10);
            //masterPane.setHgap(20);
            //masterPane.setVgap(10);
            masterPane.setAlignment(Pos.BASELINE_CENTER);
            //masterPane.setPrefWrapLength(nodeSize);

            if (tree.getRoot().evaluate().equals("=")) { //tree represents a LS = RS expression
                //draw left side of equation:
                masterPane.getChildren().add(drawExpression(tree.getLeft()));
                //draw root:
                masterPane.getChildren().add(drawString((String) tree.getRoot().evaluate()));
                //draw right number:
                masterPane.getChildren().add(drawExpression(tree.getRight()));
            }
            else{ //tree represents x*y expression
                //draw result:
                int placeHolder = (Integer) tree.getRight().getRoot().evaluate();
                //this is a dummy variable in place for new functionality from ExpressionTree
                //ExpressionTree will have a method that will return the value that the tree represents
                for(int i = 1; i <= placeHolder; i++) {//repeat the vis for left subtree, right subtree amount of times
                    masterPane.getChildren().add(drawExpression(tree.getLeft()));
                }
            }
            return masterPane;
        }
    }

    /**
     * Given an ExpressionTree, return an ArrayList containing all the leaves of the ExpressionTree (i.e all the
     * integers in the tree).
     * This is a helper method to drawExpression.
     * An alternative solution to finding the number of times to repeat the visualization of the left subtree.
     * If needed, implementation is ready.
     * If new ExpressionTree functionality is not possible, use this solution for multiplication visualizations.
     * @param tree The right subtree of the main ExpressionTree from drawExpression.
     * @return An ArrayList containing all the leaves of tree.
     */
    private ArrayList<Integer> findLeaves(ExpressionTree tree) {
        if (tree.isLeaf()) {
            ArrayList<Integer> factors = new ArrayList<>();
            factors.add((Integer) tree.getRoot().evaluate());
            return factors;
        }
        else {
            ArrayList<Integer> factors = new ArrayList<>();
            factors.addAll(findLeaves(tree.getRight()));
            factors.addAll(findLeaves(tree.getLeft()));
            return factors;
        }
    }
}
