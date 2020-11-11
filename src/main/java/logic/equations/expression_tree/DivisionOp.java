package logic.equations.expression_tree;

public class DivisionOp extends BinaryOp {

    /**
     * Creates a division operator
     * @param left left sub-expression
     * @param right right sub-expression
     */
    public DivisionOp(Expression left, Expression right){
        super(left, "/", right);
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
