package gui.vis;

import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.equations.expression_tree.Expression;

public abstract class Visualizer {

    Font drawFont = new Font(30);
    int nodeSize = 100;

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

    public abstract Pane drawExpression(Expression tree);

    public Pane drawString(String str){
        Text text = new Text();
        text.setFont(drawFont);
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
