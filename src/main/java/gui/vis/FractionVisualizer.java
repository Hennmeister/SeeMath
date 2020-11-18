package gui.vis;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import logic.equations.expression_tree.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class FractionVisualizer extends Visualizer{

    @Override
    public Pane drawInt(int num){
        return null;
    };

    /**
     * Visualizes one or more circles, each split into {@code denom} different segments, with
     * {@code num} of those segments being coloured in.
     * @param num the numerator of the fraction being visualized.
     * @param denom the denominator of the fraction being visualized.
     * @return The Pane containing the circles which represent the fraction.
     */

    public Pane drawFraction(Double num, Double denom){
        HBox masterPane = new HBox();
        masterPane.setSpacing(10);

        double arcLength = 360/denom;
        //Draw full circles for each whole number contained within the fraction
        while (num > denom){
            Pane pane = new Pane();
            double startAngle = 0.0;
            for (int i = 0; i < denom; i++){
                Arc arc = new Arc();
                arc.setCenterX(51);
                arc.setCenterY(51);
                arc.setRadiusX(50);
                arc.setRadiusY(50);
                arc.setLength(arcLength);
                arc.setStartAngle(startAngle);
                arc.setType(ArcType.ROUND);
                arc.setFill(Color.DEEPSKYBLUE);
                arc.setStroke(Color.BLACK);
                arc.setStrokeWidth(1);
                pane.getChildren().add(arc);
                startAngle += arcLength;
            }
            masterPane.getChildren().add(pane);
            num = num - denom;
        }
        //Draw the remainder of the fraction
        Pane pane = new Pane();
        double startAngle = 0.0;
        for (int i = 0; i < denom; i++){
            Arc arc = new Arc();
            arc.setCenterX(51);
            arc.setCenterY(51);
            arc.setRadiusX(50);
            arc.setRadiusY(50);
            arc.setLength(arcLength);
            arc.setStartAngle(startAngle);
            arc.setType(ArcType.ROUND);
            if (i < num){
                arc.setFill(Color.DEEPSKYBLUE);
            } else {
                arc.setFill(Color.WHITE);
            }
            arc.setStroke(Color.BLACK);
            arc.setStrokeWidth(1);
            pane.getChildren().add(arc);
            startAngle += arcLength;
        }
        masterPane.getChildren().add(pane);
        return masterPane;
    };

    /**
     * Given an ExpressionTree, create a nested HBox structure visualizing every node within the ExpressionTree
     * as fractions.
     * Assumes that all interior nodes will be strings representing operators and all leaves will be doubles.
     * @param tree The root ExpressionTree to be visualized.
     * @return A FlowPane containing a visualization of {@code tree}.
     */
    @Override
    public Pane drawExpression(Expression tree) {
        // Find the LCM for all the leaves representing denominators in the expression
        ArrayList<Double> leaves = tree.getLeaves();
        ArrayList<Double> rightLeaves = new ArrayList<>();
        for (int i = 1; i < leaves.size(); i=i+2){
            rightLeaves.add(leaves.get(i));
        }

        Double denominator = findLCM(rightLeaves);

        return drawExpressionRecursive(tree, denominator);
    }

    private Pane drawExpressionRecursive(Expression tree, double denominator){

        if (tree.getLeft().isLeaf() && tree.getRight().isLeaf()){
            // Draw fraction where the left leaf is numerator, right leaf is denominator
            // First, adjust the numerator to the equivalent numerator over the LCM for the expression

            double adjustedNum = tree.getLeft().evaluate() * denominator / tree.getRight().evaluate();

            Pane fractionPane = drawFraction(adjustedNum, denominator);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(fractionPane);

            stackPane.setOnMouseEntered((EventHandler<Event>) event -> {
                stackPane.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5); -fx-background-radius: 10;");
                stackPane.getChildren().add(drawString(tree.getLeft().evaluate().intValue() +
                        "/" + tree.getRight().evaluate().intValue()));
            });
            stackPane.setOnMouseExited((EventHandler<Event>) e -> {
                stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 10;");
                stackPane.getChildren().remove(1);
            });

            return stackPane;
            //return drawFraction(tree.getLeft().evaluate(), tree.getRight().evaluate());
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
                Pane leftPane = drawExpressionRecursive(tree.getLeft(), denominator);
                masterPane.getChildren().add(leftPane);
            }

            // Add the visualization of the root value
            masterPane.getChildren().add(drawString(tree.getValue()));

            // Add the visualization of the right ExpressionTree to the masterPane
            if (!Objects.isNull(tree.getRight())){
                Pane rightPane = drawExpressionRecursive(tree.getRight(), denominator);
                masterPane.getChildren().add(rightPane);
            }

            return masterPane;
        }
    }

    /**
     * Given an ArrayList of Doubles, returns the lowest common multiple of every element. Assumes all elements
     * are whole numbers.
     */
    private Double findLCM(ArrayList<Double> list){
        Double ans = 0.0;
        Double testVal = Collections.max(list); //Start testing at the maximum number in the list
        boolean searching = true;
        while (searching){
            boolean found = true;
            for (Double i : list){
                if (testVal % i != 0.0) {
                    found = false;
                    break;
                }
            }
            if (found) {
                searching = false;
            } else {
                testVal += 1.0;
            }
        }
        ans = testVal;
        return ans;
    }
}
