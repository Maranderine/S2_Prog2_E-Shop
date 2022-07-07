package UserInterface.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import Domain.Artikel.Artikel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.MouseEvent;
import java.awt.*;

public class KundeGUI extends JPanel{
  GUI gui;
  Vector<Artikel> data;

  JButton b1;
  JButton b2;
  JButton b3;
  CardLayout cardLayout;
  JPanel menu;
  JPanel cards;
  JPanel card1;
  JPanel card2;
  JPanel p;



  public KundeGUI(GUI gui, Vector<Artikel> data){
    initializeLayout();
    initializeAction();
    setVisible(true);
    this.data = data;
    this.gui = gui;
  }

  public void initializeLayout(){

    setPreferredSize(new Dimension(800,600));
    setLayout(new BorderLayout());

    //menuBar zum ANischt wechseln Shop/Warenkorb und logout
    menu = new JPanel(new GridBagLayout());
    menu.setPreferredSize(new Dimension(100,600));
    menu.setBackground(new Color(71,39,79));


    //button initialisierung
    b1 = new JButton("Artikel");
    b2 = new JButton("Warenkorb");
    b3 = new JButton("Logout");
    b3.setActionCommand("kunde");

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

    //Panels
    cardLayout = new CardLayout();
    cards = new JPanel(cardLayout);
    cards.setPreferredSize(new Dimension(700,600));
    card1 = new JPanel(new BorderLayout());
    card2 = new JPanel();

    // card 1 Layout  
    p = new JPanel();
    p.setPreferredSize(new Dimension(700,300));
    //Table Model und Jtable basierend darauf in scrollbarem Fenster
    ArtikelTableModel artikelTableModel = new ArtikelTableModel(data);
    JTable table = new JTable(artikelTableModel);
    JScrollPane sp = new JScrollPane(table);
    card1.add(p, BorderLayout.NORTH);
    card1.add(sp, BorderLayout.CENTER);
        
    //card2 Layout
    card2.setBackground(Color.blue);
    JLabel test = new JLabel(b3.getActionCommand());
    card2.add(test);


    cards.add(card1, "RedCard");
    cards.add(card2, "BlueCard");

    add(menu, BorderLayout.WEST);
    add(cards, BorderLayout.CENTER);
  }

  public void initializeAction(){

    b3.addActionListener(gui);
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
  }

  public void setCard(){
    cardLayout.show(cards, "BlueCard");
  }
}

