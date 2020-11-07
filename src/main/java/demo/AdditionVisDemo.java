package demo;

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
        FlowPane drawExpressionPane = new FlowPane(Orientation.VERTICAL);
        drawExpressionPane.setHgap(10);
        drawExpressionPane.setVgap(50);
        drawExpressionPane.setAlignment(Pos.TOP_LEFT);
        //drawExpressionPane.setPrefWrapLength(100);

        Expression ex1 = new Expression(2);
        Expression ex2 = new Expression("+");
        Expression ex3 = new Expression(-9);
        Expression ex4 = new Expression("-");
        Expression ex5 = new Expression(2);
        Expression ex6 = new Expression("=");
        Expression ex7 = new Expression(80);


        ExpressionTree expressionTree1 = new ExpressionTree(ex1);
        ExpressionTree expressionTree2 = new ExpressionTree(ex3);
        ExpressionTree expressionTree3 = new ExpressionTree(expressionTree1, ex2, expressionTree2);
        ExpressionTree expressionTree4 = new ExpressionTree(ex5);
        ExpressionTree expressionTree5 = new ExpressionTree(expressionTree3, ex4, expressionTree4);
        ExpressionTree expressionTree6 = new ExpressionTree(ex7);
        ExpressionTree expressionTree7 = new ExpressionTree(expressionTree5, ex6, expressionTree6);

        drawExpressionPane.getChildren().add(additionVisualizer.drawExpression(expressionTree7));

        //primaryStage.setScene(new Scene(masterPane, 800, 800));
        //primaryStage.setScene(new Scene(drawExpressionPane, 1200, 800));
        primaryStage.setScene(new Scene(additionVisualizer.drawExpression(expressionTree7), 800, 800));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);

    }
}
