package logic.equations.expression_tree;

import gui.vis.AdditionVisualizer;
import gui.vis.Visualizer;
import javafx.scene.layout.Pane;

public class Number extends Expression {

    /**
     * Creates a number instance from a String
     * @param value string representation of the number
     * @param id the Hypatia-assigned id for this math node
     */
    public Number(String value, String id){
        super(value, id);
        this.setType(ExpType.NUMBER);
    }

    /**
     * Evaluates the number
     * @return double representation of the number
     */
    @Override
    public Double evaluate(){
        return Double.parseDouble(value);
    }

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
