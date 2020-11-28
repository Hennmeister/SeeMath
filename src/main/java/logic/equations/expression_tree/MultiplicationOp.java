package logic.equations.expression_tree;

import gui.vis.AdditionVisualizer;
import gui.vis.MultiplicationVisualizer;
import gui.vis.Visualizer;
import javafx.scene.layout.Pane;

public class MultiplicationOp extends BinaryOp {

    /**
     * Creates a multiplication operator
     * @param left left sub-expression
     * @param right right sub-expression
     * @param id the Hypatia-assigned id for this math node
     */
    public MultiplicationOp(Expression left, Expression right, String id){
        super(left, "*", right, id);
        this.setType(ExpType.MULTIPLICATION);
    }

    /**
     * Evaluates the this multiplication node based on its sub-expressions (e.g 4*3)
     * @return the resulting number of the operation
     */
    @Override
    public Double evaluate() {
        return left.evaluate() * right.evaluate();
    }

    /**
     * Creates proper visualization for this operator
     * @return the visualization as a Pane
     */
    @Override
    public Pane visualization() {
        Visualizer vis = new MultiplicationVisualizer();
        return vis.drawExpression(this);
    }

}
