package gui.vis;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import logic.equations.AdditionEquation;
import logic.equations.expression_tree.ExpressionTree;

import java.util.Objects;

public class AdditionVisualizer extends Visualizer {

    Node discreteShape = new Rectangle(25, 25);
    // The Node that will be replicated on screen to visualize integers

    public void setDiscreteShape(Node node){
        this.discreteShape = node;
    }

    public Node getDiscreteShape(){
        return this.discreteShape;
    }

    /**
     * Creates a FlowPane containing {@code num} copies of {@code discreteShape}.
     * @param num The amount of things.
     * @return A FlowPane containing the things.
     */
    @Override
    public Pane drawInt(int num){
        FlowPane pane = new FlowPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.BASELINE_CENTER);
        pane.setPrefWrapLength(nodeSize);

        for (int i=0; i<num; i++){
            pane.getChildren().add(discreteShape);
        }
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
        return null;
        /**
        if (Objects.isNull(tree.root)) {
            return null;
        }

        else {
            // Set up a Pane to hold the visualization
            FlowPane masterPane = new FlowPane();
            masterPane.setHgap(10);
            masterPane.setVgap(10);

            // Add the visualization of the left ExpressionTree to the masterPane
            if (!Objects.isNull(tree.left)){
                FlowPane leftPane = drawExpression(tree.left);
                masterPane.getChildren().add(leftPane);
            }

            // Add the visualization of the root value, will be
            // masterPane.getChildren().add(drawString(tree.root.value));
            // OR
            // masterPane.getChildren().add(drawInt(tree.root.value));

            // Add the visualization of the right ExpressionTree to the masterPane
            if (!Objects.isNull(tree.right)){
                FlowPane rightPane = drawExpression(tree.right);
                masterPane.getChildren().add(rightPane);
            }


        }*/

    }

}
