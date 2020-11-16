package logic.equations;

import com.sun.org.apache.xpath.internal.operations.Div;
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

        Expression left = parseJSONExprTree((JSONObject) eqnArray.get(0));
        Expression right = parseJSONExprTree((JSONObject) eqnArray.get(2));

        // Create the equation
        // TEMP: equations are always false
        Equation eqn = new Equation(equationBlockId, problemId, left, right, false);
        return eqn;
    }

    /**
     * Create an expresion tree by parsing JSON object
     * @param subExpr The expression tree in JSON object format
     * @return the Expression Tree parsed from input
     */
    public Expression parseJSONExprTree(JSONObject subExpr) throws InvalidEquationException {
        // Check if the subtree consists of a single number or contains subexpressions
        if (!subExpr.has("children")) {
            String rootVal = (String) subExpr.get("value");
            Number rootExpr = new Number(rootVal);
            return rootExpr;
        } else {
            Expression left = parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(0));
            Expression right = parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(1));
            String operator = (String) subExpr.get("command");
            switch (operator) {
                case "+":
                    return new AdditionOp(left, right);
                case "/":
                    return new DivisionOp(left, right);
                case "*":
                    return new MultiplicationOp(left, right);
                default:
                    throw new InvalidEquationException("Operator not recognized");
            }
        }
    }
}
