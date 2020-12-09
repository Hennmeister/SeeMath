package gui.vis;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import logic.equations.expression_tree.Expression;
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
import logic.equations.expression_tree.ExpType;

public class MultiplicationVisualizer extends Visualizer {
    /**
     * Creates a FlowPane containing {@code num} copies of {@code shape}.
     * @param num The amount of things.
     * @return A FlowPane containing the things.
     */

    public Pane drawInt(int num, boolean isPos){
        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setAlignment(Pos.BASELINE_CENTER);

        for (int i=0; i<abs(num); i++){
            Rectangle shape = new Rectangle(25, 25);
            pane.getChildren().add(shape);

            if (isPos) {
                shape.setFill(positiveColor);
            }
            else {
                shape.setFill(negativeColor);
            }
        }
        pane.setEffect(getDropShadow());
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
        if (tree.isLeaf() || tree.getType() == ExpType.MULTIPLICATION) {
            return generateCompleteVisualization(tree);
        }
        else { //if root is not multiplication, draw left, root right
            HBox layout = new HBox();
            layout.setAlignment(Pos.BASELINE_CENTER);
            Pane leftVisualization = drawExpression(tree.getLeft());
            Pane rightVisualization = drawExpression(tree.getRight());
            Pane root = drawString(tree.getValue());
            layout.setSpacing(50);
            layout.getChildren().addAll(leftVisualization, root, rightVisualization);
            return layout;
        }
    }

    private Pane generateCompleteVisualization(Expression tree) {
        Pane vis = generateMouseOver(tree); //get the visualization
        GridPane border = new GridPane(); //create a GridPane to add the borders of the visualization
        //the string representation of the number that is being repeated
        //Pane left = drawString(tree.findLeftMostLeaf().getValue());
        Pane left;
        Pane top;
        if (tree.isLeaf()) { //if tree is leaf, then its equivalent to multiplying by 1
            left = drawString(tree.getValue());
            top = drawString("1");
        }
        else { //otherwise, get string containing all the multiplication factors
            left = drawString(tree.getLeft().toString());
            top = drawString(tree.getRight().toString());
        }
        //the following lines of code will set up the GridPane to hold the visualization and the borders
        //NOTE: indices .setConstraints are col, row
        GridPane.setConstraints(vis, 1, 1); //put visualization in centre
        GridPane.setConstraints(left, 0, 1); //put left in the cell to the left of vis
        GridPane.setConstraints(top, 1, 0); //put top in the cell right above vis
        GridPane.setFillHeight(left, false); //this is for formatting
        GridPane.setHalignment(top, HPos.CENTER); //this is for formatting
        //border.setGridLinesVisible(true); //for debugging
        border.getChildren().addAll(vis, left, top); //add everything to the GridPane
        return border;
    }

    private Pane draw(Expression tree, boolean isPos) {
        if (tree.isLeaf()){
            return drawInt(Integer.parseInt(tree.getValue()), isPos);
        }
        // Set up a Pane to hold the visualization
        HBox masterPane = new HBox();
        masterPane.setSpacing(12);
        masterPane.setAlignment(Pos.BASELINE_CENTER);
        //draw result:
        //draw left.evaluate(), repeat right.evaluate() # of times
        for (int i = 1; i <= abs(tree.getRight().evaluate()); i++) {
            masterPane.getChildren().add(drawInt(tree.getLeft().evaluate().intValue(), isPos));
        }
        return masterPane;
    }

    private Pane generateMouseOver(Expression tree) {
        // Set up a StackPane to handle mouse-over behaviour on top of the visualization
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setMaxSize(nodeSize, nodeSize);
        if (tree.evaluate() > 0){
            stackPane.getChildren().add(draw(tree, true));
        }
        else{
            stackPane.getChildren().add(draw(tree, false));
        }
        // Code for mouse-over behaviour:
        stackPane.setOnMouseEntered((EventHandler<Event>) event -> {
            Pane strPane = drawString(Integer.toString(tree.evaluate().intValue()));
            strPane.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5); -fx-background-radius: 10;");
            stackPane.getChildren().add(strPane);
        });
        stackPane.setOnMouseExited((EventHandler<Event>) e -> {
            stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 10;");
            stackPane.getChildren().remove(1);
        });

        return stackPane;
    }
}
