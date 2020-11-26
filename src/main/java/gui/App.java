package gui;

import javafx.application.Application;
import javafx.css.Stylesheet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.WebController;
import logic.equations.EquationManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        VisualizationPresenter visPresenter = new VisualizationPresenter(stage);
        EquationManager eqnManager = new EquationManager(visPresenter);
        WindowFactory windowFactory = new WindowFactory();


        // Start WebSocket Server
        try {
            WebController server = new WebController();
            server.startServer(eqnManager); } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LandingPage landing = new LandingPage();
        Image iconImage = new Image(new FileInputStream("seemathicon.png"));
        ImageView iconView = new ImageView(iconImage);
        iconView.setPreserveRatio(true);
        iconView.setFitHeight(35);

        // BorderPane is the root pane for the App
        BorderPane borderPane = windowFactory.createWindow(stage, iconView);

        // visPane is the main blank space in the app, where the content (i.e. visualizations) should go
        AnchorPane visPane = (AnchorPane) borderPane.getCenter();


        Scene scene = new Scene(borderPane);

        stage.getIcons().add(iconImage);

        stage.setTitle("SeeMath");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}