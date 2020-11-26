package logic.equations.expression_tree;

public class Variable extends Expression {
    /**
     * Creates a variable instance from a String
     * @param value string representation of the variable
     * @param id the Hypatia-assigned id for this math node
     */
    public Variable(String value, String id){
        super(value, id);
        this.setType(ExpType.VARIABLE);
    }

    /**
     * Evaluates the variable - since they are only used in graph visualizer, we just return 1 as a default
     * @return Default value of 1.0
     */
    public Double evaluate(){ return 1.0; }

    public String toString(){
        return value;
    }
}
