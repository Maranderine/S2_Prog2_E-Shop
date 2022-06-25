package UserInterface.GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import Domain.Eshop;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import UserInterface.UserInterface;


public class GUI extends UserInterface implements ActionListener{
    JFrame frame;
    LoginGUI login;
    RegisterGUI register;
    KundeGUI kunde;
    InfoBox info;

    public GUI(Eshop eshop){

        super(eshop);
        frame = new JFrame("maiNFrame");
        login = new LoginGUI(this);
        register = new RegisterGUI(this);
        kunde = new KundeGUI(this);
        info = new InfoBox();

        buildMainWindow();

    }


    public void buildMainWindow(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(register);
        frame.add(login);
        frame.add(kunde);
        setVisiblePanel("login");
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        }


    //reagiert auf aktionen, kommuniziert mit Eshop, führt entsprechende Befehle zur Layout Änderung in den Panels aus
    public void actionPerformed(ActionEvent ae) {
        //login
        if(ae.getSource() == login.loginButton){
            switch(eshop.login(this, login.userText.getText(), login.passwordText.getText())){
                    case NONE: 
                        info.infoBox("username oder Passwort falsch", "Login Fehler");
                        login.clearText();
                        break;
                    case KUNDE:
                        setVisiblePanel("kunde");
                        login.clearText();
                        break;
                    case MITARBEITER:
                     login.clearText();
                        setVisiblePanel("kunde");
                        login.clearText();
                        break;
            }
        }
        if(ae.getSource() == login.toRegister){
            setVisiblePanel("register");
        }

        //register
        if(ae.getSource()== register.registerButton){
            try{
                String name = "" + register.vornameLabel.getText() + register.nameText.getText();
                String email = register.mailText.getText();
                String address = "" + register.landBox.getSelectedItem() + register.ortLabel.getText() + register.streetLabel.getText();
                String un = register.userText.getText();
                String passwort = register.passwordText.getText();

                eshop.BV_kundeHinzufügen(name, un, passwort, email, address);
                info.infoBox("Konto wurde erstellt", "Bestätigung");

            } catch (ExceptionBenutzerNameUngültig e) {
                info.infoBox(e.getMessage(), "Registrieren Fehler");
                register.clearText();
            }  
        }
        if(ae.getSource()== register.backToLogin){
            setVisiblePanel("login");
        }
    }

    //bestimmmt sichtbares Panel
    public void setVisiblePanel(String sichtbar){

        //Alle panels unsichtbar setzen
        login.setVisible(false);
        register.setVisible(false);
        kunde.setVisible(false);

        //richtiges Panel sichtbar setzen
        switch(sichtbar){
            case "none":
                break;
            case "login": 
                login.setVisible(true);
                frame.pack();
                break;
            case "register": 
                register.setVisible(true);
                frame.pack();
                break;
            case "kunde":
                kunde.setVisible(true);
                frame.pack();
                break;
        }

    }
        
    public static void main(String[] args){
        Eshop eshop = new Eshop("Nutzer.txt", "Ereignisse.txt");
         GUI gui = new GUI(eshop);
    }

    @Override
    public boolean run(){
        // TODO Auto-generated method stub
        return false;
    }
        
}


//reagiert auf aktionen, kommuniziert mit Eshop, führt entsprechende Befehle zur Layout Änderung in den Panels aus
   /* public void actionPerformed(ActionEvent ae) {
        switch(Level()){
            case LOGIN:

                //entnimmt Textfeldern String --> eshop.login
                switch(eshop.login(this, login.userText.getText(), login.passwordText.getText())){
                        case NONE: 
                            login.clearText();
                            info.infoBox("username oder passwort falsch", "login Fehler");
                            break;
                        case KUNDE:
                            LevelMove(MenuLevel.KUNDEN_ANSICHT);
                            setVisiblePanel();
                            info.infoBox("username oder passwort falsch", "login Fehler");
                            break;
                        case MITARBEITER:
                            LevelMove(MenuLevel.KUNDEN_ANSICHT);
                            setVisiblePanel();
                            break;
                }

            case KUNDEN_REGISTRIEREN:

                try{
                    String name = register.nameText.getText();
                    String email = register.mailText.getText();
                    String address = register.adressText.getText();
                    String un = register.userText.getText();
                    String passwort = register.passwordText.getText();

                    eshop.BV_kundeHinzufügen(name, un, passwort, email, address);

                    info.infoBox("Konto wurde erstellt", "Bestätigung");
                    LevelReturn();
                    setVisiblePanel();

                } catch (ExceptionBenutzerNameUngültig e) {
                    info.infoBox(e.getMessage(), "Registrieren Fehler");
                }
                break;
            case KUNDEN_ANSICHT:
                break;
        }
    }
    
    enum MenuLevel {
        LOGIN, // start menu
        KUNDEN_REGISTRIEREN,
        KUNDEN_ANSICHT,
      }
      
    */