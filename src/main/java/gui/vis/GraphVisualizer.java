package gui.vis;
import logic.equations.Equation;
import logic.equations.expression_tree.Variable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;
import java.util.Collections;

public class GraphVisualizer extends Application{
    @Override
    public void start(Stage stage){
        BorderPane bp = new BorderPane();
        Pane topPane = new Pane();
        topPane.setPrefHeight(0);
        topPane.setStyle("-fx-background-color: rgb(68, 72, 83);");
        topPane.getChildren();

        Equation eq = new Equation("1", 1, "=", new Variable("y", "2"), new Variable("x", "3"), true);
        ArrayList<Double> values = new ArrayList<Double>();
        double i = -1000;
        boolean infinite = false;
        while(i < 1000){
            values.add(Math.pow(i,2)*eq.getRightTree().evaluate());
            i += 0.01;
        }
        if(((values.get(values.size()-1) == Collections.max(values)) ||
                (values.get(values.size()-1) == Collections.min(values))) ||
                ((values.get(0) == Collections.max(values)) ||
                        (values.get(0) == Collections.min(values)))
        ){
            infinite = true;
        }
        double max = Math.round(Collections.max(values));
        double min = Math.round(Collections.min(values));
        int spacing = (int)(Math.round(max)/10);
        int xMin = -1000;
        int xMax = 1000;
        if(infinite){
            max = 17;
            min = -17;
            spacing = 1;
            xMin = -17;
            xMax = 17;
        }
        Axes axes = new Axes(800, 600,
                xMin, xMax, (int)(Math.round(xMax)/10),
                min, max, spacing);
        Plot plot = new Plot(eq, -1000, 1000, 0.01, axes);
        StackPane layout = new StackPane(
                plot
        );
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: rgb(35, 39, 50);");
        layout.getChildren().clear();
        layout.getChildren().add(plot);
        bp.setCenter(layout);
        bp.setTop(topPane);
        stage.setScene(new Scene(bp, Color.rgb(35, 39, 50)));
        stage.show();
    }
    public static void main(){

    }

}

class Axes extends Pane{
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

class Plot extends Pane{
    public Plot(
            Equation eq,
            double xMin, double xMax, double xIncrement,
            Axes axes
    ){
        Path path = new Path();
        path.setStroke(Color.GREEN.deriveColor(0, 1, 1, 0.6));
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
        while(x < xMax){
            try{
                double mX = mapX(x, axes);
                double mY = mapY(Math.pow(x,2)*eq.getRightTree().evaluate(), axes);

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