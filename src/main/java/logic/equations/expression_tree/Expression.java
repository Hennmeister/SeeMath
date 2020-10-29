package logic.equations.expression_tree;


public abstract class Expression<T> {

    T value;

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
