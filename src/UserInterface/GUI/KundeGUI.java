package UserInterface.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public class KundeGUI extends JPanel{
    GUI gui;

    public KundeGUI(GUI gui){
        initializeLayout();
        initializeAction();
        setVisible(true);
    }

    public void initializeLayout(){

        setPreferredSize(new Dimension(800,600));
        setLayout(new BorderLayout());

        JPanel menu = new JPanel(new GridBagLayout());
        menu.setPreferredSize(new Dimension(100,600));
        menu.setBackground(new Color(71,39,79));

        JButton b1 = new JButton("Artikel");
        JButton b2 = new JButton("Mitarbeiter");
        JButton b3 = new JButton("Event-log");

        b1.setOpaque(false);
        b1.setContentAreaFilled(false);
        b1.setBorderPainted(false);
        b1.setForeground(Color.white);

        b2.setOpaque(false);
        b2.setContentAreaFilled(false);
        b2.setBorderPainted(false);
        b2.setForeground(Color.white);

        b3.setOpaque(false);
        b3.setContentAreaFilled(false);
        b3.setBorderPainted(false);
        b3.setForeground(Color.white);

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        menu.add(b1,c);
        c.gridy = 1;
        menu.add(b2,c);
        c.gridy = 2;
        menu.add(b3,c);

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        cards.setPreferredSize(new Dimension(700,600));

        JPanel card1 = new JPanel();
        card1.setBackground(Color.red);
 
        JPanel card2 = new JPanel();
        card2.setBackground(Color.blue);
        cards.add(card1, "RedCard");
        cards.add(card2, "BlueCard");

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
              cardLayout.show(cards, "RedCard");
            }
          });
       
          b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
              cardLayout.show(cards, "BlueCard");
            }
          });

        add(menu, BorderLayout.WEST);
        add(cards, BorderLayout.CENTER);
        


    }

    public void initializeAction(){

    }
    
}
