/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.thread;

import hr.erika.javafx.app.userScreen.UserScreenController;
import hr.erika.javafx.utils.JsonUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author ER
 */
public class ClientThread extends Thread {
    
    private static final String PROPERTIES_FILE = "config.properties";
    private static final String CLIENT_PORT = "CLIENT_PORT";
    private static final Properties PROPERTIES = new Properties();

    private final UserScreenController controller;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    
    static {
        try {
            PROPERTIES.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ClientThread(UserScreenController controller) throws IOException {
        this.controller = controller;
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = new Socket(host.getHostName(), Integer.valueOf(PROPERTIES.getProperty(CLIENT_PORT)));
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void send(hr.erika.javafx.state.State state) {
        try {
            oos.writeObject(JsonUtils.toJsonString(state));
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object message = ois.readObject();

                if (message.getClass() == Integer.class) {
                    int numberOfPlayers = (int) message;
                    controller.setPawns(numberOfPlayers);

                    Platform.runLater(
                            () -> {
                                controller.showInformation();
                            }
                    );
                    continue;
                }

                if (message.getClass() == String.class) {
                    String stringMessage = (String) message;

                    if ("YELLOW".equals(stringMessage) || "GREEN".equals(stringMessage)
                            || "RED".equals(stringMessage) || "BLUE".equals(stringMessage)) {
                        System.out.println(stringMessage);
                        controller.setThisPlayer(stringMessage);
                    } else {
                        hr.erika.javafx.state.State state = JsonUtils.toState(stringMessage);
                        Platform.runLater(
                                () -> {
                                    controller.updateState(state);
                                }
                        );
                    }

                    continue;
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
