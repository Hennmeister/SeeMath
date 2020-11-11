package logic.equations.expression_tree;

public class MultiplicationOp extends BinaryOp {

    /**
     * Creates a multiplication operator
     * @param left left sub-expression
     * @param right right sub-expression
     */
    public MultiplicationOp(Expression left, Expression right){
        super(left, "*", right);
        this.setType(ExpType.MULTIPLICATION);
    }

    /**
     * Evaluates the this multiplication node based on its sub-expressions (e.g 4*3)
     * @return the resulting number of the operation
     */
    public Double evaluate() {
        return left.evaluate() * right.evaluate();
    }

}
