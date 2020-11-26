package logic;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import logic.equations.Equation;
import logic.equations.EquationFactory;
import logic.equations.EquationManager;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;

public class WebController {

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

        final SocketIOServer server = new SocketIOServer(config);

        EquationFactory eqnFactory = new EquationFactory();

        server.addEventListener("result", String.class, (client, data, ackRequest) -> {
            // System.out.println("Result:" + data);
            JSONObject JSONValueData = new JSONObject(data).getJSONObject("value");
            // Parse id and tell equation manager to update equation
            String fullId = JSONValueData.getString("id");
            String id = fullId.substring(0, fullId.indexOf('$'));

            // Check if equation is incorrect and update correctness accordingly
            eqnManager.updateEquationCorrectness(id, !JSONValueData.getString("type").equals("math-error"));
        });

        server.addEventListener("expressions", String.class, (client, data, ackRequest) -> {
            // System.out.println("Expression" + data);
            Equation eqn = eqnFactory.getEquation(data);
            eqnManager.add(eqn);
        });

        server.start();
    }
}