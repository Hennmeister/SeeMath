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

public class AdditionVisDemo extends Application {

    Button button;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");

        // For testing the drawInt and drawString methods
        FlowPane masterPane = new FlowPane();
        AdditionVisualizer additionVisualizer = new AdditionVisualizer();
        masterPane.getChildren().add(additionVisualizer.drawInt(4));
        masterPane.getChildren().add(additionVisualizer.drawString("+"));
        masterPane.getChildren().add(additionVisualizer.drawInt(4));

        primaryStage.setScene(new Scene(masterPane, 800, 800));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);

    }
}
