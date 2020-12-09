package gui.vis;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;

class Plot extends Pane {
    public Plot(
            ArrayList<Double> values,
            double xMin, double xMax, double xIncrement,
            Axes axes
    ){
        Path path = new Path();
        path.setStroke(Color.CYAN);
        path.setStrokeWidth(2);
        path.setClip(
                new Rectangle(
                        0, 0,
                        axes.getPrefWidth(),
                        axes.getPrefHeight()
                )
        );
        double x = xMin;
        boolean firstPointPlotted = false;
        int i = 0;
        while(x < xMax){
            try{
                double mX = mapX(x, axes);
                double mY = mapY(values.get(i), axes);

                if(firstPointPlotted){
                    path.getElements().add(
                            new LineTo(
                                    mX,mY
                            )
                    );
                }
                else{
                    path.getElements().add(
                            new MoveTo(
                                    mX, mY
                            )
                    );
                    firstPointPlotted = true;
                }
            } catch(Exception ignored){

            } finally {
                x += xIncrement;
                i += 1;
            }
        }
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        getChildren().setAll(axes, path);
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

