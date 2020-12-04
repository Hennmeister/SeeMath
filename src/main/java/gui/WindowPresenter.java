package gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WindowPresenter {

    public BorderPane createWindow(Stage stage) {

        // Change application colour scheme here
        String colour2 = "#24496d";
        String colour1 = "#4FC3F7";

        // borderPane is the root for the visualization scene
        BorderPane borderPane = new BorderPane();

        ToolBar toolBar = new ToolBar();
        toolBar.setStyle("-fx-background-color: " + colour1 + "; " +
                "-fx-padding: 0; " +
                "-fx-spacing: 0; " +
                "-fx-text-fill: white");

        /**
        // Formatting the right-aligned Buttons for window behaviour:
        Button closeButton = createCloseButton(stage);
        Button maximizeButton = createMaximizeButton(stage);
        Button minimizeButton = createMinimizeButton(stage);

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
         **/

        // Left-aligned drop-down menus:
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-padding: 0, 0, 0, 0; " +
                "-fx-spacing: 0; " +
                "-fx-background-color: " + colour1 + "; " +
                "-fx-font-family: Goudy Old Style; " +
                //"-fx-font-style: italic; " +
                "-fx-font-size: 15pt; " +
                "-fx-color: white"
        );

        Menu fileMenu = createFileMenu();
        Menu editMenu = createEditMenu();
        Menu helpMenu = createHelpMenu(stage);

        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        // Buffer HBox makes everything after it right-oriented
        HBox buffer = new HBox();
        HBox.setHgrow(buffer, Priority.ALWAYS);

        toolBar.getItems().addAll(menuBar, buffer);

        // The AnchorPane holds the visualization within the blank space in the center of the application
        VBox visPane = new VBox();
        visPane.setPrefSize(900, 500);
        //visPane.setMinHeight(500);
        //visPane.setMinWidth(900);
        //visPane.setMinSize(900, 500);
        visPane.setAlignment(Pos.CENTER);
        visPane.setSpacing(20);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(visPane);

        borderPane.setTop(toolBar);
        borderPane.setCenter(scrollPane);

        return borderPane;
    }

    private Button createCloseButton(Stage stage) throws FileNotFoundException {
        Button closeButton = new Button();
        Image closeImg = new Image(new FileInputStream("x.png"));
        ImageView closeView = new ImageView(closeImg);
        closeView.setFitHeight(35);
        closeView.setPreserveRatio(true);
        closeButton.setGraphic(closeView);
        closeButton.setOnAction((EventHandler<ActionEvent>) e -> {
            stage.close();
        });
        return closeButton;
    }

    private Button createMinimizeButton(Stage stage) throws FileNotFoundException {
        Button minimizeButton = new Button();
        minimizeButton.setCancelButton(true);
        Image minImage = new Image(new FileInputStream("min.png"));
        ImageView minView = new ImageView(minImage);
        minView.setFitHeight(35);
        minView.setPreserveRatio(true);
        minimizeButton.setGraphic(minView);
        minimizeButton.setOnAction((EventHandler<ActionEvent>) e -> {
            stage.setIconified(true);
        });
        return minimizeButton;
    }

    private Button createMaximizeButton(Stage stage) throws FileNotFoundException {
        Button maximizeButton = new Button();
        Image maxImage = new Image(new FileInputStream("max.png"));
        ImageView maxView = new ImageView(maxImage);
        maxView.setFitHeight(35);
        maxView.setPreserveRatio(true);
        maximizeButton.setGraphic(maxView);
        maximizeButton.setOnAction((EventHandler<ActionEvent>) e -> {
            stage.setMaximized(!stage.isMaximized());
        });
        return maximizeButton;
    }

    private Menu createFileMenu(){
        Menu fileMenu = new Menu("file");

        MenuItem saveImage = new MenuItem("save image");
        saveImage.setOnAction((EventHandler<ActionEvent>) event -> {
            // Placeholder action:
            System.out.println("Save Image action.");
        });

        fileMenu.getItems().addAll(saveImage);
        return fileMenu;
    }

    private Menu createEditMenu(){
        Menu editMenu = new Menu("edit");

        MenuItem changeColours = new MenuItem("change colours");
        changeColours.setOnAction((EventHandler<ActionEvent>) e -> {
            // Placeholder action:
            System.out.println("Change Colour action.");
        });

        editMenu.getItems().addAll(changeColours);
        return editMenu;
    }

    private Menu createHelpMenu(Stage stage){
        Menu helpMenu = new Menu("help");
        LandingPage landing = new LandingPage();
        MenuItem aboutSeeMath = new MenuItem("about SeeMath");
        aboutSeeMath.setOnAction((EventHandler<ActionEvent>) e -> {
            try {
                BorderPane pane = (BorderPane) stage.getScene().getRoot();
                ScrollPane sp = (ScrollPane) pane.getCenter();
                VBox visPane = (VBox) sp.getContent();
                visPane.getChildren().clear();
                visPane.getChildren().addAll(landing.getAboutPage(stage));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            System.out.println("About SeeMath action.");
        });

        helpMenu.getItems().addAll(aboutSeeMath);
        return helpMenu;
    }

}
