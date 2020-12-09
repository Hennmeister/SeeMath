package gui.vis;

import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import logic.equations.expression_tree.Expression;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphVisualizer extends Visualizer{

    @Override
    public Pane drawExpression(Expression tree) {
        BorderPane bp = new BorderPane();
        Pane topPane = new Pane();
        topPane.setPrefHeight(0);
        topPane.setStyle("-fx-background-color: rgb(255,255,255);");
        //68,72,83
        topPane.getChildren();
        String equationString = tree.toString();
//        String equationString = "x^2*3-3";
        equationString = equationString.replaceAll("\\s+", ""); //remove whitespace
        equationString = equationString.replace("(",""); //remove opening parentheses
        equationString = equationString.replace(")",""); //remove closing parentheses
        // equationString = equationString.replaceAll("(?i)([-])", "+$1"); //Add + before every -
//        if((equationString.charAt(1) == '-')){
//            equationString = equationString.substring(1); //Remove + before first term
//        }
        String[] terms = equationString.split("\\+");
        ArrayList<Double> values = new ArrayList<>();
        double yIntercept;
        double x = -10;
        double y;
        ArrayList<Double> xIntercepts = new ArrayList<>();
        ArrayList<Double> rounded = new ArrayList<>();
        while(x < 10){
            y = evaluate(terms, x);
            if(((y < 0.01 && y > -0.01)||(y < 0.05 && y > -0.05)) && !rounded.contains((double) Math.round(x))){
                xIntercepts.add(x);
                rounded.add((double) Math.round(x));
            }
            values.add(y);
            x += 0.01;
        }
        yIntercept = evaluate(terms, 0);

        // Create Axes

        Axes axes = new Axes(800, 600,
                -10, 10, 1,
                -12, 12, 1);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(3);
        Circle yInterceptPoint = new Circle();
        yInterceptPoint.setCenterX(mapX(0, axes));
        yInterceptPoint.setCenterY(mapY(yIntercept, axes));
        yInterceptPoint.setFill(Color.BLACK);
        yInterceptPoint.setRadius(3);
        yInterceptPoint.setStrokeWidth(2);
        yInterceptPoint.setStroke(Color.BLACK);
        Tooltip.install(yInterceptPoint, new Tooltip("(0.0, "+decimalFormat.format(yIntercept)+")"));
        for(double xIntercept : xIntercepts){
            Circle xInterceptPoint = new Circle();
            xInterceptPoint.setCenterX(mapX(xIntercept, axes));
            xInterceptPoint.setCenterY(mapY(0, axes));
            xInterceptPoint.setFill(Color.BLACK);
            xInterceptPoint.setRadius(3);
            xInterceptPoint.setStrokeWidth(2);
            xInterceptPoint.setStroke(Color.BLACK);
            Tooltip.install(xInterceptPoint, new Tooltip("("+decimalFormat.format(xIntercept)+", 0.0)"));
            topPane.getChildren().add(xInterceptPoint);
        }

        // Create Plot
        Plot plot = new Plot(values, -10, 10, 0.01, axes);
        plot.getChildren().add(topPane);
        // Place Plot in new Stack Pane
        StackPane layout = new StackPane();
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: rgb(255,255,255);");
        topPane.getChildren().add(yInterceptPoint);
        layout.getChildren().addAll(plot);
        bp.setCenter(layout);
        return bp;
    }
    public double getCoefficient(String term){

        double coefficient = 1;
        if(term.contains("*")) {
            String[] terms = term.split("\\*");
            for (String str : terms) {
                if (!str.contains("x")) {
                    coefficient = Double.parseDouble(str);
                }
            }
        }
        return coefficient;
    }

    public double evaluate(String[] terms, double x){
        double y = 0;
        for(String term : terms){
            //Exponential term
            if(term.contains("^")){
                double coefficient = getCoefficient(term);
                String[] splits = term.split("\\*");
                double exponent = 1;
                for(String split :splits){
                    if(split.contains("^")) {
                        exponent = Double.parseDouble(split.substring(split.indexOf("^") + 1));
                    }
                }
                y += coefficient*Math.pow(x, exponent);
            }
            //Linear term
            else if(term.contains("x")){
                double coefficient = getCoefficient(term);
                y += coefficient*x;
            }
            //Constant term
            else{
                double constant = Double.parseDouble(term);
                y += constant;
            }
        }
        return y;
    }
    private double mapX(double x, Axes axes) {
        double tX = axes.getPrefWidth() / 2;
        double sX = axes.getPrefWidth()
                / (axes.getXAxis().getUpperBound()
                - axes.getXAxis().getLowerBound());

        return x * sX + tX;
    }
    private double mapY(double y, Axes axes) {
        double tY = axes.getPrefHeight() / 2;
        double sY = axes.getPrefHeight()
                / (axes.getYAxis().getUpperBound()
                - axes.getYAxis().getLowerBound());
        return -y * sY + tY;
    }

}