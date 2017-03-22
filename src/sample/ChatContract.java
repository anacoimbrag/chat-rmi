/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author 886345
 */
public interface ChatContract extends Remote {
    List<User> getRoom() throws RemoteException;

    int join(String name, String ip) throws RemoteException;

    void leave(String ip) throws RemoteException;

    void send(String name, String message) throws RemoteException;

    List<String> receive() throws RemoteException;
}
