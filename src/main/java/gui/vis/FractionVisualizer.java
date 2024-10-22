package gui.vis;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.FlowPane;
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
import java.util.concurrent.Flow;

public class FractionVisualizer extends Visualizer{

    /**
     * Visualizes one or more circles, each split into {@code denom} different segments, with
     * {@code num} of those segments being coloured in.
     * @param num the numerator of the fraction being visualized.
     * @param denom the denominator of the fraction being visualized.
     * @return The Pane containing the circles which represent the fraction.
     */

    public Pane drawFraction(Double num, Double denom){
        FlowPane masterPane = new FlowPane();
        masterPane.setHgap(10);
        masterPane.setAlignment(Pos.CENTER);
        //masterPane.setStyle("-fx-border-color: black"); // for debug

        // Draw arcs with positive or negative color depending on num
        Color color;
        Color accentColor;
        Effect shadow = getDropShadow();
        if (num > 0){
            color = positiveColor;
            accentColor = positiveAccentColor;
        } else {
            color = negativeColor;
            accentColor = negativeAccentColor;
        }

        double absNum = Math.abs(num);

        // circleCount keeps track of many many circles are in the vis so dimensions of the pane
        // can be set appropriately
        int circleCount = 0;

        double arcLength = 360/denom;
        //Draw full circles for each whole number contained within the fraction
        while (absNum > denom){
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
                arc.setFill(color);
                arc.setStroke(accentColor);
                arc.setStrokeWidth(1);
                pane.getChildren().add(arc);
                startAngle += arcLength;
            }
            pane.setEffect(shadow);
            masterPane.getChildren().add(pane);
            circleCount += 1;
            absNum = absNum - denom;
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
            if (i < absNum){
                arc.setFill(color);
            } else {
                arc.setFill(Color.WHITE);
            }
            arc.setStroke(accentColor);
            arc.setStrokeWidth(1);
            pane.getChildren().add(arc);
            startAngle += arcLength;
        }
        circleCount += 1;
        masterPane.setMaxSize(110*circleCount, 110*circleCount);
        pane.setEffect(shadow);
        masterPane.getChildren().add(pane);
        return masterPane;
    };

    /**
     * Given an ExpressionTree, create a nested HBox structure visualizing every node within the ExpressionTree
     * as fractions.
     * Assumes that all interior nodes will be strings representing operators and all leaves will be doubles.
     * @return A FlowPane containing a visualization of {@code tree}.
     */

    @Override
    public Pane drawExpression(Expression tree) {
        // Find the LCM for all the leaves representing denominators in the expression
        ArrayList<Double> leaves = tree.getLeaves();
        ArrayList<Double> rightLeaves = new ArrayList<>();
        for (int i = 1; i < leaves.size(); i=i+2){
            rightLeaves.add(Math.abs(leaves.get(i)));
        }
        Double denominator = findLCM(rightLeaves);

        return drawExpressionRecursive(tree, denominator);
    }

    /**
     * Used for recursively drawing a fraction Expression recursively
     * based on a pre-defined common denominator.
     */
    private Pane drawExpressionRecursive(Expression tree, double denominator){

        if (tree.getLeft().isLeaf() && tree.getRight().isLeaf()){
            // Draw fraction where the left leaf is numerator, right leaf is denominator
            // First, adjust the numerator to the equivalent numerator over the LCM for the expression

            double adjustedNum = tree.getLeft().evaluate() * denominator / tree.getRight().evaluate();

            Pane fractionPane = drawFraction(adjustedNum, denominator);
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.getChildren().add(fractionPane);

            //Set Mouse-over Behaviour

            stackPane.setOnMouseEntered((EventHandler<Event>) event -> {
                //stackPane.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5); -fx-background-radius: 10;");
                //stackPane.getChildren().add(drawString(tree.getLeft().evaluate().intValue() +
                 //       "/" + tree.getRight().evaluate().intValue()));
                Pane strPane = drawString(tree.getLeft().evaluate().intValue() +
                        "/" + tree.getRight().evaluate().intValue());
                strPane.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5); -fx-background-radius: 10;");
                stackPane.getChildren().add(strPane);
            });
            stackPane.setOnMouseExited((EventHandler<Event>) e -> {
                stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 10;");
                stackPane.getChildren().remove(1);
            });

            return stackPane;
        }

        else {
            // Set up a Pane to hold the visualization
            FlowPane masterPane = new FlowPane();
            masterPane.setPrefWrapLength(900);
            masterPane.setAlignment(Pos.CENTER);
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
