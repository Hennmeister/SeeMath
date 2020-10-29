package gui.vis;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.equations.expression_tree.ExpressionTree;

public abstract class Visualizer {

    Font drawFont = new Font(75);
    int nodeSize = 100;

    public abstract Pane drawExpression(ExpressionTree tree);

    public abstract Pane drawInt(int num);

    public Pane drawString(String str){
        Text text = new Text();
        text.setFont(drawFont);
        text.setText(str);
        FlowPane pane = new FlowPane();
        pane.getChildren().add(text);
        return pane;
    }

}
