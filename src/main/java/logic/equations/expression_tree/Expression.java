package logic.equations.expression_tree;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        if (right == null && left == null){
            return true;
        } else if (right != null && left != null) {
            // is an operator
            return right.isValid() && left.isValid();
        }
        // else expression is unbalanced i.e x + _
        return false;
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

    /**
     * Gets all the leaves from the tree
     * @return an AraayList containing the number value of all leaves
     */
    public ArrayList<Double> getLeaves(){
        ArrayList<Double> leaves = new ArrayList<>();
        if (value == null) {
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
     * Checks if tree contains a node of type {@code type}
     * @param type expression type to check for
     * @return true if Expression contain {@code type} and false otherwise
     */
    public boolean hasType(ExpType type){
        return this.getType() == type || (!this.isLeaf() && (left.hasType(type) || right.hasType(type)));
    }

    /**
     * Finds all distinct variables in expression tree
     * @return a set containing the string value of all variables in the expression
     */
    public Set<String> distinctVariables(){
        Set<String> var = new HashSet<>();
        return findDistinctVar(var);
    }

    /**
     * Helper method for finding distinctVariables()
     * @param var a set containing variables in expression
     * @return the set containing all variables in expression (recursively)
     */
    private Set<String> findDistinctVar(Set<String> var){
        if (right == null && left == null){
            if (this.getType() == ExpType.VARIABLE) {
                var.add(this.getValue());
            }
        } else {
            Set<String> copy = new HashSet<>(var);
            var.addAll(left.findDistinctVar(copy));
            var.addAll(right.findDistinctVar(copy));
        }
        return var;
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

    public Expression findLeftMostLeaf() {
        if (this.isLeaf()) {
            return this;
        }
        else {
            return left.findLeftMostLeaf();
        }
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
        return "(" + left.toString() + value + right.toString() + ")";
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
