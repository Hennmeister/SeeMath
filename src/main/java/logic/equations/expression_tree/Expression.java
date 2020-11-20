package logic.equations.expression_tree;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Expression {

    String value;
    Expression left, right = null;
    ExpType type;
    String id;

    /**
     * Creates a leaf expression with the value inputted
     * @param value value of expression
     * @param id the Hypatia-assigned id for this math node
     */
    public Expression(String value, String id){
        this.value = value;
        this.id = id;
        // left and right are null
    }

    /**
     * Creates an expression with the values inputted
     * @param left left sub-expression
     * @param value value of current expression
     * @param right right sub-expression
     * @param id the Hypatia-assigned id for this math node
     */
    public Expression(Expression left, String value, Expression right, String id){
        this.value = value;
        this.left = left;
        this.right = right;
        this.id = id;
    }

    /**
     * Checks validity of expression tree
     * @return whether tree is valid (recursively)
     */
    public boolean isValid(){
        if(right == null && left == null){
            return true;
        } else if(right != null && left != null) {
            return right.isValid() && left.isValid();
        }
        // else expression is unbalanced i.e x + _
        return false;
    }

    public abstract Double evaluate();

    /**
     * Gets all the leaves from the tree
     * @return an AraayList containing the number value of all leaves
     */
    public ArrayList<Double> getLeaves(){
        ArrayList<Double> leaves = new ArrayList<>();
        if (value == null){
            return leaves;
        }
        else if (this.isLeaf()) {
            leaves.add(this.evaluate());
            return leaves;
        }
        else {
            leaves.addAll(left.getLeaves());
            leaves.addAll(right.getLeaves());
            return leaves;
        }
    }

    /**
     * Check if this expression or its children contain the given id
     * @param id The id of the math node assigned by Hypatia
     * @return true if this expression tree contains the given id; False otherwise
     */
    public boolean containsId(String id){
        return this.getId().equals(id) || (!this.isLeaf() && (this.left.containsId(id) || this.right.containsId(id)));
    }

    /**
     * Checks if this node is a leaf node
     * @return whether node is a leaf
     */
    public boolean isLeaf(){
        return left == null && right == null;
    }

    public ExpType getType(){
        return type;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public String getId() { return id; }

    public void setType(ExpType type){
        this.type = type;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // getValue() is the same as toString()
    public String getValue(){
        return value;
    }

    public String toString(){
        return left.toString() + (value.charAt(0) == '-' ? "(" + value + ")" : value) + right.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expression that = (Expression) o;
        return getValue().equals(that.getValue()) &&
                Objects.equals(left, that.left) &&
                Objects.equals(right, that.right) &&
                getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), left, right, getType());
    }
}
