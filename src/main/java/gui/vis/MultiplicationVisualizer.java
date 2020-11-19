package gui.vis;

import javafx.scene.layout.Pane;
import logic.equations.expression_tree.Expression;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
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

            shape.setFill(javafx.scene.paint.Color.GREEN);
        }
        //pane.setStyle("-fx-border-color: black"); // for debug
        // Set up a StackPane to handle mouse-over behaviour on top of the visualization
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setMaxSize(nodeSize, nodeSize);
        stackPane.getChildren().add(pane);

        // Code for mouse-over behaviour:
        stackPane.setOnMouseEntered((EventHandler<Event>) event -> {
            stackPane.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5); -fx-background-radius: 10;");
            stackPane.getChildren().add(drawString(Integer.toString(num)));
        });
        stackPane.setOnMouseExited((EventHandler<Event>) e -> {
            stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 10;");
            stackPane.getChildren().remove(1);
        });

        return stackPane;
    };
    /**
     * Given an ExpressionTree, create a nested HBox structure visualizing every node within the ExpressionTree.
     * Assumes that all interior nodes will be strings representing operators and all leaves will be integers.
     * @param tree The root ExpressionTree to be visualized.
     * @return A FlowPane containing a visualization of {@code tree}.
     */
    @Override
    public Pane drawExpression(Expression tree) {
        if (tree.isLeaf()){
            return drawInt(Integer.parseInt(tree.getValue()));
        }
        else {
            // Set up a Pane to hold the visualization
            HBox masterPane = new HBox();
            masterPane.setSpacing(12);
            masterPane.setAlignment(Pos.BASELINE_CENTER);


            if (tree.getValue().equals("=")) { //tree represents a LS = RS expression
                //draw left side of equation:
                masterPane.getChildren().add(drawExpression(tree.getLeft()));
                //draw root:
                masterPane.getChildren().add(drawString(tree.getValue()));
                //draw right number:
                masterPane.getChildren().add(drawExpression(tree.getRight()));
            }
            else{
                //tree represents x*y expression
                //draw result:
                //repeat the vis for left subtree, right subtree amount of times
                for(int i = 1; i <= tree.getRight().evaluate(); i++) {
                    masterPane.getChildren().add(drawExpression(tree.getLeft()));
                }
            }
            //Testing borders - will work on later to try and come up with fix, seems challenging due to the recursion...
            //BorderPane border = new BorderPane();
            //border.setLeft(drawString(findLeftMostLeaf(tree)));
            //border.setCenter(masterPane);
            //border.setTop(drawString("*" + tree.getRight().evaluate()));

            return masterPane;
        }
    }

    /**
     * Private helper to find the base number that is being repeated in the visualization. Was used to help with borders
     * @param tree - the expression from drawExpression
     * @return the leftmost leaf's value
     */
    private String findLeftMostLeaf(Expression tree) {
        if (tree.isLeaf()) {
            return tree.getValue();
        }
        else {
            return findLeftMostLeaf(tree.getLeft());
        }
    }


}
