package logic.equations.expression_tree;

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
     * Evaluates the this division node based on its sub-expressions (e.g 4/3)
     * @return the resulting number of the operation
     */
    public Double evaluate() {
        return left.evaluate() / right.evaluate();
    }

}
