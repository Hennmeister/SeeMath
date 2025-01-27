package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class is responsible for generating the landing page and about page for the SeeMath Hypatia Companion
 * Application.
 */

public class LandingPage {

    /**
     * Generates a landing page for SeeMath containing the project logo, and a quick instruction informing users how to
     * begin using the application.
     * @param stage The primary stage of the JavaFX app.
     * @return A VBox pane that stores the various nodes of the landing page.
     * @throws FileNotFoundException
     */

    public Pane getLandingPage(Stage stage) throws FileNotFoundException {

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #6EC2F2");
        layout.setPrefSize(900, 500);
        Label label = new Label("a Hypatia companion app");
        label.setFont(new Font("Century Gothic", 24));

        VBox buffer = new VBox();
        VBox.setVgrow(buffer, Priority.ALWAYS);

        Text instructional = new Text("Edit an equation in Hypatia's CheckMath Evaluate to begin.");
        instructional.setStyle("-fx-font-style: italic");
        instructional.setFont(new Font("Century Gothic", 12));

        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(getLogo(), label, instructional);
        return layout;
    }


    /**
     * Retrieves the SeeMath Logo via an input stream and creates an ImageView node to store it in.
     * @return An Imageview node containing the SeeMath logo.
     * @throws FileNotFoundException
     */

    private ImageView getLogo() throws FileNotFoundException {
        //Import SeeMath Logo
        Image logo = new Image(new FileInputStream("SeeMath Logo 2.png"));

        //ImageView
        ImageView imageView = new ImageView(logo);

        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(50);

        //setting the fit height and width of the image view
        imageView.setFitHeight(455);
        imageView.setFitWidth(500);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        return imageView;
    }

    /**
     * Generates the about page for the SeeMath application. This page contains information about the development team,
     * development licenses, the purpose of the application and version number.
     * @param stage The PrimaryStage of the JavaFx app.
     * @return A VBox pane that contains the various nodes for the about page.
     * @throws FileNotFoundException
     */

    public Pane getAboutPage(Stage stage) throws FileNotFoundException {
        VBox layout = new VBox();
        layout.setSpacing(50);
        layout.setAlignment(Pos.CENTER);


        Label aboutLabel = new Label("About SeeMath");
        aboutLabel.setFont(new Font("Century Gothic", 24));

        Label version = new Label("Version: \n");
        Text versionInfo = new Text("Version 1.0 \n");
        Label about = new Label ("About: \n");
        Text aboutInfo = new Text("SeeMath is a companion application to Hypatia's CheckMath software. \n" +
                "This application will generate mathematical visualizations for the problems entered into " +
                "CheckMath.\n");
        Label hypothesis = new Label("Project Hypothesis: \n");
        Text hypothesisInfo = new Text("The visualization of mathematical problems gives students " +
                "different perspectives to help them solve the problems. \n");
        Label licences = new Label("Licenses:\n");
        Text licensesInfo = new Text("MIT License \n");
        Label developers = new Label("Developers:\n");
        Text teamName = new Text("Team Orestes \n");
        Text developersInfo = new Text(" Henning Lindig \n Jacob Sahlmueller \n " +
                "Tales Scopinho \n Piyush Sharma \n Affanullah Siddiqui");
        TextFlow flow = new TextFlow(version, versionInfo, about, aboutInfo, hypothesis, hypothesisInfo,
                licences, licensesInfo, developers, teamName, developersInfo);

        flow.setLineSpacing(2.0);

        Button home = new Button("Home");
        home.setOnAction(e -> {
            try {
                BorderPane pane = (BorderPane) stage.getScene().getRoot();
                ScrollPane sp = (ScrollPane) pane.getCenter();
                VBox visPane = (VBox) sp.getContent();
                visPane.getChildren().clear();
                visPane.getChildren().addAll(getLandingPage(stage));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        layout.getChildren().addAll(aboutLabel, getLogo(), home, flow);
        return layout;

    }

}
