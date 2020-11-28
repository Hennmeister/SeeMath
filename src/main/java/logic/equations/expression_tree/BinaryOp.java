package logic.equations.expression_tree;

import gui.vis.Visualizer;
import javafx.scene.layout.Pane;

public abstract class BinaryOp extends Expression {

    /**
     * Creates a binary operator from left and right
     * @param left left sub-expression
     * @param value string representation of binary operator
     * @param right right sub-tree
     * @param id the Hypatia-assigned id for this math node
     */
    public BinaryOp(Expression left, String value, Expression right, String id){
        super(value, id);
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the this binary operator node based on its sub-expressions (e.g 4+3)
     * @return the resulting number of the operation
     */
    public abstract Double evaluate();

    /**
     * Creates proper visualization for this operator
     * @return the visualization as a Pane
     */
    public abstract Pane visualization();

}
