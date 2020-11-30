package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.WebController;
import logic.equations.EquationManager;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        VisualizationPresenter visPresenter = new VisualizationPresenter(stage);
        EquationManager eqnManager = new EquationManager(visPresenter);
        WindowPresenter windowPresenter = new WindowPresenter();


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
        BorderPane borderPane = windowPresenter.createWindow(stage);

        // visPane is the main blank space in the app, where the content (i.e. visualizations) should go
        ScrollPane sp = (ScrollPane) borderPane.getCenter();
        VBox visPane = (VBox) sp.getContent();
        visPane.getChildren().addAll(landing.getLandingPage(stage));

        Scene scene = new Scene(borderPane);

        stage.getIcons().add(iconImage);

        stage.setTitle("SeeMath");
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}