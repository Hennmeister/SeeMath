package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.WebController;
import logic.equations.EquationManager;

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


        // Start WebSocket Server
        try {
            WebController server = new WebController();
            server.startServer(eqnManager); } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Change application colour scheme here
        String colour1 = "#24496d";
        String colour2 = "#caebfa";

        // borderPane is the root for the visualization scene
        BorderPane borderPane = new BorderPane();

        ToolBar toolBar = new ToolBar();
        toolBar.setStyle("-fx-background-color: " + colour1 + "; " +
                "-fx-padding: 0, 0, 0, 0; " +
                "-fx-spacing: 0; ");

        // Right-aligned Buttons for window behaviour
        Button closeButton = new Button();
        Image closeImg = new Image(new FileInputStream("x.png"));
        ImageView closeView = new ImageView(closeImg);
        closeView.setFitHeight(30);
        closeView.setPreserveRatio(true);
        closeButton.setGraphic(closeView);
        closeButton.setOnAction((EventHandler<ActionEvent>) e -> {
            stage.close();
        });

        Button minimizeButton = new Button();
        minimizeButton.setCancelButton(true);
        Image minImage = new Image(new FileInputStream("min.png"));
        ImageView minView = new ImageView(minImage);
        minView.setFitHeight(30);
        minView.setPreserveRatio(true);
        minimizeButton.setGraphic(minView);
        minimizeButton.setOnAction((EventHandler<ActionEvent>) e -> {
            stage.setIconified(true);
        });

        Button maximizeButton = new Button();
        Image maxImage = new Image(new FileInputStream("max.png"));
        ImageView maxView = new ImageView(maxImage);
        maxView.setFitHeight(30);
        maxView.setPreserveRatio(true);
        maximizeButton.setGraphic(maxView);
        maximizeButton.setOnAction((EventHandler<ActionEvent>) e -> {
            stage.setMaximized(!stage.isMaximized());
        });

        ArrayList<Button> buttonList = new ArrayList<Button>();
        buttonList.add(closeButton);
        buttonList.add(maximizeButton);
        buttonList.add(minimizeButton);


        for (Button button : buttonList) {
            button.setStyle("-fx-background-color: " + colour1 + "; " + "-fx-background-radius: 0");

            button.setOnMouseEntered((EventHandler<Event>) e ->{
                button.setStyle("-fx-background-color: " + colour2 + "; " + "-fx-background-radius: 0");
            });
            button.setOnMouseExited((EventHandler<Event>) e -> {
                button.setStyle("-fx-background-color: " + colour1 + "; " + "-fx-background-radius: 0");
            });
        }

        // Left-aligned drop-down menus
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-padding: 0, 1, 0, 1; " +
                "-fx-spacing: 0; " +
                "-fx-background-color: " + colour1 + "; " +
                "-fx-font-family: Goudy Old Style; " +
                //"-fx-font-style: italic; " +
                "-fx-font-size: 15pt; " +
                "-fx-font-color: " + colour2);

        Menu fileMenu = new Menu("file");
        Menu editMenu = new Menu("edit");
        Menu helpMenu = new Menu("help");

        MenuItem saveImage = new MenuItem("save image");
        saveImage.setOnAction((EventHandler<ActionEvent>) event -> {
            System.out.println("Save Image action.");
        });

        MenuItem aboutSeeMath = new MenuItem("about SeeMath");
        aboutSeeMath.setOnAction((EventHandler<ActionEvent>) e -> {
            System.out.println("About SeeMath action.");
        });

        MenuItem changeColours = new MenuItem("change colours");
        changeColours.setOnAction((EventHandler<ActionEvent>) e -> {
            System.out.println("Change Colour action.");
        });

        fileMenu.getItems().addAll(saveImage);
        editMenu.getItems().addAll(changeColours);
        helpMenu.getItems().addAll(aboutSeeMath);

        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        HBox buffer = new HBox();
        HBox.setHgrow(buffer, Priority.ALWAYS);

        toolBar.getItems().addAll(menuBar, buffer, minimizeButton, maximizeButton, closeButton);

        // The AnchorPane holds the visualization within the blank space in the center of the application
        AnchorPane visPane = new AnchorPane();
        visPane.setPrefSize(900, 500);

        // UpdateVisualization within the VisualizationPresenter should interact here, but I'm not 100%
        // sure how it works, so it's getting an empty pane rn.
        Pane visualization = new Pane();
        visualization.setTranslateX(20);
        visualization.setTranslateY(20);

        visPane.getChildren().addAll(visualization);

        borderPane.setTop(toolBar);
        borderPane.setCenter(visPane);

        Scene scene = new Scene(borderPane);

        stage.getIcons().add(new Image(new FileInputStream("seemathicon.png")));

        stage.setTitle("SeeMath");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}