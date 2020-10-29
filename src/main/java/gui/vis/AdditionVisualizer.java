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

        if (Objects.isNull(tree.getRoot())) {
            return null;
        }

        else if (Objects.isNull(tree.getLeft()) && Objects.isNull(tree.getRight())){
            return drawInt((Integer) tree.getRoot().evaluate());
        }

        else {
            // Set up a Pane to hold the visualization
            FlowPane masterPane = new FlowPane();
            masterPane.setHgap(10);
            masterPane.setVgap(10);

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
