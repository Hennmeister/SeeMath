package logic.equations.expression_tree;

public class ExponentOp extends BinaryOp {

    /**
     * Creates an exponential operator
     * @param left left sub-expression representing the base
     * @param right right sub-expression representing the exponent
     */
    public ExponentOp(Expression left, Expression right){
        super(left, "^", right);
        this.setType(ExpType.EXPONENT);
    }

    /**
     * Evaluates the this exponential node based on its sub-expressions (e.g 4^3)
     * @return the resulting number of the operation
     */
    public Double evaluate() {
        return Math.pow(left.evaluate(), right.evaluate());
    }

}
