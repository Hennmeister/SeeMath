package gui.vis;

import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.equations.expression_tree.ExpressionTree;

public abstract class Visualizer {

    Font drawFont = new Font(75);
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

    public abstract Pane drawExpression(ExpressionTree tree);

    public abstract Pane drawInt(int num);

    public Pane drawString(String str){
        Text text = new Text();
        text.setFont(drawFont);
        text.setText(str);
        FlowPane pane = new FlowPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setMaxSize(100, 100);
        pane.setAlignment(Pos.BASELINE_CENTER);
        pane.setPrefWrapLength(50);
        pane.getChildren().add(text);
        //pane.setStyle("-fx-border-color: black"); // for debug
        return pane;
    }

}
