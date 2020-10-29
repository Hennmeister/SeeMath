package gui.vis;

import javafx.scene.layout.Pane;
import logic.equations.AdditionEquation;
import logic.equations.ExpressionTree;

public abstract class Visualizer {

    public Visualizer(){}

    public abstract Pane constructPane(ExpressionTree tree);


}
