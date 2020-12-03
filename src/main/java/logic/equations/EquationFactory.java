package logic.equations;

import logic.InvalidEquationException;
import logic.equations.expression_tree.*;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.Number;
import org.json.JSONArray;
import org.json.JSONObject;

public class EquationFactory {

    /**
     * Construct a equation object from equation data in JSON format
     * @param JSON_expr_data equation data in JSON format
     * @throws InvalidEquationException
     */
    public Equation getEquation(String JSON_expr_data) throws InvalidEquationException {
        // Parse string into Java JSON object
        JSONObject jsonObj = new JSONObject(JSON_expr_data);
        // Retrieve equation meta data
        String mathId = (String) jsonObj.get("mathid");
        Integer problemId = (Integer) jsonObj.get("problem");
        Integer version = (Integer) jsonObj.getInt("version");

        // Get only the first equation in equation array
        JSONArray eqnArray = (JSONArray) ((JSONArray) jsonObj.get("value")).get(0);

        String equalityOp = parseEqualityOperator((String) ((JSONObject) eqnArray.get(1)).get("command"));

        Expression left; Expression right;

        if (equalityOp.equals("≠")) {
            left = parseJSONExprTree(((JSONArray)
                    ( (JSONObject) eqnArray.get(0)).get("children")).getJSONObject(0), false);
            right =  parseJSONExprTree(((JSONArray)
                    ( (JSONObject) eqnArray.get(0)).get("children")).getJSONObject(1), false);
        } else {
            left = parseJSONExprTree((JSONObject) eqnArray.get(0), false);
            right = parseJSONExprTree((JSONObject) eqnArray.get(2), false);
        }
        // Create the equation - notice by default they are correct
        return new Equation(mathId, problemId, version, equalityOp, left, right, true);
    }

    /**
     * Create an expression tree by parsing JSON object
     * @param subExpr The expression tree in JSON object format
     * @param isNegative Stores whether the leaves should be negated
     * @return the Expression Tree parsed from input
     */
    public Expression parseJSONExprTree(JSONObject subExpr, boolean isNegative) throws InvalidEquationException {
        String fullId = subExpr.getString("id");
        String id = fullId.substring(0, fullId.indexOf('$'));
        // Check if the expression is a leaf (number or variable)
        if (!subExpr.has("children")) {
            String rootVal = (String) subExpr.get("value");
            String leafType = (String) subExpr.get("command");
            if (leafType.equals("Number")) {
                return new Number(isNegative ? '-' + rootVal : rootVal, id);
            }
            if (leafType.equals("Symbol")){
                return new Variable(isNegative ? '-' + rootVal : rootVal, id);
            }
            else throw new InvalidEquationException("Leaf is not a Number or Variable");
        } else {
            String operator = (String) subExpr.get("command");

            // Explicit case for 'Negative' Operator since it is the only unary operator we handle
            if (operator.equals("Neg")) {
                return parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(0), !isNegative);
            }

            Expression left = parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(0)
                    , operator.equals("Divide") || operator.equals("Multiply") ? false : isNegative);
            Expression right = parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(1),
                    operator.equals("Minus") != isNegative);
            switch (operator) {
                case "Minus":
                case "Plus":
                    return new AdditionOp(left, right, id);
                case "Divide":
                    return new DivisionOp(left, right, id);
                case "Multiply":
                    return new MultiplicationOp(left, right, id);
                case "Exponent":
                    return new ExponentOp(left, right, id);
                default:
                    throw new InvalidEquationException("Operator not recognized");
            }
        }
    }

    /**
     * Find the appropriate string representation for an equality operator
     * @param equalityOpJSON The string equality operator command created by Hypatia
     * @return The string representation of given operator in this app
     * @throws InvalidEquationException
     */
    public String parseEqualityOperator(String equalityOpJSON) throws InvalidEquationException {
        switch (equalityOpJSON) {
            case "=":
                return equalityOpJSON;
            case "Approx":
                return "≈";
            case "<>":
                return "≠";
            default:
                throw new InvalidEquationException("Equality Operator not recognized");
        }
    }
}
