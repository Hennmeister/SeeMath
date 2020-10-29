package logic.equations.expression_tree;

/**
 * BinaryOp handles the following operators:
 *  'Add'
 *  'Multiply'
 *  with fractions
 */

public class BinaryOp extends Expression{

    String value;

    public BinaryOp(String value){
        this.value = value;
    }

    public String evaluate(){
        return value;
    }

}
