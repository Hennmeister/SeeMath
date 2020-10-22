package logic;

import java.io.UnsupportedEncodingException;

import GUI.App;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.listener.DataListener;
import org.apache.log4j.BasicConfigurator;

public class SeeMathServer {

    public static void main(String[] args) throws InterruptedException {
        BasicConfigurator.configure();
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3333);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);


        final SocketIOServer server = new SocketIOServer(config);

        server.addEventListener("result", String.class, (client, data, ackRequest) -> {
            System.out.println("Data" + data);
            App.main(new String[]{data});
        });

        server.addEventListener("expressions", String.class, (client, data, ackRequest) -> {
            System.out.println("EXP" + data);
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}