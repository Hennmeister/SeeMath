package logic.equations.expression_tree;

import gui.vis.AdditionVisualizer;
import gui.vis.Visualizer;
import javafx.scene.layout.Pane;

public class ExponentOp extends BinaryOp {

    /**
     * Creates an exponential operator
     * @param left left sub-expression representing the base
     * @param right right sub-expression representing the exponent
     * @param id the Hypatia-assigned id for this math node
     */
    public ExponentOp(Expression left, Expression right, String id){
        super(left, "^", right, id);
        this.setType(ExpType.EXPONENT);
    }

    /**
     * The exponent operator does not have a specific visualization.
     * Therefore, it should never be visualized outside of the graphs
     * @return false since exponents are non-visualizable
     */
    public boolean isValid(){
        return false;
    }

    /**
     * Evaluates the this exponential node based on its sub-expressions (e.g 4^3)
     * @return the resulting number of the operation
     */
    @Override
    public Double evaluate() {
        return Math.pow(left.evaluate(), right.evaluate());
    }

    /**
     * Creates proper visualization for this operator
     * @return the visualization as a Pane
     */
    @Override
    public Pane visualization() {
        // TODO: replace with exponent visualizer once this is implemented
        Visualizer vis = new AdditionVisualizer();
        return vis.drawExpression(this);
    }

}
