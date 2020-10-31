package demo;

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

public class AdditionVisDemo extends Application {

    Button button;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("This is a test of the AdditionVisualizer.");

        // For testing the drawInt and drawString methods
        FlowPane masterPane = new FlowPane();
        AdditionVisualizer additionVisualizer = new AdditionVisualizer();
        masterPane.getChildren().add(additionVisualizer.drawInt(4));
        masterPane.getChildren().add(additionVisualizer.drawString("+"));
        masterPane.getChildren().add(additionVisualizer.drawInt(4));

        // For testing the drawExpression method

        FlowPane drawExpressionPane = new FlowPane();

        Expression ex1 = new Expression(4);
        Expression ex2 = new Expression("+");
        Expression ex3 = new Expression(3);

        ExpressionTree expressionTree1 = new ExpressionTree(ex1);
        ExpressionTree expressionTree2 = new ExpressionTree(ex3);
        ExpressionTree expressionTree3 = new ExpressionTree(expressionTree1, ex2, expressionTree2);

        drawExpressionPane.getChildren().add(additionVisualizer.drawExpression(expressionTree3));

        //primaryStage.setScene(new Scene(masterPane, 800, 800));
        primaryStage.setScene(new Scene(drawExpressionPane, 800, 800));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);

    }
}
