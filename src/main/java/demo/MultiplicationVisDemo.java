package demo;

import gui.vis.MultiplicationVisualizer;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gui.vis.Visualizer;
import gui.vis.AdditionVisualizer;
import logic.equations.expression_tree.AdditionOp;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.MultiplicationOp;
import logic.equations.expression_tree.Number;

public class MultiplicationVisDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("This is a test of the MultiplicationVisualizer.");

        // For testing the drawInt and drawString methods
        FlowPane masterPane = new FlowPane();
        MultiplicationVisualizer mV = new MultiplicationVisualizer();
        masterPane.getChildren().add(mV.drawInt(2));

        // For testing the drawExpression method
        FlowPane drawExpressionPane = new FlowPane(Orientation.VERTICAL);
        drawExpressionPane.setHgap(10);
        drawExpressionPane.setVgap(50);
        drawExpressionPane.setAlignment(Pos.TOP_LEFT);
        //drawExpressionPane.setPrefWrapLength(100);

        Expression ex1 = new Number("2");
        Expression ex2 = new Number("4");
        Expression ex3 = new MultiplicationOp(ex1, ex2);
        Expression ex4 = new MultiplicationOp(ex1, ex3);
        Expression ex6 = new MultiplicationOp(ex3, ex3);
        Expression ex5 = new MultiplicationOp(ex1, ex6);

        drawExpressionPane.getChildren().add(mV.drawExpression(ex5));

        BorderPane border = new BorderPane();
        border.setCenter(drawExpressionPane);
        primaryStage.setScene(new Scene(border, 600, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);

    }
}
