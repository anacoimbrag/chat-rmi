/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * @author 886345
 */
public class ChatServer  {

    public ChatServer() {
        try {
            ChatContract chat = new ChatImpl(2);
            Naming.rebind("rmi://localhost:1099/ChatService", chat);

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ChatServer();
    }
}
