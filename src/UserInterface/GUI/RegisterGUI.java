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

public class RegisterGUI extends JPanel{
    GUI gui;
    JLabel title;
    JPanel inputPanel;
    JLabel nameLabel;
    JLabel userLabel;
    JLabel passwordLabel;
    JLabel mailLabel;
    JLabel adressLabel;
    JTextField nameText;
    JTextField userText;
    JTextField passwordText;
    JTextField mailText;
    JTextField adressText;
    JLabel validation;
    JButton registerButton;
    JButton backToLogin;

    public RegisterGUI(GUI gui)   {

        this.gui = gui;
        title = new JLabel("Register", JLabel.CENTER);   
        inputPanel = new JPanel();
        nameLabel = new JLabel("Name");
        userLabel = new JLabel("User");
        passwordLabel = new JLabel("password");
        mailLabel = new JLabel("Email");
        adressLabel = new JLabel("Adresse");
        nameText = new JTextField();
        userText = new JTextField();
        passwordText = new JTextField();
        mailText = new JTextField();
        adressText = new JTextField();
        validation = new JLabel("");
        registerButton = new JButton("register");
        backToLogin = new JButton("back to login");
        
        initializeLayout();
        initializeAction();
        setVisible(true);
    }

    private void initializeLayout() {
        
        //gesamtes Panel
        setPreferredSize(new Dimension(400,600));
        setLayout(null);
        setBackground(new Color(71,39,79));
        GridBagConstraints c = new GridBagConstraints();

        //Überschrift Login
        title.setBackground(Color.white);
        title.setOpaque(true);
        Font font = new Font("Verdana", Font.PLAIN, 30);
        title.setFont(font);
        title.setBounds(50,30,300,50);
        add(title);

        //Label, Eingabefelder, Button in einem Panel 
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBounds(50,110,300, 290);
        add(inputPanel);

        c.gridy = 0;
        c.weighty=0;
        //c.weightx=0.2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 10, 10,0);
        c.ipadx = 10;
        inputPanel.add(nameLabel, c);

        c.gridy=1;
        c.gridx=0;
        inputPanel.add(userLabel, c);

        c.gridy=2;
        inputPanel.add(passwordLabel, c);

        c.gridy=3;
        inputPanel.add(mailLabel, c);

        c.gridy=4;
        inputPanel.add(adressLabel, c);

        c.weightx = 1;
        c.gridx=1;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.ipady = 10;
        c.insets = new Insets(0,10,10,10);
        inputPanel.add(nameText, c);

        c.gridy = 1;
        inputPanel.add(userText, c);

        c.gridy = 2;
        inputPanel.add(passwordText, c);

        c.gridy = 3;
        inputPanel.add(mailText, c);

        c.gridy = 4;
        inputPanel.add(adressText,c);

        c.gridy = 5;
        inputPanel.add(validation, c);

        c.gridy = 6;
        c.gridx = 0;
        c.ipady = 5;
        c.gridwidth = 2;
        c.insets = new Insets(0,10,0,10);
        inputPanel.add(registerButton,c);

        
        backToLogin.setBounds(50,560,300,30);
        backToLogin.setOpaque(false);
        backToLogin.setContentAreaFilled(false);
        backToLogin.setBorderPainted(false);
        backToLogin.setForeground(Color.white);
        add(backToLogin);

    }

    public void initializeAction(){
        registerButton.addActionListener(gui);
        backToLogin.addActionListener(gui);
        backToLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                backToLogin.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent me) {
                backToLogin.setForeground(Color.white);
            }
        });
    }

    void clearText(){
        nameText.setText("");
        userText.setText("");
        passwordText.setText("");
        mailText.setText("");
        adressText.setText("");
    }

}

