package logic.equations.expression_tree;


public class Expression<T> {

    T value;

    /**
     * Creates an expression with the value inputted
     * @param value: value of expression
     */
    public Expression(T value){
        this.value = value;
    }

    protected T evaluate(){
        return value;
    }

    public String toString(){
        return String.valueOf(value);
    }
}
