package gui.vis;

import javafx.scene.layout.Pane;
import logic.equations.expression_tree.Expression;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import static java.lang.Math.abs;
import logic.equations.expression_tree.ExpType;

/**
 * This class is responsible for generating intuitive visualizations for an Expression. These visualizations include a
 * base layer which consists of a rectangle with a length and width which correspond to the values of the left and right
 * subtrees of the Expression, respectively. This rectangle is made of several smaller squares to illustrate the
 * repeated addition nature of multiplication. There is a hover-over functionality that is added on top of the base
 * visualization which will print the value of the Expression on top of the rectangle when a user hovers over the shape
 * with their mouse. The visualization also includes labels on the top and to the left of the rectangle which contain
 * the value of the length and width of the rectangle being drawn.
 */

public class MultiplicationVisualizer extends Visualizer {
    /**
     * Creates a VBox containing {@code num} copies of Rectangle Nodes.
     * The colour of the nodes is dependent on {@code isPos}.
     *
     * @param num The number of rectangles to draw.
     * @param isPos Indicates which colour rectangles should be drawn.
     * @return A VBox containing the Rectangles.
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

        if (isPos){
            pane.setEffect(getDropShadow());
        } else {
            pane.setEffect(getInnerShadow());
        }

        return pane;
    };
    /**
     * Given an Expression {@code tree}, creates a nested pane structure which contains the complete visualization
     * representation of {@code tree}. This pane structure includes the hover-over functionality along with the labels
     * representing the parameters of the multiplication on the top and left of the shape.
     * @param tree The Expression to be visualized.
     * @return A Pane containing a visualization of {@code tree}.
     */
    @Override
    public Pane drawExpression(Expression tree) {
        if (tree.isLeaf() || tree.getType() == ExpType.MULTIPLICATION) {
            return generateCompleteVisualization(tree);
        }
        else { //if root is not multiplication, draw left, root, right
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

    /**
     * This private helper method is responsible for retrieving the pane that stores the base visualization with the
     * mouse hover-over functionality and applying the labels to the top and left of the shape to complete the
     * visualization of Expression {@code tree}.
     *
     * @param tree The Expression to be visualized.
     * @return A GridPane that has the base visualization with mouse hover-over in the center and multiplication
     * parameter labels on the top and left of the shape.
     */

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

    /**
     * This private method is responsible for generating the base visualization of the Expression {@code tree}.
     * This method will determine how many rectangles need to be drawn and generate a tree.left.evaluate() by
     * tree.right.evaluate() rectangle to represent the visualization of the Expression {@code tree}.
     *
     * @param tree The Expression to be visualized.
     * @param isPos Indicates which colour rectangles should be drawn.
     * @return A Pane that stores the base visualization rectangle of tree.
     */

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

    /**
     * This private helper method is responsible for retrieving the pane that stores the base visualization and
     * adding the mouse hover-over functionality which will print the value of the expression on top of the visualization
     * when the user hovers over with their mouse.
     * @param tree The Expression to be visualized
     * @return A StackPane that has the base visualization of the Expression {@code tree} and the mouse hover-over
     * functionality.
     */

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
