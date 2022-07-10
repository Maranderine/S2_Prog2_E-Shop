package UserInterface.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.TableCellRenderer;
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

public class MitarbeiterGUI extends JPanel{
    //allgemeine Daten
    GUI gui;
    Vector<Object> shopData;
    Vector<Object> mitarbeiterData;
    Vector<Object> Data;

    //Bestandteile Seitenmenü
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JPanel menu;

    //Kartenlayout
    CardLayout cardLayout;
    JPanel cards;
    JPanel card1;
    JPanel card2;
    JPanel card3;

    //Bestandteile der Shopansicht 
    JPanel shopPanel;
    JTextField searchterm;
    JButton search;
    JButton erstellen;
    ArtikelVwTableModel artikelTableModel;
    JTable shopTable;
    TableCellRenderer tableRenderer;
    JScrollPane artikelScroll;

    JPanel employPanel;
    WKTableModel employTableModel;
    JTable employTabel;
    JScrollPane employScroll;

    public MitarbeiterGUI(GUI gui, Vector<Object> data){

        this.shopData = data;
        this.gui = gui;

        b1 = new JButton("Artikel");
        b2 = new JButton("Mitarbeiter");
        b3 = new JButton("Events");
        b4 = new JButton("Logout");
        menu = new JPanel();

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        card1 = new JPanel();
        card2 = new JPanel();
        card3 = new JPanel();

        shopPanel = new JPanel(new BoxLayout(menu, BoxLayout.X_AXIS));
        searchterm = new JTextField();
        search = new JButton("suchen");
        erstellen = new JButton("+neuer Artikel");
        artikelTableModel = new ArtikelVwTableModel(data);
        shopTable = new JTable(artikelTableModel);
        artikelScroll = new JScrollPane(shopTable);

        employPanel = new JPanel(new BoxLayout(menu, BoxLayout.X_AXIS));
        ArtikelVwTableModel mitarbeiterTable = new ArtikelVwTableModel(data);
        employTabel = new JTable(employTableModel);
        employScroll = new JScrollPane(employTabel);

        initializeLayout();
        initializeAction();
        setVisible(true);
    }

    private void initializeLayout(){

        setPreferredSize(new Dimension(600,400));
        setLayout(new BorderLayout());

        //Seitenmenu
        menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));
        menu.setBackground(new Color(71,39,79));

        //Buttons im Seitenmenü
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

        //CardLayout --> Fenster auf dem Panel Warenkorb oder Panel SHop angezeigt wird
        cards.setPreferredSize(new Dimension(530,400));
        card1.setBorder(BorderFactory.createTitledBorder("Artikel verwalten"));
        card2.setBorder(BorderFactory.createTitledBorder("Mitarbeiter Übersicht"));
        card3.setBorder(BorderFactory.createTitledBorder("Event Log"));
        
        shopPanel.setPreferredSize(new Dimension(500,140));
        shopPanel.setLayout(new FlowLayout());

        searchterm.setPreferredSize(new DimensionUIResource(250, 30));

        shopTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("Artikel bearbeiten"));;
        shopTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        shopTable.getColumn("Nr").setMaxWidth(20);
        shopTable.getColumn("Artikel").setMinWidth(200);
        shopTable.getColumn("Preis").setMinWidth(90);
        shopTable.getColumn("Bestand").setMinWidth(90);
        shopTable.getColumn("").setMaxWidth(50);
        
        employPanel.setPreferredSize(new Dimension(500,140));
        employPanel.setLayout(new FlowLayout());

        //add Components
        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        menu.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
    
        menu.add(b1);
        menu.add(b2);
        menu.add(b3);
    
        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5,20);
        Dimension fillerPreferredSize = new Dimension(5,Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5,Short.MAX_VALUE);
        menu.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));
    
        menu.add(b4);
    
        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        menu.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        
        //card2 Layou
        borderMinSize = new Dimension(500, 40);
        borderPrefSize = new Dimension(500, 40);
        borderMaxSize = new Dimension(500, 40);
        shopPanel.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        shopPanel.add(searchterm);
        shopPanel.add(search);
        shopPanel.add(erstellen);


        card1.add(shopPanel, BorderLayout.NORTH);
        card1.add(artikelScroll, BorderLayout.CENTER);

        employPanel.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        employPanel.add(new JButton("kaufen"));

        card2.add(employPanel, BorderLayout.CENTER);
        card2.add(employScroll, BorderLayout.NORTH);

        cards.add(card1, "RedCard");
        cards.add(card2, "BlueCard");
        cards.add(card3, "GreenCard");

        add(menu, BorderLayout.WEST);
        add(cards, BorderLayout.CENTER);
    }

    private void initializeAction(){
        //Actions die nur Änderungen innerhalb des Panels (der KLasse) machen
        //event wenn in den Warenkorb geklickt wird
        shopTable.addMouseListener(new MouseInputAdapter() {
        @Override
            public void mouseReleased(MouseEvent e) {
                int selectedColumn = shopTable.getSelectedColumn();
                if (selectedColumn == 4){
                    JButton button = new JButton();
                    //Zwecksmäßiger Button um ActionEvent zu feuern
                    button.setActionCommand("mitarbeiter_bearbeiten");
                    button.addActionListener(gui);
                    button.doClick();
                }
            }
        });
        
        //wechseln des Fensters Shop <-> Warenkorb
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

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                cardLayout.show(cards, "GreenCard");
            }
            });

        b4.setActionCommand("mitarbiter_logout");
        b4.addActionListener(this.gui);
    }

        //eingeben in suchzeile 

        //Actions die auf Methoden im Eshop zurückgreifen
        //logout Button
        

    public void updateArtikel(Vector data){
        ArtikelTableModel tablemodel = (ArtikelTableModel) shopTable.getModel();
        tablemodel.setArtikel(data);
    }
}