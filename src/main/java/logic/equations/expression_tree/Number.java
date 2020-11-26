package logic.equations.expression_tree;

public class Number extends Expression {

    /**
     * Creates a number instance from a String
     * @param value string representation of the number
     * @param id the Hypatia-assigned id for this math node
     */
    public Number(String value, String id){
        super(value, id);
        this.setType(ExpType.NUMBER);
    }

    /**
     * Evaluates the number
     * @return double representation of the number
     */
    public Double evaluate(){
        return Double.parseDouble(value);
    }

    public String toString(){
        return String.valueOf(value);
    }
}
