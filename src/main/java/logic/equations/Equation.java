package logic.equations;

import logic.equations.expression_tree.ExpressionTree;

public abstract class Equation {
    private int id;
    private int problemId;
    private ExpressionTree tree;
    boolean isCorrect;

    public Equation (int id, int problemId, ExpressionTree tree, boolean isCorrect)  {
        this.id = id;
        this.problemId = problemId;
        this.tree = tree;
        this.isCorrect = isCorrect;
    }
}
