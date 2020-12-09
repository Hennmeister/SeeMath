package logic.equations.expression_tree;

import gui.vis.AdditionVisualizer;
import gui.vis.Visualizer;
import javafx.scene.layout.Pane;

public class Variable extends Number {
    /**
     * Creates a variable instance from a String
     * @param value string representation of the variable
     * @param id the Hypatia-assigned id for this math node
     */
    public Variable(String value, String id){
        super(value, id);
        this.setType(ExpType.VARIABLE);
    }

    /**
     * Evaluates the variable - since they are only used in graph visualizer, we just return 1 as a default
     * @return Default value of 1.0
     */
    @Override
    public Double evaluate(){ return 1.0; }

    /**
     * Creates proper visualization for this operator
     * @return the visualization as a Pane
     */
    @Override
    public Pane visualization() {
        // default visualizer
        Visualizer vis = new AdditionVisualizer();
        return vis.drawExpression(this);
    }

    public String toString(){
        return value.charAt(0) == '-' ? "(" + value + ")" : value;
    }
}
