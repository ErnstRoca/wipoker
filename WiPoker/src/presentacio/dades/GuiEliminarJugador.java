/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacio.dades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author wida47716705
 */
public class GuiEliminarJugador {

    private JFrame jFrame;
    private JPanel jpFons;
    private JLabel jlTitol;
    private JLabel jlImatgeFons;
    private JLabel jlBuscador;
    private JTextField jtfAlies;
    private JButton jbBuscar;
    private JButton jbEliminar;
    private JTextArea jlText;

    public GuiEliminarJugador() throws InterruptedException {
        iniciarComponents();
    }

    public void iniciarComponents() throws InterruptedException {
        jFrame = new JFrame();
        jFrame.setSize(new Dimension(338, 629));
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Eliminar jugador");
        jFrame.setLayout(null);
        jFrame.setBackground(Color.WHITE);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);

        jpFons = new JPanel();
        jpFons.setLayout(null);
        jpFons.setOpaque(false);
        jpFons.setBounds(0, 0, 340, 950);
        jFrame.add(jpFons);

        jlTitol = new JLabel();
        jlTitol.setBounds(0, 0, 340, 104);
        jlTitol.setLayout(null);
        jlTitol.setIcon(new ImageIcon(getClass().getResource("/serveis/imatges/WiPokerLogo2.gif")));
        jpFons.add(jlTitol);

        jlImatgeFons = new JLabel();
        jlImatgeFons.setBounds(0, 104, 340, 499);
        jlImatgeFons.setLayout(null);
        jlImatgeFons.setIcon(new ImageIcon(getClass().getResource("/serveis/imatges/Wipokerbackground.jpg")));
        jlImatgeFons.setOpaque(false);

        jlBuscador = new JLabel();
        jlBuscador.setBounds(8, 80, 340, 104);
        jlBuscador.setText("Introduir Alies: ");
        jlBuscador.setForeground(Color.red);
        jlBuscador.setLayout(null);
        jpFons.add(jlBuscador);

        jtfAlies = new JTextField(20);
        jtfAlies.setBounds(107, 120, 120, 24);
        jpFons.add(jtfAlies);

        jbBuscar = new JButton("Buscar");
        jbBuscar.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        jbBuscar.setBorder(null);
        jbBuscar.setLayout(null);
        jbBuscar.setBounds(234, 120, 80, 24);
        jbBuscar.setIconTextGap(-260);
        jbBuscar.setIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto1.gif")));
        jbBuscar.setRolloverIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto2.gif")));
        jbBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
        jpFons.add(jbBuscar);

        jlText = new JTextArea("Linia 1\nLinia 2\nLinia 3", 5, 10);
        jlText.setBounds(15, 173, 300, 300);
        jlText.setText("Mont-ros, és un poble del municipi de la Torre de Cabdella, " +
                "al Pallars Jussà; era el cap de l'antic municipi de Mont-ros abans del 1970. " +
                "És un dels tres pobles de la Coma, juntament amb Paüls de Flamisell i Pobellà.");
        jlText.setLayout(null);
        jlText.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(); // Per posar barres scroll
        jScrollPane.setViewportView(jlText);       // Assignar scrollbar al textarea
        jpFons.add(jlText);


        jbEliminar = new JButton("ELIMINAR");
        jbEliminar.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        jbEliminar.setBorder(null);
        jbEliminar.setLayout(null);
        jbEliminar.setBounds(80, 500, 160, 24);
        jbEliminar.setIconTextGap(-260);
        jbEliminar.setIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto1.gif")));
        jbEliminar.setRolloverIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto2.gif")));
        jbEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
        jpFons.add(jbEliminar);

        jpFons.add(jlImatgeFons);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new GuiEliminarJugador();
                } catch (InterruptedException ex) {
                    Logger.getLogger(GuiDades.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
