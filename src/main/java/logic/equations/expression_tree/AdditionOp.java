package logic.equations.expression_tree;

public class AdditionOp extends BinaryOp {

    /**
     * Creates an addition operator
     * @param left left sub-expression
     * @param right right sub-expression
     */
    public AdditionOp(Expression left, Expression right){
        super(left, "+", right);
        this.setType(ExpType.ADDITION);
    }

    /**
     * Evaluates the this addition node based on its sub-expressions (e.g 4+3)
     * @return the resulting number of the operation
     */
    public Double evaluate() {
        return left.evaluate() + right.evaluate();
    }

}
