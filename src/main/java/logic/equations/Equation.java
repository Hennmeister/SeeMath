package logic.equations;

import logic.equations.expression_tree.ExpressionTree;

public class Equation {
    private String id;
    private int problemId;
    private ExpressionTree tree;
    boolean isCorrect;
    String type;

    public Equation (String id, int problemId, ExpressionTree tree, boolean isCorrect, String type)  {
        this.id = id;
        this.problemId = problemId;
        this.tree = tree;
        this.isCorrect = isCorrect;
        this.type = type;
    }

    public ExpressionTree getTree() {
        return tree;
    }

    public String getId() {
        return id;
    }

    public int getProblemId() {
        return problemId;
    }

    public String getType() {
        return type;
    }
}
