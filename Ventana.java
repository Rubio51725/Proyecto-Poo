import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Ventana con Botón");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Crear un panel con un layout nulo (null)
            JPanel panel = new JPanel(null);

            // Crear un botón
            JButton boton = new JButton("Haz clic");

            // Establecer la posición absoluta del botón
            boton.setBounds(50, 0, 100, 10
        ); // x, y, ancho, alto

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(ventana, "¡Has hecho clic en el botón!");
                }
            });

            // Agregar el botón al panel
            panel.add(boton);

            // Agregar el panel a la ventana
            ventana.getContentPane().add(panel);

            // Establecer el tamaño de la ventana
            ventana.setSize(300, 200);

            // Centrar la ventana en la pantalla
            ventana.setLocationRelativeTo(null);

            // Hacer visible la ventana
            ventana.setVisible(true);
        });
    }
}
