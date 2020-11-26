package demo;

import gui.vis.MultiplicationVisualizer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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
        //masterPane.getChildren().add(mV.drawInt(2));

        // For testing the drawExpression method
        //FlowPane drawExpressionPane = new FlowPane(Orientation.VERTICAL);
        //FlowPane drawExpressionPane = new FlowPane();
        StackPane drawExpressionPane = new StackPane();
        //drawExpressionPane.setHgap(10);
        //drawExpressionPane.setVgap(50);
        drawExpressionPane.setAlignment(Pos.TOP_LEFT);
        //drawExpressionPane.setPrefWrapLength(100);
        /**
        Expression ex1 = new Number("-2");
        Expression ex2 = new Number("-3");
        Expression ex3 = new MultiplicationOp(ex1, ex2);
        Expression ex4 = new Number("-3");
        Expression ex5 = new MultiplicationOp(ex3, ex4);
        Expression ex6 = new MultiplicationOp(ex3, ex3);

        drawExpressionPane.getChildren().add(mV.drawExpression(ex3));
        */
        BorderPane border = new BorderPane();
        //border.setCenter(mV.drawExpression(ex6));
        //border.setCenter(drawExpressionPane);
        primaryStage.setScene(new Scene(border, 800, 800));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);

    }
}
