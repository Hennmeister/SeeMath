package logic.equations.expression_tree;


public abstract class Expression<T> {

    T value;

//    TODO: figure out how to work with generic subclasses
//    public Expression(T value){
//        this.value = value;
//    }

    //protected abstract T evaluate();

    public String toString(){
        return String.valueOf(value);
    }
}
