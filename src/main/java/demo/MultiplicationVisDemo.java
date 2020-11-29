package demo;

import gui.vis.AdditionVisualizer;
import gui.vis.MultiplicationVisualizer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.MultiplicationOp;
import logic.equations.expression_tree.Number;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

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

        Expression ex1 = new Number("-2", "1");
        Expression ex2 = new Number("-3", "1");
        Expression ex3 = new MultiplicationOp(ex1, ex2, "1");
        Expression ex4 = new Number("-3", "1");
        Expression ex5 = new MultiplicationOp(ex3, ex4, "1");
        Expression ex6 = new MultiplicationOp(ex3, ex3, "1");

        drawExpressionPane.getChildren().add(mV.drawExpression(ex3));

        BorderPane border = new BorderPane();
        border.setCenter(mV.drawExpression(ex3));
        //border.setCenter(drawExpressionPane);
        primaryStage.setScene(new Scene(border, 800, 800));
        primaryStage.show();


        /*
        Code for testing the photoHintCreator
         */
        File file = new File("/Users/affansiddiqui/Desktop/image.png");
        Pane pane = mV.drawExpression(ex3);
        pane.setStyle("-fx-padding: 25");
        WritableImage write = pane.snapshot(new SnapshotParameters(), null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(write, null), "png", s);
        byte [] arr = s.toByteArray();
        String str = Base64.getEncoder().encodeToString(arr);
        System.out.println(str);

    }
    public static void main(String[] args) {
        launch(args);

    }
}
