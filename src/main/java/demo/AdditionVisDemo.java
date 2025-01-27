package demo;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
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
import logic.equations.expression_tree.*;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.Number;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

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

        Expression ex1 = new Number("2", "1");
        Expression ex2 = new Number("19", "1");
        Expression ex3 = new AdditionOp(ex1, ex2, "1");
        Expression ex4 = new Number("8", "1");
        Expression ex5 = new Number("6", "1");
        Expression ex6 = new DivisionOp(ex4, ex5, "1");
        Expression ex8 = new MultiplicationOp(ex4, ex5, "1");
        Expression ex7 = new AdditionOp(ex3, ex8, "1");


        //primaryStage.setScene(new Scene(masterPane, 800, 800));
        //primaryStage.setScene(new Scene(drawExpressionPane, 1200, 800));

        primaryStage.setScene(new Scene(additionVisualizer.drawExpression(ex7), 800, 800));
        primaryStage.show();

//        File file = new File("/Users/affansiddiqui/Desktop/image.png");
//        //BufferedImage image;
//
//
//
//        AdditionVisualizer av = new AdditionVisualizer();
//        Pane pane = av.drawExpression(ex3);
//        WritableImage write = drawExpressionPane.snapshot(new SnapshotParameters(), null);
//        RenderedImage renderedImage = SwingFXUtils.fromFXImage(write, null);
//        ImageIO.write(renderedImage, "png", file);

    }


    public static void main(String[] args) {
        launch(args);

    }
}
