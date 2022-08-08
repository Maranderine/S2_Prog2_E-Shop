package UserInterface.GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class LoginGUI extends JPanel {

    //Variablen
    GUI gui;
    JLabel title;
    JButton toRegister;
    JLabel validation;
    JPanel inputPanel;
    JLabel userLabel;
    JLabel passwordLabel;
    JButton loginButton;
    JTextField userText;
    JPasswordField passwordText;

    public LoginGUI(GUI gui){

        this.gui = gui;
        title = new JLabel("Login", JLabel.CENTER);
        toRegister = new JButton("Register now");
        toRegister.setActionCommand("login_toRegister");
        validation = new JLabel("", JLabel.CENTER);
        inputPanel = new JPanel();
        userLabel = new JLabel("Username");
        passwordLabel = new JLabel("password");
        loginButton = new JButton("login");
        loginButton.setActionCommand("login_loginButton");
        userText = new JTextField();
        passwordText = new JPasswordField();
        
        initializeLayout();
        initializeAction();
        setVisible(true);
    }

    private void initializeLayout() {
        
        //layout Components
        //gesamtes Panel
        setPreferredSize(new Dimension(400,330));
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
        inputPanel.setBounds(50,110,300, 150);
        add(inputPanel);

        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        c.weighty=0;
        c.insets = new Insets(0,10,10,0);
        c.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(userLabel, c);

        c.gridy=1;
        inputPanel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.ipady = 10;
        c.weightx = 1;
        c.insets = new Insets(0,10,10,10);
        c.fill = GridBagConstraints.BOTH;
        inputPanel.add(userText, c);

        c.gridy = 1;
        inputPanel.add(passwordText, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.ipady = 5;
        c.insets = new Insets(20,10,0,10);
        inputPanel.add(loginButton,c);

        //Button um ins Register Menü zu wechseln
        toRegister.setBounds(50,280,300,30);
        toRegister.setOpaque(false);
        toRegister.setContentAreaFilled(false);
        toRegister.setBorderPainted(false);
        toRegister.setForeground(Color.white);
        add(toRegister);

    }

    public void initializeAction(){

        //Listener Initialisieren 
        //Action für Events innerhalb des Panels 
        toRegister.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                toRegister.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent me) {
                toRegister.setForeground(Color.white);
            }
        });

        //Action für Events die außerhalb des Panels behandelt werden 
        loginButton.addActionListener(gui);
        toRegister.addActionListener(gui);
        
    }

    public void clearText(){
        userText.setText("");
        passwordText.setText("");
    }
    
    public void clearColor(){
        userText.setBackground(Color.white);
        passwordText.setBackground(Color.white);
    }

    public void resetLayout(){
        repaint();
    }
}