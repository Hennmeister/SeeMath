package logic.equations;

import logic.equations.expression_tree.ExpressionTree;

public class Equation {
    private String id;
    private int problemId;
    private ExpressionTree tree;
    private boolean isCorrect;
    private String visualizationType;

    /**
     * Contructs a new equation object
     * @param id The id of the math block
     * @param problemId The id of the problem in the Hypatia assignment
     * @param tree The expression tree representing the equation values
     * @param isCorrect Whether the equation is true or not
     * @param visualizationType The type of the visualization used for the equation, such as addition and multiplication
     */
    public Equation (String id, int problemId, ExpressionTree tree, boolean isCorrect, String visualizationType)  {
        this.id = id;
        this.problemId = problemId;
        this.tree = tree;
        this.isCorrect = isCorrect;
        this.visualizationType = visualizationType;
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
    public String getId() {
        return id;
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

    /**
     * @return The type of visualization that should be used to represent this equation.
     */
    public String getVisualizationType() {
        return visualizationType;
    }
}
