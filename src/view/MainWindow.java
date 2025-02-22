package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Botones
		JButton LoadButton = new JButton("Connect");
		LoadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		LoadButton.setBounds(26, 12, 105, 27);
		contentPane.add(LoadButton);
		
		JButton SendButton = new JButton("Send");
		SendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton disconnectButton = new JButton("disconnect");
		disconnectButton.setBounds(317, 12, 105, 27);
		contentPane.add(disconnectButton);
		
		SendButton.setBounds(317, 511, 105, 27);
		contentPane.add(SendButton);
		
		// Paneles e input
		JPanel chatBox = new JPanel();
		chatBox.setBounds(26, 50, 241, 399);
		contentPane.add(chatBox);
		chatBox.setBorder(new LineBorder(Color.gray, 1));
		
		JPanel userList = new JPanel();
		userList.setBounds(289, 50, 133, 399);
		contentPane.add(userList);
		userList.setBorder(new LineBorder(Color.gray, 1));
		
		JTextField textField;
		
		JPanel msgInput = new JPanel();
		msgInput.setBounds(26, 464, 396, 35);
		contentPane.add(msgInput);
		msgInput.setLayout(null);

		
		// Input Text
		textField = new JTextField();
		textField.setBounds(12, 5, 372, 25);
		msgInput.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(36, 512, 254, 25);
		contentPane.add(textField_1);
		
		
		
	}
}
