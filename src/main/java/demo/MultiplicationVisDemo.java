package demo;

import gui.vis.MultiplicationVisualizer;
import javafx.application.Application;
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
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.ExpressionTree;

public class MultiplicationVisDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("This is a test of the MultiplicationVisualizer.");

        // For testing the drawInt and drawString methods
        FlowPane masterPane = new FlowPane();
        MultiplicationVisualizer mV = new MultiplicationVisualizer();
        masterPane.getChildren().add(mV.drawInt(2));

        // For testing the drawExpression method
        FlowPane drawExpressionPane = new FlowPane();
        drawExpressionPane.setHgap(10);
        drawExpressionPane.setVgap(40);
        drawExpressionPane.setAlignment(Pos.TOP_LEFT);
        drawExpressionPane.setPrefWrapLength(100);

        /*
        Creating Expression objects for testing
         */
        Expression ex1 = new Expression(2);
        Expression ex2 = new Expression("*");
        Expression ex3 = new Expression(9);
        Expression ex4 = new Expression(2);
        Expression ex5 = new Expression("=");

        /*
        Creating Tree objects from Expression objects above for testing
         */
        ExpressionTree tree = new ExpressionTree(new ExpressionTree(ex1), ex2, new ExpressionTree(ex3));
        ExpressionTree tree1 = new ExpressionTree(tree, ex2, new ExpressionTree(ex4));
        ExpressionTree tree2 = new ExpressionTree(tree, ex5, new ExpressionTree(ex4));

        drawExpressionPane.getChildren().add(mV.drawExpression(tree));
        //drawExpressionPane.getChildren().add(mV.drawExpression(tree1));
        //drawExpressionPane.getChildren().add(mV.drawExpression(tree2));

        /*
        Testing to see how borders work and how to potentially provide more useful information to a multiplication
        visualization.
         */
        BorderPane border = new BorderPane();
        border.setTop(mV.drawString("* 9"));
        border.setCenter(drawExpressionPane);
        border.setLeft(mV.drawString("2"));
        //primaryStage.setScene(new Scene(masterPane, 800, 800));
        primaryStage.setScene(new Scene(border, 600, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);

    }
}
