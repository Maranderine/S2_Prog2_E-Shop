package UserInterface.GUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.TableCellRenderer;

import UserInterface.GUI.models.ArtikelTableModel;
import UserInterface.GUI.models.WKTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Vector;
import java.awt.event.MouseEvent;
import java.awt.*;

public class KundeGUI extends JPanel{
  //Variablen
  //allgemeine Daten
  GUI gui;

  //Bestandteile Seitenmenü
  JButton b1;
  JButton b2;
  JButton b3;
  JPanel menu;

  //Kartenlayout
  CardLayout cardLayout;
  JPanel cards;
  JPanel card1;
  JPanel card2;

  //Bestandteile der Shopansicht 
  JPanel shopPanel;
  JTextField search;
  JComboBox<String> filter;
  JButton suchButton;
  ArtikelTableModel artikelTableModel;
  JTable shopTable;
  TableCellRenderer tableRenderer;
  JScrollPane artikelScroll;

  JPanel WKPanel;
  JLabel summe;
  JButton clearAll;
  JButton kaufen;
  WKTableModel WKTableModel;
  JTable WKTable;
  JScrollPane korbScroll;

  public KundeGUI(GUI gui, ArtikelTableModel artikelTableModel){

    this.gui = gui;
    String[] filterTypes = {"Kein Filter", "Preis aufsteigend", "Preis absteigend"};

    b1 = new JButton("Artikel");
    b2 = new JButton("Warenkorb");
    b3 = new JButton("Logout");
    menu = new JPanel();

    cardLayout = new CardLayout();
    cards = new JPanel(cardLayout);
    card1 = new JPanel();
    card2 = new JPanel();

    shopPanel = new JPanel(new BoxLayout(menu, BoxLayout.X_AXIS));
    this.artikelTableModel = artikelTableModel;
    shopTable = new JTable(artikelTableModel);
    artikelScroll = new JScrollPane(shopTable);
    search = new JTextField();
    filter = new JComboBox<>(filterTypes);
    suchButton = new JButton("suchen");

    WKPanel = new JPanel(new BoxLayout(menu, BoxLayout.PAGE_AXIS));
    summe = new JLabel("Momentane Gesamtsumme: 0");
    kaufen = new JButton("kaufen");
    clearAll = new JButton("clear All");
    WKTableModel = new WKTableModel(null);
    WKTable = new JTable(WKTableModel);
    korbScroll = new JScrollPane(WKTable);

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
    card1.setBorder(BorderFactory.createTitledBorder("Willkommen im Shop!"));
    card2.setBorder(BorderFactory.createTitledBorder("Dein Warenkorb"));
    
    //Shopansicht mit Artikel Tabelle
    shopPanel.setPreferredSize(new Dimension(500,140));
    shopPanel.setLayout(new FlowLayout());

    search.setPreferredSize(new DimensionUIResource(250, 30));
    search.setText("wonach suchst du?");
    search.setForeground(Color.LIGHT_GRAY);

    filter.setPreferredSize(new DimensionUIResource(100, 30));
    suchButton.setPreferredSize(new DimensionUIResource(100, 30));

    //Tabelle mit eigenem CellRenderer der Zelle als Button rendered
    shopTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("in den Warenkorb"));;
    shopTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    shopTable.getColumn("Nr").setMaxWidth(20);
    shopTable.getColumn("Artikel").setMinWidth(200);
    shopTable.getColumn("Preis").setMinWidth(90);
    shopTable.getColumn("auf Lager").setMinWidth(90);
    shopTable.getColumn("").setMaxWidth(50);
    
    //Warenkorbansicht mit Tabelle über Warenkorb Inhalt
    WKPanel.setPreferredSize(new Dimension(500,140));
    WKPanel.setLayout(new FlowLayout());

    WKTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("aus Warenkorb entfernen"));;

    //add Components....
    //...to menü
    // Abstandhalter ("Filler") zwischen Rand und erstem Element
    Dimension borderMinSize = new Dimension(5, 10);
    Dimension borderPrefSize = new Dimension(5, 10);
    Dimension borderMaxSize = new Dimension(5, 10);
    menu.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
 
    menu.add(b1);
    menu.add(b2);
 
    // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
    Dimension fillerMinSize = new Dimension(5,20);
    Dimension fillerPreferredSize = new Dimension(5,Short.MAX_VALUE);
    Dimension fillerMaxSize = new Dimension(5,Short.MAX_VALUE);
    menu.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));
 
    menu.add(b3);
 
    //Abstandhalter ("Filler") zwischen letztem Element und Rand
    menu.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
     
    //...to shopansicht
    borderMinSize = new Dimension(500, 40);
    borderPrefSize = new Dimension(500, 40);
    borderMaxSize = new Dimension(500, 40);
    shopPanel.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
    shopPanel.add(search);
    shopPanel.add(suchButton);
    shopPanel.add(filter);

    //shopansicht zu card1
    card1.add(shopPanel, BorderLayout.NORTH);
    card1.add(artikelScroll, BorderLayout.CENTER);

    //...to Warenkorbansicht
    WKPanel.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
    WKPanel.add(summe);
    WKPanel.add(kaufen);
    WKPanel.add(clearAll);

    card2.add(WKPanel, BorderLayout.CENTER);
    card2.add(korbScroll, BorderLayout.NORTH);

    cards.add(card1, "RedCard");
    cards.add(card2, "BlueCard");

    add(menu, BorderLayout.WEST);
    add(cards, BorderLayout.CENTER);
  }

  private void initializeAction(){

    //Actions die nur Änderungen innerhalb des Panels (der KLasse) machen
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

    //eingeben in suchzeile 
    search.addMouseListener(new MouseAdapter(){ 
      public void mouseReleased(MouseEvent me){
        if(search.getText().equals("wonach suchst du?")){
          search.setText("");
        };
        search.setForeground(Color.black);
        
      }});

      //MouseListener für gesamte Tabelle, schickt Action Event an GUI falls Click in Row 4
      shopTable.addMouseListener(new MouseInputAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
          int selectedColumn = shopTable.getSelectedColumn();
          if (selectedColumn == 4){
            JButton button = new JButton();
            //Zwecksmäßiger Button um ActionEvent zu feuern
            button.setActionCommand("kunde_hinzufügen");
            button.addActionListener(gui);
            button.doClick();
          }
        }
      });

      WKTable.addMouseListener(new MouseInputAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
          int selectedColumn = shopTable.getSelectedColumn();
          if (selectedColumn == 4){
            JButton button = new JButton();
            //Zwecksmäßiger Button um ActionEvent zu feuern
            button.setActionCommand("kunde_entfernen");
            button.addActionListener(gui);
            button.doClick();
          }
        }
      });

      //Actions die auf Methoden im Eshop zurückgreifen
      //logout Button
      b3.setActionCommand("kunde_logout");
      b3.addActionListener(this.gui);

      kaufen.setActionCommand("kunde_kaufen");
      kaufen.addActionListener(gui);

      clearAll.setActionCommand("kunde_clearAll");
      clearAll.addActionListener(gui);

      suchButton.setActionCommand("kunde_suchen");
      suchButton.addActionListener(this.gui);
  }

  /**
   * updated Artikel Tabelle
   * @param data Vektor mit sichtbaren Artikeln 
   */
  public void updateArtikel(Vector data){
    ArtikelTableModel tablemodel = (ArtikelTableModel) shopTable.getModel();
    tablemodel.setArtikel(data);
  }

  /**
   * updatet Warenkorb 
   * @param data Artikel die im Warenkorb enthalten als hashmap
   * @param sum Summe aller Preise
   */
  public void updateWK(HashMap data, double sum){
    WKTableModel tablemodel = (WKTableModel) WKTable.getModel();
    tablemodel.setArtikel(data);
    summe.setText("momentane Gesamtsumme: " + sum);
  }

  //get Methoden 

  public String getSelectedFilter(){
    return filter.getSelectedItem().toString();
  }

  public String getSearchterm(){
    return search.getText();
  }

  public Integer getSelectedRow(){
    return shopTable.getSelectedRow();
  }
}

