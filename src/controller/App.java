package controller;

import view.ChatView;

import java.awt.EventQueue;

public class App {
    public static void main(String[] args) {

    	// Muestra la ventana del chat    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatView vista = new ChatView();
                    ChatController controlador = new ChatController(vista);
                    vista.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
