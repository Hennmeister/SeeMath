package logic.equations;

import logic.equations.expression_tree.ExpType;
import logic.equations.expression_tree.Expression;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Equation {
  
    private final String mathBlockId;
    private final int problemId;
    private final int version;
    private final String equalityOperator;
    private final Expression leftTree;
    private final Expression rightTree;
    private boolean isCorrect;

    /**
     * Contructs a new equation object
     * @param id The id of the math block
     * @param problemId The id of the problem in the Hypatia assignment
     * @param version The version of the Hypatia doc
     * @param equalityOperator The equality operator (=, ≈, ≠) connecting the two expression trees
     * @param leftTree The left expression in the equation
     * @param rightTree The right expression in the equation
     * @param isCorrect Whether the equation is true or not
     */
    public Equation (String id, int problemId, int version, String equalityOperator,
                     Expression leftTree, Expression rightTree, boolean isCorrect)  {
        this.mathBlockId = id;
        this.problemId = problemId;
        this.version = version;
        this.leftTree = leftTree;
        this.rightTree = rightTree;
        this.isCorrect = isCorrect;
        this.equalityOperator = equalityOperator;
    }

    public boolean containsExpression(String id){
        return leftTree.containsId(id) || rightTree.containsId(id);
    }

    /**
     * @return The left expression of the equation tree, with operators as internal nodes and
     * leaves as values.
     */
    public Expression getLeftTree() {
        return leftTree;
    }

    /**
     * @return The right expression of the equation tree, with operators as internal nodes and
     * leaves as values.
     */
    public Expression getRightTree() {
        return rightTree;
    }

    /**
     * @return The id of the math block containing this equation in Hypatia.
     */
    public String getMathBlockId() {
        return mathBlockId;
    }

    /**
     * @return The id of the problem of an assignment in Hypatia which contains this equation
     */
    public int getProblemId() {
        return problemId;
    }

    /**
     * @return The version number of the Hypatia doc
     */
    public int getVersion(){ return version;}

    /**
     * @return Whether this equation is correct mathematically, so if the left side and right side are correct under
     * the given operator found at the root.
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * @return Get the equality operator joining the expressions of the equation
     */
    public String getEqualityOperator() {
        return equalityOperator;
    }

    /**
     * Sets whether this equation is logically correct
     * @param correct true if equation is logically correct; false otherwise
     */
    public void setCorrectness(boolean correct) {
        isCorrect = correct;
    }

    /**
     * Checks if equation is graph visualizable
     * Rules:
     * - equation has exactly 2 variables
     * - left side is a single variable (e.g. y = __)
     * - right side has a variable different than the one on the left side
     * @return true if graph is visualizable and false otherwise
     */
    public boolean graphVisualizable(){
        Set<String> leftVars = leftTree.distinctVariables();
        Set<String> rightVars = rightTree.distinctVariables();
        Set<String> vars = new HashSet<>(leftVars);
        vars.addAll(rightVars);
        // The implementation is explicit like this for scalability purposes
        boolean leftConditions = leftTree.isLeaf() && (leftVars.size() == 1);
        boolean variableConditions = vars.size() == 2 && !leftVars.equals(rightVars);
        return  leftConditions && variableConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation = (Equation) o;
        return problemId == equation.problemId &&
                mathBlockId.equals(equation.mathBlockId) &&
                leftTree.equals(equation.leftTree) &&
                rightTree.equals(equation.rightTree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mathBlockId, problemId);
    }

    @Override
    public String toString() {
        return "Equation: {" +
                ", problemId=" + problemId + '\'' +
                ", isCorrect=" + isCorrect() + '\'' +
                ", expression: " + leftTree.toString() + equalityOperator + rightTree.toString() +
                '}';
    }

    public String toStringLabel() {
        return "\"" + leftTree.toString() + equalityOperator + rightTree.toString() + "\"";
    }
}
