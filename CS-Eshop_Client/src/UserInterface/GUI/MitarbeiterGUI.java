package UserInterface.GUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.TableCellRenderer;

import Domain.BenutzerObjekte.Benutzer;
import UserInterface.GUI.models.ArtikelTableModel;
import UserInterface.GUI.models.ArtikelVwTableModel;
import UserInterface.GUI.models.MitarbeiterTableModel;
import UserInterface.GUI.models.EventTableModel;
import UserInterface.GUI.models.WKTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.MouseEvent;
import java.awt.*;

public class MitarbeiterGUI extends JPanel{
    //allgemeine Daten
    GUI gui;
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
    JButton addButton;
    MitarbeiterTableModel employTableModel;
    JTable employTabel;
    JScrollPane employScroll;

    JPanel eventPanel;
    JTextField searchEvent;
    JButton searchButton;
    JComboBox<String> filter;
    EventTableModel eventTableModel;
    JTable eventTable;
    JScrollPane eventScroll;
//TODO: Events auch nach string suchbar machen 

    public MitarbeiterGUI(GUI gui, ArtikelVwTableModel artikelTableModel, MitarbeiterTableModel mitarbeiterTableModel, EventTableModel eventTableModel){
        String[] filterList = {"Filter1", "Filter2"};
        this.gui = gui;

        //navigation
        b1 = new JButton("Artikel");
        b2 = new JButton("Mitarbeiter");
        b3 = new JButton("Events");
        b4 = new JButton("Logout");
        menu = new JPanel();

        //cardlayout
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        card1 = new JPanel();
        card2 = new JPanel(new BorderLayout());
        card3 = new JPanel();

        //Artikel Tabelle im Mitarbeiter Fenster - Komponenten
        shopPanel = new JPanel(new BoxLayout(menu, BoxLayout.X_AXIS));
        searchterm = new JTextField();
        search = new JButton("suchen");
        erstellen = new JButton("+neuer Artikel");
        this.artikelTableModel = artikelTableModel;
        shopTable = new JTable(artikelTableModel);
        artikelScroll = new JScrollPane(shopTable);

        //Mitarbeiter Tabelle - Komponenten
        employPanel = new JPanel(new BoxLayout(menu, BoxLayout.X_AXIS));
        addButton = new JButton("Mitarbeiter hinzufügen");
        employTabel = new JTable(mitarbeiterTableModel);
        employScroll = new JScrollPane(employTabel);

        eventPanel = new JPanel(new BoxLayout(menu, BoxLayout.X_AXIS));
        searchEvent = new JTextField();
        searchButton = new JButton("suchen");
        filter = new JComboBox<String>(filterList);
        this.eventTableModel = eventTableModel;
        eventTable = new JTable(eventTableModel);
        eventScroll = new JScrollPane(eventTable);


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

        b4.setOpaque(false);
        b4.setContentAreaFilled(false);
        b4.setBorderPainted(false);
        b4.setForeground(Color.white);

        //CardLayout --> Fenster auf dem Panel Warenkorb oder Panel SHop angezeigt wird
        cards.setPreferredSize(new Dimension(530,400));
        card1.setBorder(BorderFactory.createTitledBorder("Artikel verwalten"));
        card2.setBorder(BorderFactory.createTitledBorder("Mitarbeiter Übersicht"));
        card3.setBorder(BorderFactory.createTitledBorder("Event Log"));
        
        //Artikel Tabellen Fenster
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
        
        //Mitarbeiter Tabellen Fenster
        employTabel.getColumn("Nr").setMaxWidth(40);
        employTabel.getColumn("Name").setMaxWidth(200);
        employScroll.setPreferredSize(new Dimension(250, 400));
        employPanel.setPreferredSize(new Dimension(250,400));
        employPanel.setLayout(new FlowLayout());

        //add Components

        eventPanel.setPreferredSize(new Dimension(500,140));
        eventPanel.setLayout(new FlowLayout());

        searchEvent.setPreferredSize(new DimensionUIResource(250, 30));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        menu.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
    
        menu.add(b1);
        menu.add(b2);
        menu.add(b3);
    
        // Abstandhalter ("Filler") zwischen Logout button und anderen Buttons
        Dimension fillerMinSize = new Dimension(5,20);
        Dimension fillerPreferredSize = new Dimension(5,Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5,Short.MAX_VALUE);
        menu.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));
    
        menu.add(b4);
    
        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        menu.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        
        //oberer Teil der Artikelansicht 
        //Filler zwischen oberem Rand und suchleiste
        borderMinSize = new Dimension(500, 40);
        borderPrefSize = new Dimension(500, 40);
        borderMaxSize = new Dimension(500, 40);
        shopPanel.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        shopPanel.add(searchterm);
        shopPanel.add(search);
        shopPanel.add(erstellen);

        //ersten Fenster zusammenfügen
        card1.add(shopPanel, BorderLayout.NORTH);
        card1.add(artikelScroll, BorderLayout.CENTER);


        employPanel.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        employPanel.add(addButton);

        card2.add(employPanel, BorderLayout.WEST);
        card2.add(employScroll, BorderLayout.CENTER);

        eventPanel.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        eventPanel.add(searchEvent);
        eventPanel.add(searchButton);
        //eventPanel.add(filter);

        card3.add(eventPanel, BorderLayout.NORTH);
        card3.add(eventScroll, BorderLayout.CENTER);
        

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
                    button.setActionCommand("mitarbeiter_Artikelbearbeiten");
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
            gui.resetEventLog();
            }
            });

        b4.setActionCommand("mitarbeiter_logout");
        b4.addActionListener(this.gui);

        search.setActionCommand("mitarbeiter_Artikelsuchen");
        search.addActionListener(gui);

        erstellen.setActionCommand("mitarbeiter_Artikelerstellen");
        erstellen.addActionListener(gui);

        addButton.setActionCommand("mitarbeiter_mitarbeiterHinzufügen");
        addButton.addActionListener(gui);

        searchButton.setActionCommand("mitarbeiter_EreignisSuchen");
        searchButton.addActionListener(gui);
    }

        //eingeben in suchzeile 

        //Actions die auf Methoden im Eshop zurückgreifen
        //logout Button
        

    public void updateArtikel(Vector data){
        ArtikelVwTableModel tablemodel = (ArtikelVwTableModel) shopTable.getModel();
        tablemodel.setArtikel(data);
    }

    public void updateMitarbeiter(Vector<Benutzer> data){
        MitarbeiterTableModel tablemodel = (MitarbeiterTableModel )employTabel.getModel();
        tablemodel.setMitarbeiter(data);
    }

    public Integer getSelectedRow(){
        return shopTable.getSelectedRow();
    }

    public String getSearchterm(){
        return searchterm.getText();
    }

    public String getSearchtermEvent(){
        return (searchEvent.getText().equals("nach Nummer suchen"))? "" : searchEvent.getText();
    }
}