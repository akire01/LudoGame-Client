/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.chat;

import hr.erika.java.chat.ServerChatInterface;
import hr.erika.javafx.app.userScreen.UserScreenController;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 *
 * @author ER
 */
public class ChatClient {

    private static final String SERVER_NAME = "Server";
    private static final String RMI_CLIENT = "client";
    private static final String RMI_SERVER = "server";
    private static final String RMI_URL = "rmi://localhost:1099";
    private static final int RMI_PORT = 1099;
    private static final int RANDOM_PORT_HINT = 0;

    private ServerChatInterface chatService;
    private ServerChatInterface server;

    private UserScreenController controller;

    public ChatClient(UserScreenController controller) {
        this.controller = controller;
        publishClient();
        fetchSetver();
    }

    private void publishClient() {
        chatService = new ServerChatInterface() {

            @Override
            public void send(String message) throws RemoteException {
                controller.postMessage(message);
            }
        };
        try {
            Registry registry = LocateRegistry.getRegistry(RMI_PORT);
            ServerChatInterface stub = (ServerChatInterface) UnicastRemoteObject.exportObject(chatService, RANDOM_PORT_HINT);
            registry.rebind(RMI_CLIENT, stub);

        } catch (RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fetchSetver() {
        final Hashtable properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, com.sun.jndi.rmi.registry.RegistryContextFactory.class.getName());
        properties.put(Context.PROVIDER_URL, RMI_URL);

        try (InitialDirContextCloseable ctx = new InitialDirContextCloseable(properties)) {
            server = (ServerChatInterface) ctx.lookup(RMI_SERVER);
        } catch (NamingException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String message) {
        try {
            server.send(message);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
