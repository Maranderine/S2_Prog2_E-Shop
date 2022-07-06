package UserInterface.GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import Domain.Eshop;
import Domain.Search.SuchOrdnung;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import Exceptions.Input.ExceptionInputFalsch;
import UserInterface.UserInterface;

public class GUI extends UserInterface implements ActionListener {
  JFrame frame;
  LoginGUI login;
  RegisterGUI register;
  KundeGUI kunde;
  InfoBox info;

  public GUI(Eshop eshop) {

    super(eshop);
    frame = new JFrame("maiNFrame");
    login = new LoginGUI(this);
    register = new RegisterGUI(this);
    kunde = new KundeGUI(this);
    info = new InfoBox();

    buildMainWindow();

  }

  // reagiert auf aktionen, kommuniziert mit Eshop, führt entsprechende Befehle
  // zur Layout Änderung in den Panels aus
  public void actionPerformed(ActionEvent ae) {
    switch (ae.getActionCommand()) {
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

      case "register_registerButton":
        try {
          String name = "" + register.vornameLabel.getText() + register.nameText.getText();
          String email = register.mailText.getText();
          String address = "" + register.landBox.getSelectedItem() + register.ortLabel.getText()
              + register.streetLabel.getText();
          String un = register.userText.getText();
          String passwort = register.passwordText.getText();

          eshop.BV_kundeHinzufügen(name, un, passwort, email, address);
          info.infoBox("Konto wurde erstellt", "Bestätigung");

        } catch (ExceptionBenutzerNameUngültig e) {
          info.infoBox(e.getMessage(), "Registrieren Fehler");
          register.clearText();
        }
        break;
      case "register_backToLogin":
        setVisiblePanel("login");
        break;
    }
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
  protected void CheckStringText(String string) throws ExceptionInputFalsch {
    CheckString(PatternText, string, null);
  }

  /**
   * checkt input für Namen pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringNamen(String string) throws ExceptionInputFalsch {
    CheckString(PatternEchtNamen, string, null);
  }

  /**
   * checkt input für Artikel pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringArtikel(String string) throws ExceptionInputFalsch {
    CheckString(PatternArtikel, string, null);
  }

  /**
   * checkt input für Num pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringNum(String string) throws ExceptionInputFalsch {
    CheckString(PatternNum, string, null);
  }

  /**
   * checkt input für Int pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringInt(String string) throws ExceptionInputFalsch {
    CheckString(PatternInt, string, null);
  }

  /**
   * checkt input für Float pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringFloat(String string) throws ExceptionInputFalsch {
    CheckString(PatternFloat, string, null);
  }

  /**
   * checkt input für Email pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringEmail(String string) throws ExceptionInputFalsch {
    CheckString(PatternEmail, string, null);
  }

  /**
   * checkt input für PatternAdresseStraße pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringAdresseStraße(String string) throws ExceptionInputFalsch {
    CheckString(PatternAdresseStraße, string, null);
  }

  /**
   * checkt input für PatternAdresseOrt pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringAdresseOrt(String string) throws ExceptionInputFalsch {
    CheckString(PatternAdresseOrt, string, null);
  }

  /**
   * checkt input für PatternAdresseLand pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringAdresseLand(String string) throws ExceptionInputFalsch {
    CheckString(PatternAdresseLand, string, null);
  }

  /**
   * checkt input für PasswortNew pattern
   * 
   * @param string string zu checken
   * @throws ExceptionInputFalsch
   */
  protected void CheckStringPasswort(String string) throws ExceptionInputFalsch {
    CheckString(PatternPasswort, string, null);
  }
}

