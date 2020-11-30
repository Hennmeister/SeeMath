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
    private SocketIOServer serverInstance;
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

        serverInstance = new SocketIOServer(config);
        serverInstance.addConnectListener((client) -> {
            this.client = client;
        });

        EquationFactory eqnFactory = new EquationFactory();

        serverInstance.addEventListener("result", String.class, (client, data, ackRequest) -> {
            // System.out.println("Result:" + data);
            JSONObject JSONValueData = new JSONObject(data).getJSONObject("value");
            // Parse id and tell equation manager to update equation
            String fullId = JSONValueData.getString("id");
            String id = fullId.substring(0, fullId.indexOf('$'));

            // Check if equation is incorrect and update correctness accordingly
            eqnManager.updateEquationCheckMathResults(id, !JSONValueData.getString("type").equals("math-error"));
        });

        serverInstance.addEventListener("expressions", String.class, (client, data, ackRequest) -> {
            // System.out.println("Expression" + data);
            Equation eqn = eqnFactory.getEquation(data);
            eqnManager.add(eqn);
        });

        serverInstance.start();
    }

    public void sendVisualHint(Equation eqn, String base64Img) {
        JSONObject packet = new JSONObject();
        packet.put("mathid", eqn.getMathBlockId());
        packet.put("version", eqn.getVersion());
        packet.put("id", eqn.getLeftTree().findLeftMostLeaf().getId() + "$" + eqn.getRightTree().findRightMostLeaf().getId());
        packet.put("color", "#FFFF0037");
        packet.put("type", "math-custom");
        packet.put("hint", "&<img src=\"data:image/png;base64, " + base64Img + "\" alt=\"Red dot\" />");
        client.sendEvent("add_box", packet.toString());
    }
}