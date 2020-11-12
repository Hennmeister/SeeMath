package logic;

import gui.App;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import logic.equations.EquationFactory;
import org.apache.log4j.BasicConfigurator;

public class SeeMathServer {

    public static void main(String[] args) throws InterruptedException {
        BasicConfigurator.configure();
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(3333);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);

        final SocketIOServer server = new SocketIOServer(config);

        EquationFactory eF = new EquationFactory();

        server.addEventListener("result", String.class, (client, data, ackRequest) -> {

        });

        server.addEventListener("expressions", String.class, (client, data, ackRequest) -> {
            System.out.println("Received Expression");
            eF.getEquation(data);
        });

        server.start();
    }
}