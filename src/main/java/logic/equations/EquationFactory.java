package logic.equations;

import logic.InvalidEquationException;
import logic.equations.expression_tree.*;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.Number;
import org.json.JSONArray;
import org.json.JSONObject;

// Sample Equation Input
// {"problem":1,"docid":"916.6.2","docname":"Assignment *","userid":1378,"version":87,
//        "value":[[{"id":"chr1378-10$chr1378-10","value":"8","command":"Number"},
//        {"id":"chr1378-7$chr1378-7","command":"="},
//        {"children":[{"id":"chr1378-69$chr1378-69","value":"7","command":"Number"},
//        {"id":"chr1378-165$chr1378-165","value":"5","command":"Number"}],
//        "id":"chr1378-69$chr1378-165","command":"Plus"}]],"mathid":"tex10.mth1378-2","username":"Henning L"}


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
        String equationBlockId = (String) jsonObj.get("mathid");
        Integer problemId = (Integer) jsonObj.get("problem");

        // Get only the first equation in equation array
        JSONArray eqnArray = (JSONArray) ((JSONArray) jsonObj.get("value")).get(0);

        Expression left = parseJSONExprTree((JSONObject) eqnArray.get(0), false);
        Expression right = parseJSONExprTree((JSONObject) eqnArray.get(2), false);

        // Create the equation
        // TEMP: equations are always false
        Equation eqn = new Equation(equationBlockId, problemId, left, right, false);
        return eqn;
    }

    /**
     * Create an expresion tree by parsing JSON object
     * @param subExpr The expression tree in JSON object format
     * @param isNegative Stores whether the leaves should be negated
     * @return the Expression Tree parsed from input
     */
    public Expression parseJSONExprTree(JSONObject subExpr, boolean isNegative) throws InvalidEquationException {
        // Check if the expression is a leaf (number or variable)
        if (!subExpr.has("children")) {
            String rootVal = (String) subExpr.get("value");
            String leafType = (String) subExpr.get("command");
            if (leafType.equals("Number")) {
                return new Number(isNegative ? '-' + rootVal : rootVal);
            }
            if (leafType.equals("Symbol")){
                return new Variable(isNegative ? '-' + rootVal : rootVal);
            }
            else throw new InvalidEquationException("Leaf is not a Number or Variable");
        } else {
            String operator = (String) subExpr.get("command");

            // Explicit case for 'Negative' Operator since it is the only unary operator we handle
            if (operator.equals("Neg")) {
                return parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(0), !isNegative);
            }

            Expression left = parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(0), isNegative);
            Expression right = parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(1),
                    operator.equals("Minus") != isNegative);
            switch (operator) {
                case "Plus":
                case "Minus":
                    return new AdditionOp(left, right);
                case "Divide":
                    return new DivisionOp(left, right);
                case "Multiply":
                    return new MultiplicationOp(left, right);
                case "Exponent":
                    return new ExponentOp(left, right);
                default:
                    throw new InvalidEquationException("Operator not recognized");
            }
        }
    }
}
