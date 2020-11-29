package logic.equations.expression_tree;

import gui.vis.AdditionVisualizer;
import gui.vis.FractionVisualizer;
import gui.vis.Visualizer;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class DivisionOp extends BinaryOp {

    /**
     * Creates a division operator
     * @param left left sub-expression
     * @param right right sub-expression
     * @param id the Hypatia-assigned id for this math node
     */
    public DivisionOp(Expression left, Expression right, String id){
        super(left, "/", right, id);
        this.setType(ExpType.DIVISION);
    }

    /**
     * Checks validity of an fraction node
     * - Rule: fraction are multiplication are not mixed together
     * @return whether node is valid
     */
    @Override
    public boolean isValid(){
        return left.getType() != ExpType.MULTIPLICATION && right.getType() != ExpType.MULTIPLICATION;
    }

    /**
     * Evaluates the this division node based on its sub-expressions (e.g 4/3)
     * @return the resulting number of the operation
     */
    @Override
    public Double evaluate() {
        return left.evaluate() / right.evaluate();
    }

    /**
     * Creates proper visualization for this operator
     * @return the visualization as a Pane
     */
    @Override
    public Pane visualization() {
        Visualizer vis = new FractionVisualizer();
        return vis.drawExpression(this);
    }

}
