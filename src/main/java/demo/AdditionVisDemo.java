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
import logic.equations.expression_tree.AdditionOp;
import logic.equations.expression_tree.BinaryOp;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.Number;

public class AdditionVisDemo extends Application {

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

        Expression ex1 = new Number("2");
        Expression ex2 = new Number("19");
        Expression ex3 = new AdditionOp(ex1, ex2);


        drawExpressionPane.getChildren().add(additionVisualizer.drawExpression(ex3));

        //primaryStage.setScene(new Scene(masterPane, 800, 800));
        //primaryStage.setScene(new Scene(drawExpressionPane, 1200, 800));
        primaryStage.setScene(new Scene(additionVisualizer.drawExpression(ex3), 800, 800));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);

    }
}
