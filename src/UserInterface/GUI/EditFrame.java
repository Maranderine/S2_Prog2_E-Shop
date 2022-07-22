package UserInterface.GUI;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;

import Domain.Artikel.Artikel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class EditFrame {
    Artikel artikel = null;
    GUI gui;
    String titel;
    JFrame frame; 
    JPanel inputPanel;
    JPanel buttonPanel;
    JPanel gesamt;
    //Artikel
    JLabel artikelNameLabel;
    JLabel nrLabel;
    JLabel preisLabel;
    JLabel bestandLabel;
    JLabel stückLabel;
    JLabel artLabel;
    JTextField artikelNameText;
    JTextField nrText;
    JTextField preisText;
    JTextField bestandText;
    JTextField stückText;
    //Mitarbeiter
    JLabel nutzerNameJLabel;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField nutzerNameText;
    JTextField usernameText;
    JTextField passwordText;

    
    JButton saveButton;
    JButton addButton;
    JButton createButton;
    JButton cancelButton;
    JButton deleteButton;
    JButton historyButton;
    JComboBox<String> typeBox;

    public EditFrame(GUI gui, String titel){
        this.gui = gui;
        this.titel = titel;
        frame = new JFrame();
        frame.setSize(420, 420);
        frame.setVisible(true);

        String[] landAuswahl = {"Einzelartikel", "Massengut"};
        inputPanel = new JPanel();
        buttonPanel = new JPanel(new GridBagLayout());
        gesamt = new JPanel(new BorderLayout());

        artikelNameLabel = new JLabel("Bezeichnung");
        nrLabel = new JLabel("Nr");
        preisLabel = new JLabel("Preis");
        bestandLabel = new JLabel("Bestand");
        artLabel = new JLabel("Art");
        stückLabel= new JLabel("Stückzahl");

        artikelNameText = new JTextField();
        nrText = new JTextField();
        preisText = new JTextField();
        bestandText = new JTextField();
        stückText = new JTextField();

        nutzerNameJLabel = new JLabel("Name");
        usernameLabel = new JLabel("Username");
        passwordLabel= new JLabel("Passwort");
        nutzerNameText = new JTextField();
        usernameText = new JTextField();
        passwordText = new JTextField();
        
        saveButton = new JButton("sichern");
        addButton = new JButton("hinzufügen");
        createButton = new JButton("erstellen");
        cancelButton = new JButton("abbrechen");
        deleteButton = new JButton("löschen");
        historyButton = new JButton("Bestands Historie");
        typeBox = new JComboBox<>(landAuswahl);

        initializeAction();
        }

        public void initializeLayoutArtikelErstellen() {
            inputPanel.setLayout(new GridBagLayout());
            inputPanel.setPreferredSize(new Dimension(320, 300));

            //Label, Eingabefelder, Button in einem Panel 
            createButton.setBackground(new Color(71,39,79));
            createButton.setForeground(Color.white);
            createButton.setBorder(new EmptyBorder(0,10,0,10));
            cancelButton.setBackground(new Color(71,39,79));
            cancelButton.setForeground(Color.white);
            cancelButton.setBorder(new EmptyBorder(0,10,0,10));
            
            

            stückText.setEnabled(false);
            stückText.setBackground(Color.LIGHT_GRAY);

            GridBagConstraints c = new GridBagConstraints();

            c.gridy = 0;
            c.weightx=1;
            c.anchor = GridBagConstraints.LINE_START;
            c.insets = new Insets(0, 10, 10,0);
            c.ipadx = 10;
            inputPanel.add(artikelNameLabel, c);

            c.gridy=1;
            c.gridx=0;
            inputPanel.add(nrLabel, c);

            c.gridy=2;
            inputPanel.add(preisLabel, c);

            c.gridy=3;
            inputPanel.add(bestandLabel, c);

            c.gridy=4;
            inputPanel.add(artLabel, c);

            c.gridy=5;
            inputPanel.add(stückLabel, c);


            c.weightx = 1;
            c.gridx=1;
            c.gridy = 0;
            c.ipady = 10;
            c.insets = new Insets(0,10,10,10);
            c.fill = GridBagConstraints.HORIZONTAL;
            inputPanel.add(artikelNameText, c);

            c.gridy = 1;
            inputPanel.add(nrText, c);

            c.gridy = 2;
            inputPanel.add(preisText, c);

            c.gridy = 3;
            inputPanel.add(bestandText, c);

            c.gridy = 4;
            inputPanel.add(typeBox,c);

            c.gridy = 5;
            inputPanel.add(stückText, c);

            c.fill = GridBagConstraints.NONE;
            c.weightx = 0;
            c.gridx = 0;
 
            buttonPanel.add(createButton, c);
            c.gridx = 1;
            buttonPanel.add(cancelButton, c);

            gesamt.add(inputPanel, BorderLayout.CENTER);
            gesamt.add(buttonPanel, BorderLayout.SOUTH);
            gesamt.setBorder(BorderFactory.createTitledBorder(titel));
            frame.add(gesamt);

            frame.pack();
            frame.setVisible(true);
        }

        public void initializeLayoutArtikelBearbeiten(Artikel artikel){
            this.artikel = artikel;
            inputPanel.setLayout(new GridBagLayout());
            inputPanel.setPreferredSize(new Dimension(320, 300));

            //Button Layout
            saveButton.setBackground(new Color(71,39,79));
            saveButton.setForeground(Color.white);
            saveButton.setBorder(new EmptyBorder(0,10,0,10));

            cancelButton.setBorder(new EmptyBorder(0,10,0,10));
            cancelButton.setBackground(new Color(71,39,79));
            cancelButton.setForeground(Color.white);

            historyButton.setBackground(new Color(71,39,79));
            historyButton.setForeground(Color.white);
            historyButton.setBorder(new EmptyBorder(0,10,0,10));

            deleteButton.setBorder(new EmptyBorder(0,10,0,10));
            deleteButton.setBackground(Color.red);
            deleteButton.setForeground(Color.white);

            //Daten des asugewählten Artikels in Textfelder einfügen
            artikelNameText.setText(artikel.getName());
            preisText.setText(artikel.getPreis()+"");
            bestandText.setText(artikel.getBestand()+"");

            GridBagConstraints c = new GridBagConstraints();

            //ins panel einfügen
            //Panel mit textfeldern
            c.gridy = 0;
            c.weightx=1;
            c.anchor = GridBagConstraints.LINE_START;
            c.insets = new Insets(0, 10, 10,0);
            c.ipadx = 10;
            inputPanel.add(artikelNameLabel, c);

            c.gridy=1;
            inputPanel.add(preisLabel, c);

            c.gridy=2;
            inputPanel.add(bestandLabel, c);

            c.weightx = 1;
            c.gridx=1;
            c.gridy = 0;
            c.ipady = 10;
            c.insets = new Insets(0,10,10,10);
            c.fill = GridBagConstraints.HORIZONTAL;
            inputPanel.add(artikelNameText, c);

            c.gridy = 1;
            inputPanel.add(preisText, c);

            c.gridy = 2;
            inputPanel.add(bestandText, c);

            //save und cancel button panel
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0;
            c.gridx = 0;

            buttonPanel.add(saveButton, c);
            c.gridx = 1;
            buttonPanel.add(historyButton, c);
            c.gridx = 2;
            buttonPanel.add(deleteButton,c);

            //alles zusammenfügen --> buttonpanel und textfeld panel ins gesamt panel
            gesamt.add(inputPanel, BorderLayout.CENTER);
            gesamt.add(buttonPanel, BorderLayout.SOUTH);
            gesamt.setBorder(BorderFactory.createTitledBorder(titel));
            frame.add(gesamt);

            frame.pack();
            frame.setVisible(true);
        }

    public void initializeLayoutMitarbeiter() {

        //Label, Eingabefelder, Button in einem Panel 
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setPreferredSize(new Dimension(320, 270));

        //Layout Buttons
        addButton.setBackground(new Color(71,39,79));
        addButton.setForeground(Color.white);
        addButton.setBorder(new EmptyBorder(0,10,0,10));

        cancelButton.setBorder(new EmptyBorder(0,10,0,10));
        cancelButton.setBackground(new Color(71,39,79));
        cancelButton.setForeground(Color.white);

        //einfügen ins panel
        //Panel mit Textfeldern
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.weightx=1;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 10, 10,0);
        //c.ipadx = 10;
        inputPanel.add(nutzerNameJLabel, c);

        c.gridy=1;
        c.gridx=0;
        inputPanel.add(usernameLabel, c);

        c.gridy=2;
        inputPanel.add(passwordLabel, c);

        c.gridx=1;
        c.gridy = 0;
        c.ipady = 10;
        c.insets = new Insets(0,10,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(nutzerNameText, c);

        c.gridy = 1;
        inputPanel.add(usernameText, c);

        c.gridy = 2;
        inputPanel.add(passwordText, c);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;

        //Panel mit buttons
        buttonPanel.add(addButton,c);
        c.gridx = 1;

        //panels dem gesamt Panel hinzufügen
        gesamt.add(inputPanel, BorderLayout.CENTER);
        gesamt.add(buttonPanel, BorderLayout.SOUTH);
        gesamt.setBorder(BorderFactory.createTitledBorder(titel));
        frame.add(gesamt);

        frame.pack();
        frame.setVisible(true);
    }

   public void initializeAction(){
        typeBox.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(typeBox.getSelectedItem().equals("Einzelartikel")){stückText.setBackground(Color.lightGray); stückText.setEnabled(false);}
                if(typeBox.getSelectedItem().equals("Massengut")){stückText.setBackground(Color.white); stückText.setEnabled(true);}
            }
        });

        cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        historyButton.setActionCommand("Edit_Bestandshistorie");
        historyButton.addActionListener(gui);

        saveButton.setActionCommand("Edit_ArtikelBearbeiten");
        saveButton.addActionListener(gui);

        createButton.setActionCommand("Edit_ArtikelErstellen");
        createButton.addActionListener(gui);

        addButton.setActionCommand("Edit_MitarbeiterHinzufügen");
        addButton.addActionListener(gui);

        deleteButton.setActionCommand("Edit_ArtikelLöschen");
        deleteButton.addActionListener(gui);
   }

   public String getArtikelName(){
    return artikelNameText.getText();
   }

   public String getNr(){
    return nrText.getText();
   }

   public String getPreis(){
    return preisText.getText();
   }

   public String getBestand(){
    return bestandText.getText();
   }

   public String getStückzahl(){
    return (einzelartikelSelected())? ""+ 1 : stückText.getText();
   }

   public String getNutzerName(){
    return nutzerNameText.getText();
   }

   public String getUsername(){
    return usernameText.getText();
    }

    public String getPasswort(){
        return passwordText.getText();
    }

    /**
     * gibt true wenn Einzelartikel ausgewählt, gibt false wenn Massengut
     * @return
     */
    public Boolean einzelartikelSelected(){
      return (typeBox.getSelectedItem().toString().equals("Einzelartikel"))?  true :  false;
    }

    public void dispose(){
        frame.dispose();
    }

    public Artikel getArtikel(){
        return this.artikel;
    }
}