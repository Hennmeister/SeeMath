package gui.vis;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import logic.equations.expression_tree.Expression;

import java.util.ArrayList;
import java.util.Collections;

public class FractionVisualizer extends Visualizer{

    @Override
    public Pane drawInt(int num){
        return null;
    };

    public Pane drawFrac(Double num, Double denom){
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
                pane.getChildren().add(arc);
                startAngle += arcLength;
            }
            masterPane.getChildren().add(pane);
            num = num - denom;
        }
        //Draw the remainder of the fraction
        Pane pane = new Pane();
        for (int i = 0; i < denom; i++){
            Arc arc = new Arc();
            arc.setCenterX(51);
            arc.setCenterY(51);
            arc.setRadiusX(50);
            arc.setRadiusY(50);
            arc.setLength(arcLength);
            arc.setStartAngle(0);
            arc.setType(ArcType.ROUND);
            if (i < num){
                arc.setFill(Color.DEEPSKYBLUE);
            } else {
                arc.setFill(Color.WHITE);
            }
            arc.setStroke(Color.BLACK);
            pane.getChildren().add(arc);
        }
        masterPane.getChildren().add(pane);
        return masterPane;
    };

    @Override
    public Pane drawExpression(Expression tree) {
        ArrayList<Double> leaves = tree.getLeaves();
        ArrayList<Double> rightLeaves = new ArrayList<>();
        for (int i = 0; i < leaves.size(); i=i+2){
            rightLeaves.add(leaves.get(i));
        }
        Double denominator = findLCM(rightLeaves);


        return null;
    }

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
