package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ChatController implements Initializable {

    private static final String defautName = "Matheus";

    @FXML
    TextField messageBox;

    @FXML
    Button sendMessage;

    @FXML
    ListView<String> roomClients;

    @FXML
    ListView<String> messages;

    private ChatContract chatService;

    private ObservableList<String> roomData;
    private ObservableList<String> messagesData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            chatService = (ChatContract) Naming.lookup("rmi://localhost:1099/ChatService");
            chatService.join(defautName, null);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            chatService = null;
            e.printStackTrace();
        }
        roomClients.setEditable(false);
        roomData = roomClients.getItems();
        messagesData = messages.getItems();
        messageBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                sendMessage(messageBox.getText());
            }
        });


        new Timer().schedule(
                new TimerTask() {

                    @Override
                    public void run() {

                        try {

                            if (chatService == null)
                                return;

                            List<User> clients = chatService.getRoom();
                            List<String> messages = chatService.receive();

                            Platform.runLater(() -> {
                                roomData.clear();
                                for (User user : clients)
                                    roomData.add(user.getName());

                                messagesData.clear();
                                messagesData.addAll(messages);
                            });

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 1000);


    }

    public void onSendMessage(MouseEvent mouseEvent) {

        String message = messageBox.getText();
        sendMessage(message);

    }

    private void sendMessage(String message) {
        if (chatService != null) {

            try {
                chatService.send(defautName, message);
                messageBox.setText(null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }
}
