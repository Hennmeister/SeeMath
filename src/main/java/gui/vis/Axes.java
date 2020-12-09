package gui.vis;

import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

class Axes extends Pane {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    public Axes(int width, int height, double xLow, double xHigh,
                double xUnit, double yLow, double yHigh, double yUnit){
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(width, height);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        xAxis = new NumberAxis(xLow, xHigh, xUnit);
        xAxis.setSide(Side.BOTTOM);
        xAxis.setMinorTickVisible(false);
        xAxis.setPrefWidth(width);
        xAxis.setLayoutY(height/2);

        yAxis = new NumberAxis(yLow, yHigh, yUnit);
        yAxis.setSide(Side.LEFT);
        yAxis.setMinorTickVisible(false);
        yAxis.setPrefHeight(height);
        yAxis.layoutXProperty().bind(Bindings.subtract((width/2)+1,
                yAxis.widthProperty()));
        getChildren().setAll(xAxis, yAxis);
    }
    public NumberAxis getXAxis(){return xAxis;}
    public NumberAxis getYAxis(){return yAxis;}
}