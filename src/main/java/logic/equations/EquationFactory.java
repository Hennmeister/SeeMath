package logic.equations;

import org.json.JSONObject;
import org.json.JSONString;

public class EquationFactory {

    public void getEquation(String expr_data) {
        // parse expr_data string into Java JSON object
        JSONObject jsonObj = new JSONObject(expr_data);
        // retrieve equation meta data
        int equationBlockId = (int) jsonObj.get("mathid");
        int problemId = (int) jsonObj.get("problem");

    }
}
