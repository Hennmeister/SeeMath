package logic.equations.expression_tree;

public class Number extends Expression {

    double value;

    public Number() {
        this.value = 0;
    }

    public Number(double value){
        this.value = value;
    }

    public double evaluate(){
        return value;
    }

    public Fraction makeFraction(){
        return new Fraction(value);
    }

}