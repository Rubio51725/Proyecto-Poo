import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PosicionPlataforma {
    int x, y;
}

public class DoodleJump extends JPanel implements Runnable, KeyListener {

    final int Ancho = 1000;
    final int Alto = 1113;

    boolean enEjecucion;
    Thread hilo;
    BufferedImage vista, fondo, plataforma, doodle;

    PosicionPlataforma[] posicionesPlataformas;
    int x = 400, y = 100, h = 100;
    float dy = 0;
    boolean derecha, izquierda;

    private int puntuacion; // Nueva variable para la puntuación

    public DoodleJump() {
        setPreferredSize(new Dimension(Ancho, Alto));
        addKeyListener(this);
        setFocusable(true);
        puntuacion = 0; // Inicializa la puntuación
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Menu Doogle Jump");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Crear un panel con FlowLayout
            JPanel panel= new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon fondo = new ImageIcon("FondoMenu.png");
                    g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };

            // Crear un botón
            JButton boton = new JButton("Iniciar");
            boton.setPreferredSize(new Dimension(150, 50));
            boton.setContentAreaFilled(false);
            boton.setForeground(Color.BLACK);

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        JFrame ventana = new JFrame("Juego");
                        ventana.setResizable(false);
                        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        ventana.add(new DoodleJump());
                        ventana.pack();
                        ventana.setLocationRelativeTo(null);
                        ventana.setVisible(true);
                    });
                }
            });
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 400, 200));
            panel.add(boton);
            panel.add(boton);
            ventana.getContentPane().add(panel);
            ventana.setSize(800, 400);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);

        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (hilo == null) {
            hilo = new Thread(this);
            enEjecucion = true;
            hilo.start();
        }
    }

    public void start() {
        try {
            vista = new BufferedImage(Ancho, Alto, BufferedImage.TYPE_INT_RGB);

            fondo = ImageIO.read(getClass().getResource("fondo2.jpg"));
            plataforma = ImageIO.read(getClass().getResource("plataforma.png"));
            doodle = ImageIO.read(getClass().getResource("dodle2.png"));

            posicionesPlataformas = new PosicionPlataforma[25];

            for (int i = 0; i < 25; i++) {
                posicionesPlataformas[i] = new PosicionPlataforma();
                posicionesPlataformas[i].x = new Random().nextInt(1000);
                posicionesPlataformas[i].y = new Random().nextInt(1113);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (derecha) {
            x += 3;
        } else if (izquierda) {
            x -= 3;
        }
        dy += 0.2;
        y += dy;

        // Verificar si el doodle ha caído más allá de la pantalla
        if (y > Alto) {
            enEjecucion = false;  // Fin del juego
            System.out.println("Fin del juego. Puntuación: " + puntuacion);
            return;
        }

        for (int i = 0; i < 25; i++) {
            if ((x + 50 > posicionesPlataformas[i].x) &&
                    (x + 20 < posicionesPlataformas[i].x + 68) &&
                    (y + 70 > posicionesPlataformas[i].y) &&
                    (y + 70 < posicionesPlataformas[i].y + 14) &&
                    (dy > 0)) {
                dy = -10;
                puntuacion += 1; // Aumenta la puntuación al saltar sobre una plataforma
            }
        }
    }

    public void draw() {
        Graphics2D g2 = (Graphics2D) vista.getGraphics();
        g2.drawImage(fondo, 0, 0, Ancho, Alto, this);
        g2.drawImage(doodle, x, y, doodle.getWidth(), doodle.getHeight(), this);
        for (int i = 0; i < 25; i++) {
            g2.drawImage(
                    plataforma,
                    posicionesPlataformas[i].x,
                    posicionesPlataformas[i].y,
                    plataforma.getWidth(),
                    plataforma.getHeight(),
                    null);
        }

        // Dibuja la puntuación en la esquina superior derecha
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Puntuación: " + puntuacion, Ancho - 200, 30);

        Graphics g = getGraphics();
        g.drawImage(vista, 0, 0, Ancho, Alto, null);
        g.dispose();
    }

    @Override
    public void run() {
        try {
            requestFocus();
            start();
            while (enEjecucion) {
                update();
                draw();
                Thread.sleep(1000 / 60);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derecha = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            izquierda = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derecha = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            izquierda = false;
        }
    }
}
