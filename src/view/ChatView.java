package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ChatView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel msgInput;
	private JTextField textFieldMsg;
	private JTextField textFieldName;
	private JButton connectButton;
	private JButton disconnectButton;
	private JButton sendButton;

	private JPanel userList;
	private DefaultListModel<String> modeloUsuarios;
	private JList<String> listaUsuarios;
	
	private JPanel msgList;
	private DefaultListModel<String> modeloMessages;
	private JList<String> listaMessages;

	/**
	 * Constructor: Configura la ventana principal
	 */
	public ChatView() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 550);

    // Panel principal
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setBackground(new Color(30, 40, 90)); 
    setContentPane(contentPane);
    contentPane.setLayout(null);

    // Botón "Connect"
    connectButton = new JButton("Connect");
    connectButton.setBounds(26, 12, 105, 27);
    styleButton(connectButton);
    contentPane.add(connectButton);

    // Botón "Disconnect"
    disconnectButton = new JButton("Disconnect");
    disconnectButton.setBounds(317, 12, 105, 27);
    styleButton(disconnectButton);
    contentPane.add(disconnectButton);

    // Panel de chat
    msgList = new JPanel();
    msgList.setBounds(26, 50, 241, 399);
    msgList.setBorder(new LineBorder(Color.WHITE, 1, true));
    msgList.setBackground(new Color(70, 130, 180));
    contentPane.add(msgList);

    initializeMessageList();

    // Lista de usuarios
    userList = new JPanel();
    userList.setBounds(289, 50, 133, 399);
    userList.setBorder(new LineBorder(Color.WHITE, 1, true));
    userList.setBackground(new Color(100, 149, 237)); 
    contentPane.add(userList);

    initializeUserList();

    // Panel de entrada de mensajes
    msgInput = new JPanel();
    msgInput.setBounds(26, 464, 396, 35);
    msgInput.setLayout(null);
    msgInput.setBackground(new Color(25, 25, 112)); 
    contentPane.add(msgInput);

    // Campo de texto para mensajes
    textFieldMsg = new JTextField();
    textFieldMsg.setBounds(5, 5, 270, 25);
    textFieldMsg.setBackground(Color.WHITE);
    textFieldMsg.setForeground(Color.BLACK);
    textFieldMsg.setFont(new Font("Arial", Font.PLAIN, 14));
    msgInput.add(textFieldMsg);
    textFieldMsg.setColumns(10);

    // Botón "Send"
    sendButton = new JButton("Send");
    sendButton.setBounds(285, 4, 105, 27);
    styleButton(sendButton);
    msgInput.add(sendButton);

    // Campo de texto para nombre de usuario
    textFieldName = new JTextField();
    textFieldName.setBounds(143, 13, 118, 25);
    textFieldName.setBackground(Color.WHITE);
    textFieldName.setForeground(Color.BLACK);
    textFieldName.setFont(new Font("Arial", Font.PLAIN, 14));
    contentPane.add(textFieldName);
    textFieldName.setColumns(10);
}

	/**
	 * Devuelve el nombre de usuario ingresado en el campo de texto
	 */
	public String getUserName() {
		return textFieldName.getText().trim();
	}
	
	/**
	 * Devuelve el mensaje ingresado en el campo de texto
	 */
	public String getMessage() {
		return textFieldMsg.getText().trim();
	}

	/**
	 * Permite al controlador agregar un listener al botón "Connect"
	 */
	public void addConnectListener(ActionListener listener) {
		connectButton.addActionListener(listener);
	}

	/**
	 * Permite al controlador agregar un listener al botón "Disconnect"
	 */
	public void addDisconnectListener(ActionListener listener) {
		disconnectButton.addActionListener(listener);
	}
	
	/**
	 * Permite al controlador agregar un listener al botón "Send"
	 */
	public void addSendListener(ActionListener listener) {
		sendButton.addActionListener(listener);
	}

	/**
	 * Inicializa la lista de usuarios 
	 */
	private void initializeUserList() {
		modeloUsuarios = new DefaultListModel<>();
		listaUsuarios = new JList<>(modeloUsuarios);

		JScrollPane scrollPane = new JScrollPane(listaUsuarios);
		scrollPane.setPreferredSize(new Dimension(130, 395));

		userList.setLayout(new BorderLayout());
		userList.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Inicializa la lista de mensajes 
	 */
	private void initializeMessageList() {
		modeloMessages = new DefaultListModel<>();
		listaMessages = new JList<>(modeloMessages);
		
		JScrollPane scrollPane = new JScrollPane(listaMessages);
		scrollPane.setPreferredSize(new Dimension(200,400));
		
		msgList.setLayout(new BorderLayout());
		msgList.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Devuelve la lista de usuarios
	 */
	public DefaultListModel<String> getModelUsers() {
		return modeloUsuarios;
	}
	
	/**
	 * Devuelve la lista de mensajes
	 */
	public DefaultListModel<String> getModelMessages() {
		return modeloMessages;
	}
	
	public void clearMessageInput() {
		textFieldMsg.setText(""); 
	}

	/**
	 * Método para agregar estilos a los botones
	 * @param button
	 */
	private void styleButton(JButton button) {
	    button.setBackground(new Color(70, 130, 180)); // Azul acero
	    button.setForeground(Color.WHITE);
	    button.setFont(new Font("Arial", Font.BOLD, 14));
	    button.setFocusPainted(false);
	    button.setBorder(BorderFactory.createLineBorder(new Color(30, 40, 90), 2)); // Borde azul oscuro
	    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}


}
