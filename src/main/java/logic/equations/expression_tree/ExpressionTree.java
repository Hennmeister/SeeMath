package logic.equations.expression_tree;

/**
 * Assumes the validation of the expression beforehand
 * Assumes binary operations x + y, x*y
 */

public class ExpressionTree {

    Expression root;
    ExpressionTree left, right;

    /**
     * Creates an ExpressionTree from the root node
     * @param root: the expression on this node
     */
    public ExpressionTree(Expression root){
        this.root = root;
    }

    public ExpressionTree(ExpressionTree left, Expression root, ExpressionTree right){
        this(root);
        this.left = left;
        this.right = right;
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
        // else expression tree is unbalanced i.e x + _
        return false;
    }
}
