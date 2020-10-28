package logic.equations.expression_tree;

/**
 * Assumes the validation of the expression beforehand
 * Assumes binary operations x + y, x*y
 */

public class ExpressionTree {

    Node root;
    ExpressionTree left;
    ExpressionTree right;

    public ExpressionTree(Node root){
        this.root = root;
    }

    public ExpressionTree(ExpressionTree left, Node root, ExpressionTree right){
        this(root);
        this.left = left;
        this.right = right;
    }

}

class Node {
    Expression value;
    Node left;
    Node right;

    Node(Expression value) {
        this.value = value;
        right = null;
        left = null;
    }
}


