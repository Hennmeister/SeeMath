package demo;

import gui.vis.FractionVisualizer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import logic.equations.expression_tree.AdditionOp;
import logic.equations.expression_tree.DivisionOp;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.Number;
import javafx.application.Application;
import javafx.stage.Stage;


public class FractionVisDemo extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("This is a test of the FractionVisualizer.");


        FlowPane masterPane = new FlowPane();

        FractionVisualizer fractionVisualizer = new FractionVisualizer();

        Expression ex1 = new Number("1");
        Expression ex2 = new Number("2");
        Expression ex3 = new DivisionOp(ex1, ex2);
        Expression ex4 = new Number("1");
        Expression ex5 = new Number("4");
        Expression ex6 = new DivisionOp(ex4, ex5);
        Expression ex7 = new AdditionOp(ex3, ex6);

        //masterPane.getChildren().add(fractionVisualizer.drawExpression(ex7));

        primaryStage.setScene(new Scene(masterPane, 800, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
