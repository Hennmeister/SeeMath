package logic.equations.expression_tree;

public class Fraction {

    double value;
    int numerator, denominator;

    /**
     * Fraction representation of a Number
     * @param value: decimal value
     */
    public Fraction(Double value){
        this.value = value;
        make(value);
        this.reduce();
    }

    public Fraction(Integer numerator, Integer denominator){
        this((double) (numerator/denominator));
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Makes a non-reduced fraction out of a double (i.e. make(2.5) = 25/10)
     * @param value: decimal value
     */
    private void make(Double value){
        String s = String.valueOf(value);
        int decimals = s.length() - 1 - s.indexOf('.');
        int denom = 1;
        for(int i = 0; i < decimals; i++){
            value *= 10;
            denom *= 10;
        }
        numerator = (int) Math.round(value);
        denominator = denom;
    }

    /**
     * Reduces the fraction
     */
    public void reduce(){
        int gcd = 1;
        for(int i = 1; i <= numerator && i <= denominator; i++) {
            if(numerator % i == 0 && denominator % i == 0) { gcd = i; }
        }
        numerator /= gcd;
        denominator /= gcd;
    }

}
