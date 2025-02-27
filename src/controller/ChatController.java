package controller;

import model.ChatModel;
import model.DBConnection;
import model.Mensaje;
import view.ChatView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ChatController {
    private ChatView vista;
    private boolean connected;
    private Timer timer;
    private int refreshCounter = 1;
    private static final int REFRESH_TIME = 3000; 

    public ChatController(ChatView vista) {
        this.vista = vista;
        this.connected = false;

        // Carga de usuarios conectados al iniciar la app
        loadUsers();

        // Listener al botón "Connect"
        this.vista.addConnectListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connect();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Listener al botón "Disconnect"
        this.vista.addDisconnectListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    disconnect();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Listener al botón "Send"
        this.vista.addSendListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Inicializar el temporizador para refrescar mensajes y usuarios
        timer = new Timer(REFRESH_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	refreshCounter++;
                if (connected) {
                	System.out.println("Actualización número: " + refreshCounter);
                    loadMessages();
                    loadUsers();
                }
            }
        });
    }

    /**
     * Método para conectar a la base de datos con un nombre de usuario.
     * @throws SQLException Si hay un error en la conexión.
     */
    private void connect() throws SQLException {
        String userName = vista.getUserName();

        if (userName == null || userName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre de usuario.");
            return;
        }

        ChatModel model = new ChatModel();

        // Ejecutar el procedimiento almacenado con el nombre del usuario
        boolean successConnection = model.executeConnection(userName);

        if (successConnection) {
        	timer.start();
            this.connected = true;
            loadUsers();
            loadMessages();
            JOptionPane.showMessageDialog(null, "Conectado como: " + userName);
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
        }
    }

    /**
     * Método para desconectar al usuario.
     * @throws SQLException Si hay un error en la desconexión.
     */
    private void disconnect() throws SQLException {
        ChatModel model = new ChatModel();
        model.disconnect();

        // Limpiar la lista de usuarios conectados en la vista
        DefaultListModel<String> userModel = vista.getModelUsers();
        userModel.clear();
        
        this.connected = false;
        timer.stop(); // Detener el temporizador cuando el usuario se desconecta
    }

    /**
     * Carga los usuarios conectados desde la base de datos y actualiza la vista.
     */
    public void loadUsers() {
        ChatModel model = new ChatModel();
        List<String> users = model.getConnectedUsers();

        DefaultListModel<String> modeloUsuarios = vista.getModelUsers();
        modeloUsuarios.clear();
       
        System.out.println("Usuarios conectados: " + users.size());
        for (String usuario : users) {
            modeloUsuarios.addElement(usuario);
            System.out.println("- " + usuario);
        }
    }

    /**
     * Carga los mensajes desde la base de datos y actualiza la vista.
     */
    public void loadMessages() {
        ChatModel model = new ChatModel();
        List<Mensaje> messageList = model.getMessagesFromDB();

        DefaultListModel<String> msgModel = vista.getModelMessages();

     // Obtener los mensajes ya cargados en la vista
        int existingCount = msgModel.getSize();

        for (Mensaje message : messageList) {
            String msgText = message.toString();

            // Verificar si el mensaje ya está en la lista
            boolean exists = false;
            for (int i = 0; i < existingCount; i++) {
                if (msgModel.getElementAt(i).equals(msgText)) {
                    exists = true;
                    break;
                }
            }

            // Agregar solo si es un mensaje nuevo
            if (!exists) {
                msgModel.addElement(msgText);
            }
        }
    }


    /**
     * Envía un mensaje y lo actualiza en la vista.
     */
    public void sendMessage() {
        String message = vista.getMessage();

        if (message == null || message.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un mensaje.");
            return;
        }

        ChatModel model = new ChatModel();
        boolean isSentMessage = model.sendMessage(message);

        if (isSentMessage) {
            loadMessages();
            vista.clearMessageInput();
        } else {
            JOptionPane.showMessageDialog(null, "Error al enviar el mensaje.");
        }
    }
}
