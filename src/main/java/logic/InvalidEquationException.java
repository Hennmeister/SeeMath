package logic;

/**
 * Thrown when an attempt to create an equation out of a non-visualizable
 * expression is made. For example 5 ++ 2 = 7 throws an instance of this class.
 */
public class InvalidEquationException extends Exception {

    /**
     * Constructs an {@code InvalidEquationException} with the specified
     * error message.
     * @param errorMessage the message containing details about the error.
     */
    public InvalidEquationException(String errorMessage) {
        super(errorMessage);
    }
}
