/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacio.jabber;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import presentacio.GuiMenu;

/**
 *
 * @author wida45787385
 */
public class GuiMenuJabber {

    private JFrame jFrame;
    private JPanel jpFons;
    private JLabel jlTitol;
    private JLabel jlImatgeFons;
    private JButton jbCrearCompte;
    private JButton jbTancarSessio;
    private JButton jbIniciarSessio;
    private JButton jbTornar;
    private GuiMenu menu;
    private GuiLoginJabber iniciar;
    private GuiCrearCompteJabber crear;
    private JLabel jlBarra;

    public GuiMenuJabber() throws InterruptedException {
        iniciarComponents();
    }

    public void iniciarComponents() throws InterruptedException {
        jFrame = new JFrame();
        jFrame.setSize(new Dimension(338, 629));
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Usuaris");
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

        final Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

        jbIniciarSessio = new JButton("Iniciar sessió a Jabber");
        jbIniciarSessio.setMnemonic('I');
        jbIniciarSessio.setCursor(cursor);
        jbIniciarSessio.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        jbIniciarSessio.setBorder(null);
        jbIniciarSessio.setLayout(null);
        jbIniciarSessio.setBounds(40, 135, 260, 40);
        jbIniciarSessio.setIconTextGap(-260);
        jbIniciarSessio.setIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto1.gif")));
        jbIniciarSessio.setRolloverIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto2.gif")));
        jbIniciarSessio.setHorizontalTextPosition(SwingConstants.CENTER);
        jpFons.add(jbIniciarSessio);

        jbCrearCompte = new JButton("Crea el teu compte Jabber");
        jbCrearCompte.setMnemonic('C');
        jbCrearCompte.setCursor(cursor);
        jbCrearCompte.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        jbCrearCompte.setBorder(null);
        jbCrearCompte.setLayout(null);
        jbCrearCompte.setBounds(40, 225, 260, 40);
        jbCrearCompte.setIconTextGap(-260);
        jbCrearCompte.setIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto1.gif")));
        jbCrearCompte.setRolloverIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto2.gif")));
        jbCrearCompte.setHorizontalTextPosition(SwingConstants.CENTER);
        jpFons.add(jbCrearCompte);

        jbTancarSessio = new JButton("Tanca la sessió");
        jbTancarSessio.setMnemonic('T');
        jbTancarSessio.setCursor(cursor);
        jbTancarSessio.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        jbTancarSessio.setBorder(null);
        jbTancarSessio.setLayout(null);
        jbTancarSessio.setBounds(40, 315, 260, 40);
        jbTancarSessio.setIconTextGap(-260);
        jbTancarSessio.setIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto1.gif")));
        jbTancarSessio.setRolloverIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto2.gif")));
        jbTancarSessio.setHorizontalTextPosition(SwingConstants.CENTER);
        jpFons.add(jbTancarSessio);

        jbTornar = new JButton("Tornar enrere");
        jbTornar.setMnemonic('r');
        jbTornar.setCursor(cursor);
        jbTornar.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        jbTornar.setBorder(null);
        jbTornar.setLayout(null);
        jbTornar.setBounds(40, 405, 260, 40);
        jbTornar.setIconTextGap(-260);
        jbTornar.setIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto1.gif")));
        jbTornar.setRolloverIcon(new ImageIcon(getClass().getResource("/serveis/imatges/boto2.gif")));
        jbTornar.setHorizontalTextPosition(SwingConstants.CENTER);
        jpFons.add(jbTornar);

        jlBarra = new JLabel("Menú Principal/Jabber");
        jlBarra.setForeground(Color.white);
        jlBarra.setBounds(2, 578, 340, 30);
        jpFons.add(jlBarra);

        jpFons.add(jlImatgeFons);

        jFrame.setVisible(true);

        jbIniciarSessio.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                try {
                    jFrame.setVisible(false);
                    iniciar = new GuiLoginJabber();
                    iniciar.getjFrame().setLocation(jFrame.getLocation());
                    iniciar.getjFrame().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GuiMenuJabber.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jbCrearCompte.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                try {
                    jFrame.setVisible(false);
                    crear = new GuiCrearCompteJabber();
                    crear.getjFrame().setLocation(jFrame.getLocation());
                    crear.getjFrame().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GuiMenuJabber.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jbTornar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                try {
                    jFrame.setVisible(false);
                    menu = new GuiMenu();
                    menu.getjFrame().setLocation(jFrame.getLocation());
                    menu.getjFrame().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GuiMenuJabber.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public JFrame getjFrame() {
        return jFrame;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new GuiMenuJabber();
                } catch (InterruptedException ex) {
                    Logger.getLogger(GuiMenuJabber.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
