package logic.equations.expression_tree;

import gui.vis.AdditionVisualizer;
import gui.vis.Visualizer;
import javafx.scene.layout.Pane;

public class EqualityOp extends BinaryOp {

    /**
     * Creates an equality operator
     * @param left left sub-expression
     * @param right right sub-expression
     * @param id the Hypatia-assigned id for this math node
     */
    public EqualityOp(Expression left, Expression right, String id){
        super(left, "=", right, id);
        this.setType(ExpType.EQUALITY);
    }

    /**
     * Evaluates the this equality node based on its sub-expressions (e.g 4 = 4 is True and 3 = 4 is False)
     * @return 1 if equality is true and -1 otherwise
     */
    @Override
    public Double evaluate() {
        if (left.evaluate().equals(right.evaluate())){
            return 1.0;
        }
        return -1.0;
    }

    /**
     * Creates proper visualization for this operator
     * @return the visualization as a Pane
     */
    @Override
    public Pane visualization() {
        Visualizer vis = new AdditionVisualizer();
        return vis.drawExpression(this);
    }

}
