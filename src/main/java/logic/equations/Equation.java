package logic.equations;

import logic.equations.expression_tree.ExpressionTree;

import java.util.Objects;

public class Equation {
    private String mathBlockId;
    private int problemId;
    private ExpressionTree tree;
    private boolean isCorrect;

    /**
     * Contructs a new equation object
     * @param id The id of the math block
     * @param problemId The id of the problem in the Hypatia assignment
     * @param tree The expression tree representing the equation values
     * @param isCorrect Whether the equation is true or not
     */
    public Equation (String id, int problemId, ExpressionTree tree, boolean isCorrect)  {
        this.mathBlockId = id;
        this.problemId = problemId;
        this.tree = tree;
        this.isCorrect = isCorrect;
    }

    /**
     * @return The expression tree representing the equation, with operators as internal nodes and
     * leaves as values.
     */
    public ExpressionTree getTree() {
        return tree;
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
     * @return Whether this equation is correct mathematically, so if the left side and right side are correct under
     * the given operator fonud at the root.
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation = (Equation) o;
        return problemId == equation.problemId &&
                mathBlockId.equals(equation.mathBlockId) &&
                tree.equals(equation.tree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mathBlockId, problemId, tree);
    }

    @Override
    public String toString() {
        return "Equation{" +
                "mathBlockId='" + mathBlockId + '\'' +
                ", problemId=" + problemId +
                '}';
    }
}
