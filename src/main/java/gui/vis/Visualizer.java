package gui.vis;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.equations.expression_tree.Expression;

public abstract class Visualizer {

    Font drawFont = new Font(30);
    int nodeSize = 100;
    Color positiveColor = Color.web("4fc3f7");
    Color positiveAccentColor = Color.web("31718f");
    Color negativeColor = Color.web("f74f6e");
    Color negativeAccentColor = Color.web("ad3648");

    public void setDrawFont(Font font){
        this.drawFont = font;
    }

    public Font getDrawFont(){
        return drawFont;
    }

    public void setNodeSize(int num){
        this.nodeSize = num;
    }

    public int getNodeSize(){
        return nodeSize;
    }

    public DropShadow getDropShadow(){
        // Drop-shadow styling:
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(3.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        return dropShadow;
    }

    public InnerShadow getInnerShadow(){
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setRadius(3.0);
        innerShadow.setOffsetX(3.0);
        innerShadow.setOffsetY(3.0);
        innerShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.4));
        return innerShadow;
    }

    public abstract Pane drawExpression(Expression tree);

    public Pane drawString(String str){
        Text text = new Text();
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        //text.setFill(Color.WHITE);
        text.setText(str);
        FlowPane pane = new FlowPane();
        //pane.setHgap(10);
        //pane.setVgap(10);
        //pane.setMaxSize(100, 100);
        pane.setAlignment(Pos.CENTER);
        pane.setPrefWrapLength(50);
        pane.getChildren().add(text);
        //pane.setStyle("-fx-border-color: black"); // for debug
        return pane;
    }

}
