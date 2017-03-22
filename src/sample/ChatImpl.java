/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 886345
 */
public class ChatImpl extends UnicastRemoteObject implements ChatContract {

    private int maxClients;
    private List<User> users;
    private List<String> messages;

    public ChatImpl(int maxClients) throws RemoteException {
        this.maxClients = maxClients;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    @Override
    public int join(String name, String ip) throws RemoteException {
        if (users.size() >= maxClients) {
            throw new RemoteException();
        }

        User user = new User(name, ip);
        users.add(user);

        return users.size();
    }

    @Override
    public void leave(String ip) throws RemoteException {
        for (User user : users) {
            if (user.getIp().equals(ip)) {
                users.remove(user);
            }
        }

    }

    @Override
    public void send(String name, String message) throws RemoteException {
        messages.add(String.format("%s diz: %s", name, message));
    }

    @Override
    public List<String> receive() throws RemoteException {
        return messages;
    }

    @Override
    public List<User> getRoom() throws RemoteException {
        return users;
    }

}
