package model;

/**
 * Se utiliza para dar un formato a los mensajes.
 */
public class Mensaje {
    private String usuario;
    private String mensaje;
    private String timestamp;

    public Mensaje(String usuario, String mensaje, String timestamp) {
        this.usuario = usuario;
        this.mensaje = mensaje;
        this.timestamp = timestamp;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + usuario + ": " + mensaje;
    }
}
