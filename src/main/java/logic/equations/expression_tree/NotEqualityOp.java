package logic.equations.expression_tree;

public class NotEqualityOp extends EqualityOp {

    /**
     * Creates an does not equals operator
     * @param left left sub-expression
     * @param right right sub-expression
     * @param id the Hypatia-assigned id for this math node
     */
    public NotEqualityOp(Expression left, Expression right, String id){
        super(left, right, id);
        setValue("≈");
        this.setType(ExpType.EQUALITY);
    }

    /**
     * Evaluates the this does not equals node based on its sub-expressions (e.g 4 ≠ 3 is True)
     * @return 1 if 'not equality' is true and -1 otherwise
     */
    @Override
    public Double evaluate() {
        if (left.evaluate().equals(right.evaluate())){
            return -1.0;
        }
        return 1.0;
    }

}
