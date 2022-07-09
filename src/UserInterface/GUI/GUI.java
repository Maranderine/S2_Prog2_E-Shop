package UserInterface.GUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
import Exceptions.Input.ExceptionInputFeldIstLeer;
import UserInterface.UserInterface;
import Domain.Artikel.Artikel;

public class GUI extends UserInterface implements ActionListener {
  JFrame frame;
  LoginGUI login;
  RegisterGUI register;
  KundeGUI kunde;
  InfoBox info;
  SuchOrdnung ordnung;
  Vector artikelList;
  String string = "";

  public GUI(Eshop eshop) {

    super(eshop);
    frame = new JFrame("mainFrame");
    login = new LoginGUI(this);
    register = new RegisterGUI(this);
    artikelList = eshop.AV_getAlleArtikelList();
    kunde = new KundeGUI(this, artikelList);
    info = new InfoBox();

    buildMainWindow();

  }

  // reagiert auf ActionEvents die Kommunikation mit Eshop verlangen
  public void actionPerformed(ActionEvent ae) {
    
      switch (ae.getActionCommand()) {
        //#region login
        case "login_loginButton": 
          //gibt error falls Feld empty
          login.clearColor();
          String username =  checkIfEmpty(login.userText.getText(), "Benutzername", login.userText);
          String password =  checkIfEmpty(login.passwordText.getText(), "Passwort", login.passwordText);
          if(info.errorAccurred()){
            info.infoBox("titleBar");
            break;
          }

          //Wenn nicht, leitet daten an Eshop
            switch (eshop.login(this, username, password)) {
              case NONE:
                info.infoBox("username oder Passwort ungültig", "Login Fehler");
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
        //#endregion login 
        
        //#region register
        case "register_registerButton":
          try{
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
          
        //#endregion register

        //#region kunde
        case "kunde_logout":
          setVisiblePanel("login");
          break;

        //Such Button im Shop
        case "kunde_suchen":
          this.ordnung = null;
          
          String filter = kunde.filter.getSelectedItem().toString();
          String searchTerm = kunde.search.getText();
          if(searchTerm.equals("wonach suchst du?")){searchTerm = null;}

          //switch auf ausgewählten Filter
          switch(filter){
            case "Kein Filter":
              //kein Filter gewählt, ohne suchbegriff
              if(!(searchTerm.equals(""))){
                this.ordnung = eshop.AV_sucheArtikel(searchTerm);
              }else{
              //kein Filter gewählt, mit suchbegriff
                artikelList = eshop.AV_getAlleArtikelList();
              }
              break;
            case "Preis aufsteigend":
              //Filter Preis aufsteigend, ohne Suchbegriff
              if(!(searchTerm.equals(""))){
                this.ordnung = eshop.AV_sucheArtikel(searchTerm);
                eshop.AV_sortListPreis(this.ordnung, false);
              }else{
              //Filter Preis aufsteigend, mit Suchbegriff
                eshop.AV_sortListPreis(artikelList, false);
              }
              break;
            case "Preis absteigend":
              //Filter Preis absteigend, ohne Suchbegriff
              if(!(searchTerm.equals(""))){
                this.ordnung = eshop.AV_sucheArtikel(searchTerm);
                eshop.AV_sortListPreis(this.ordnung, true);
                System.out.println("meep");
              }else{
              //Filter Preis absteigend, mit Suchbegriff
                eshop.AV_sortListPreis(artikelList, true);
              }
              break;
          }
          //falls Ordnung null, gibt artikelListe an Tabelle in Kundenansicht weiter --> Vektor mit allen Artikeln, sortiert oder unsortiert
          if(ordnung == null){
            kunde.updateArtikel(artikelList);
          //falls Ordnung, gibt Ordnung weiter --> auf Suchbegriff angepasster Vektor
          }else{
            kunde.updateArtikel(this.ordnung.getObjektList());
          }
          
          break;
        case "kunde_hinzufügen":
          string = JOptionPane.showInputDialog("Wie viele wollen sie hinzufügen?");
          if(string.equals("") | string.equals(null)){break;}
          int i = Integer.parseInt(string);
          Artikel selectedArticle = (Artikel) kunde.data.get(kunde.shopTable.getSelectedRow());
          eshop.WV_setArtikel(selectedArticle, i);
          break;
    }
  }


  public void buildMainWindow() {
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());
    frame.add(register);
    frame.add(login);
    frame.add(kunde);
    setVisiblePanel("kunde");
    frame.pack();
    frame.setVisible(true);
    frame.setResizable(false);
  }

  /**bestimm sichtbares Panel
   * @param String welches Panel ist sichtbar? "login", "kunde" etc
   */
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

  /**
   * überprüft ob Text Feld leer ist.
   * @param stringToCheck
   * @param fieldName
   * @param field
   * @return
   */
  public String checkIfEmpty(String stringToCheck, String fieldName, JTextField field){
    if(stringToCheck.equals("")){
      info.addString("Feld '"+ fieldName + "' ist leer.");
      field.setBackground(Color.red);
    }
    return stringToCheck;
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

   //TODO exception message nicht throwen sondern infobox hinzufügen --> am Ende gesammelte Errors ausgeben
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

