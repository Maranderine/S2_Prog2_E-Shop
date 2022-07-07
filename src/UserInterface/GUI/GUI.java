package UserInterface.GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.*;
import Domain.Eshop;
import Domain.Search.SuchOrdnung;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import Exceptions.Input.ExceptionInputFalsch;
import UserInterface.UserInterface;
import Domain.Artikel.Artikel;

public class GUI extends UserInterface implements ActionListener {
  JFrame frame;
  LoginGUI login;
  RegisterGUI register;
  KundeGUI kunde;
  InfoBox info;
  SuchOrdnung ordnung;
  Vector<Artikel> tableData;

  public GUI(Eshop eshop) {

    super(eshop);
    frame = new JFrame("mainFrame");
    login = new LoginGUI(this);
    register = new RegisterGUI(this);
    tableData = eshop.AV_getAlleArtikelList();
    kunde = new KundeGUI(this, tableData);
    info = new InfoBox();

    buildMainWindow();

  }

  // reagiert auf aktionen, kommuniziert mit Eshop, führt entsprechende Befehle
  // zur Layout Änderung in den Panels aus
  public void actionPerformed(ActionEvent ae) {
    switch (ae.getActionCommand()) {

      /*
      * login
      */
      case "login_loginButton": 
        switch (eshop.login(this, login.userText.getText(), login.passwordText.getText())) {

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
        break;

      case "login_toRegister":
        setVisiblePanel("register");
        break;

      /*
      * Registrieren
      */
      case "register_registerButton":
        try {
        String name = CheckStringNamen("" + register.vornameLabel.getText() + " " + register.nameText.getText());
        String email = CheckStringEmail(""+register.mailText.getText());
        String address = register.landBox.getSelectedItem() + CheckStringAdresseOrt(register.ortText.getText())
          + CheckStringAdresseStraße(""+register.streetText.getText());
        String un = register.userText.getText();
        String passwort = CheckStringPasswort(""+register.passwordText.getText());

        eshop.BV_kundeHinzufügen(name, un, passwort, email, address);
        info.infoBox("Konto wurde erstellt", "Bestätigung");

        } catch (Exception e) {
        info.infoBox(e.getMessage(), "Registrieren Fehler");
        }
        
        break;
        
      case "register_backToLogin":
        setVisiblePanel("login");
        break;
        
      case "kunde":
        kunde.setCard();
        setVisiblePanel("login");
        System.out.println("bjhdsjs");
        break;

    }
  }

  public void buildMainWindow() {
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

  // bestimmmt sichtbares Panel
  public void setVisiblePanel(String sichtbar) {

    // Alle panels unsichtbar setzen
    login.setVisible(false);
    register.setVisible(false);
    kunde.setVisible(false);

    // richtiges Panel sichtbar setzen
    switch (sichtbar) {
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

  public static void main(String[] args) {
    Eshop eshop = new Eshop("Nutzer.txt", "Ereignisse.txt");
    GUI gui = new GUI(eshop);
  }

  @Override
  public boolean run() {
    // TODO Auto-generated method stub
    return false;
  }


  // #region input checking
  // premade string pattern checks

  /**
   * checkt input für Text pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringText(String string) throws ExceptionInputFalsch {
    CheckString(PatternText, string, null);
    return string;
  }

  /**
   * checkt input für Namen pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringNamen(String string) throws ExceptionInputFalsch {
    CheckString(PatternEchtNamen, string, null );
    return string;
  }

  /**
   * checkt input für Artikel pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringArtikel(String string) throws ExceptionInputFalsch {
    CheckString(PatternArtikel, string, null);
    return string;
  }

  /**
   * checkt input für Num pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringNum(String string) throws ExceptionInputFalsch {
    CheckString(PatternNum, string, null);
    return string;
  }

  /**
   * checkt input für Int pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringInt(String string) throws ExceptionInputFalsch {
    CheckString(PatternInt, string, null);
    return string;
  }

  /**
   * checkt input für Float pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringFloat(String string) throws ExceptionInputFalsch {
    CheckString(PatternFloat, string, null);
    return string;
  }

  /**
   * checkt input für Email pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringEmail(String string) throws ExceptionInputFalsch {
    CheckString(PatternEmail, string, null); 
    return string;
  }

  /**
   * checkt input für PatternAdresseStraße pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringAdresseStraße(String string) throws ExceptionInputFalsch {
    CheckString(PatternAdresseStraße, string, null);
    return string;
  }

  /**
   * checkt input für PatternAdresseOrt pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringAdresseOrt(String string) throws ExceptionInputFalsch {
    CheckString(PatternAdresseOrt, string, null);
    return string;
  }

  /**
   * checkt input für PatternAdresseLand pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringAdresseLand(String string) throws ExceptionInputFalsch {
    CheckString(PatternAdresseLand, string, null);
    return string;
  }

  /**
   * checkt input für PasswortNew pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected String CheckStringPasswort(String string) throws ExceptionInputFalsch {
    CheckString(PatternPasswort, string, null);
    return string;
  }
}

