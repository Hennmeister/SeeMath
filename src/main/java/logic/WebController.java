package logic;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.Packet;
import logic.equations.Equation;
import logic.equations.EquationFactory;
import logic.equations.EquationManager;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;

import java.util.Base64;

public class WebController {
    private SocketIOClient client;

    /**
     * starts the WebSocket server to listen for events from the Hypatia editor
     * @throws InterruptedException
     */
    public void startServer( EquationManager eqnManager ) throws InterruptedException {
        BasicConfigurator.configure();
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(3333);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);

        SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener((client) -> {
            this.client = client;
        });

        EquationFactory eqnFactory = new EquationFactory();

        server.addEventListener("result", String.class, (client, data, ackRequest) -> {
            // System.out.println("Result:" + data);
            JSONObject JSONValueData = new JSONObject(data).getJSONObject("value");
            // Parse id and tell equation manager to update equation
            if(JSONValueData.has("id")) {
                String fullId = JSONValueData.getString("id");
                String id = fullId.substring(0, fullId.indexOf('$'));
                // Check if equation is incorrect and update correctness accordingly
                eqnManager.updateEquationCheckMathResults(id, !JSONValueData.getString("type").equals("math-error"));
            }
        });

        server.addEventListener("expressions", String.class, (client, data, ackRequest) -> {
        //    System.out.println("Expression" + data);
            Equation eqn = eqnFactory.getEquation(data);
            eqnManager.add(eqn);
        });

        server.start();
    }

    public void sendVisualHint(Equation eqn, String base64Img) {
        JSONObject packet = new JSONObject();
        packet.put("mathid", eqn.getMathBlockId());
        packet.put("version", eqn.getVersion());
        packet.put("id", eqn.getLeftTree().findLeftMostLeaf().getId() + "$" + eqn.getRightTree().findRightMostLeaf().getId());
        packet.put("color", "#FFFF0037");
        packet.put("type", "math-custom");
        packet.put("hint", "&<img style='max-width: 550px; max-height: 700px; padding: 2px; border: 1px solid black' src=\"data:image/png;base64, " + base64Img + "\" alt=\"Red dot\" />");
        client.sendEvent("add_box", packet.toString());
    }
}