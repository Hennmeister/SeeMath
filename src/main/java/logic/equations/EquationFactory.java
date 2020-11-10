package logic.equations;

import logic.InvalidEquationException;
import logic.equations.expression_tree.Expression;
import logic.equations.expression_tree.ExpressionTree;
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
        // parse string into Java JSON object
        JSONObject jsonObj = new JSONObject(JSON_expr_data);
        // retrieve equation meta data
        String equationBlockId = (String) jsonObj.get("mathid");
        Integer problemId = (Integer) jsonObj.get("problem");

        //get value array
        JSONArray eqnArray = (JSONArray) ((JSONArray) jsonObj.get("value")).get(0);

        //get expression tree root
        Expression<String> root = null;
        try {
                root = new Expression<String> ((String) ((JSONObject) eqnArray.get(1)).get("command"));
        } catch (Exception e) {
            System.out.println("Error Parsing equation array" + e.getMessage());
        }

        ExpressionTree left = parseJSONExprTree((JSONObject) eqnArray.get(0));
        ExpressionTree right = parseJSONExprTree((JSONObject) eqnArray.get(2));
        ExpressionTree tree = new ExpressionTree(left, root, right);

        // Create the equation
        // TEMP: equations are always false and of string type addition
        Equation eqn = new Equation(equationBlockId, problemId, tree, false, "Addition" );
        return eqn;
    }

    /**
     * Create an expresion tree by parsing JSON object
     * @param subExpr The expression tree in JSON object format
     * @return the Expression Tree parsed from input
     */
    public ExpressionTree parseJSONExprTree(JSONObject subExpr) {
        // Check if the subtree consists of a single number or contains subexpressions
        if (!subExpr.has("children")) {
            Integer rootVal = (Integer) subExpr.get("value");
            Expression<Integer> root = new Expression<Integer>(rootVal);
            return new ExpressionTree(root);
        } else {
            Expression<String> root = new Expression<String>((String) subExpr.get("command"));
            ExpressionTree left = parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(0));
            ExpressionTree right = parseJSONExprTree((JSONObject) ((JSONArray) subExpr.get("children")).get(1));
            return new ExpressionTree(left, root, right);
        }
    }
}
