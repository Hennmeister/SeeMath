package gui;

import gui.vis.AdditionVisualizer;
import gui.vis.GraphVisualizer;
import gui.vis.Visualizer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.WebController;
import logic.equations.Equation;
import logic.equations.expression_tree.Expression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class VisualizationPresenter implements VisualizationCreator {

    private Stage stage;
    private ArrayList<Node> visList;
    private WebController serverController;
    private PhotoHintPresenter photoHintPresenter;

    public VisualizationPresenter(Stage stage, WebController serverController) {
        this.stage = stage;
        this.visList = new ArrayList<Node>();
        this.serverController = serverController;
        this.photoHintPresenter = new PhotoHintPresenter();
    }

    /**
     * Creates an appropriate visualization for equation by calling the correct visualizer
     * @param eqn The equation to visualize
     * @return the visualization of expression if valid, or an error message
     */
    public Pane makeVisualization(Equation eqn){
        FlowPane layout = new FlowPane();
        layout.setPrefWrapLength(900);
        layout.setVgap(10);
        layout.setAlignment(Pos.TOP_CENTER);
        if (eqn.getLeftTree().isValid() && eqn.getRightTree().isValid()) {
            AdditionVisualizer vis = new AdditionVisualizer();
            layout.getChildren().addAll(eqn.getLeftTree().visualization(), vis.drawString(eqn.getEqualityOperator()),
                    eqn.getRightTree().visualization());
            sendHint(layout, eqn);
        }
        else{
            Label label = new Label("Unsupported Visualization.");
            layout.getChildren().add(label);
        }
        return layout;
    }

    /**
     * Update the UI with the new equation visualization
     * @param eqn The new equation that whose visualization should be shown
     */
    @Override
    public void updateVisualization(Equation eqn) {
        // Runs the UI related logic on the javaFX thread
        Platform.runLater(() -> {

            // displays equation id
            Label label = new Label("Equation: " + eqn.toStringLabel() + " - ID: " + eqn.getProblemId());
            label.setFont(new Font("Verdana", 24));

            Pane drawEqn;

            if (eqn.graphVisualizable()){
                GraphVisualizer vis = new GraphVisualizer();
                // assuming graph is of the form y = ....
                // so we visualize the right subtree
                drawEqn = vis.drawExpression(eqn.getRightTree());
                sendHint(drawEqn, eqn);
            } else{
                drawEqn = makeVisualization(eqn);
                sendHint(drawEqn, eqn);
            }

            // Visualize whether of not the equation is correct
            HBox correctPane = new HBox();
            correctPane.setAlignment(Pos.CENTER);
            correctPane.setSpacing(10);
            correctPane.getChildren().add(label);
            if (eqn.isCorrect()){
                try {
                    correctPane.getChildren().add(getCorrect());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    correctPane.getChildren().add(getIncorrect());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // Store the Visualization and Label for later access, add a Line between equations for visual clarity
            if (visList.size() > 0){
                Line line = new Line(0, 0, 500, 0);
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(3.0);
                dropShadow.setOffsetX(3.0);
                dropShadow.setOffsetY(3.0);
                dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
                line.setEffect(dropShadow);
                visList.add(line);
            }
            visList.add(drawEqn);
            visList.add(correctPane);

            // Navigate through the UI objects to get to the visPane
            BorderPane ui = (BorderPane) stage.getScene().getRoot();
            ScrollPane sp = (ScrollPane) ui.getCenter();
            VBox visPane = (VBox) sp.getContent();

            // Empty the visPane, before adding in all the stored visualizations
            visPane.getChildren().clear();
            for (int i = visList.size() - 1; i >= 0; i -= 1) {
                 visPane.getChildren().add(visList.get(i));
            }
            stage.setScene(stage.getScene());
            stage.show();
        });
    }

    private ImageView getCorrect() throws FileNotFoundException {
        Image correctImg = new Image(new FileInputStream("correct.png"));
        ImageView correctView = new ImageView(correctImg);
        correctView.setFitHeight(35);
        correctView.setPreserveRatio(true);
        return correctView;
    }

    private ImageView getIncorrect() throws FileNotFoundException {
        Image incorrectImg = new Image(new FileInputStream("incorrect.png"));
        ImageView incorrectView = new ImageView(incorrectImg);
        incorrectView.setFitHeight(35);
        incorrectView.setPreserveRatio(true);
        return incorrectView;

    private void sendHint(Pane vis, Equation eqn) {
        String base64Image = photoHintPresenter.getPhotoHint(vis);
        if (!eqn.isCorrect()) {
            serverController.sendVisualHint(eqn, base64Image);
        }
    }
}
