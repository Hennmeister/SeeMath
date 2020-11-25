package logic.equations.expression_tree;

public class ApproxEqualityOp extends EqualityOp {

    /**
     * Creates an approximately equals operator
     * @param left left sub-expression
     * @param right right sub-expression
     * @param id the Hypatia-assigned id for this math node
     */
    public ApproxEqualityOp(Expression left, Expression right, String id){
        super(left, right, id);
        setValue("≈");
        this.setType(ExpType.EQUALITY);
    }

    /**
     * Evaluates the this approximately equals node based on its sub-expressions
     * Rule: results are within 0.1 of each other
     * @return 1 if equality is true and -1 otherwise
     */
    @Override
    public Double evaluate() {
        if (Math.abs(left.evaluate() - right.evaluate()) <= 0.1){
            return 1.0;
        }
        return -1.0;
    }

}
