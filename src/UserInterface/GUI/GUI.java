package UserInterface.GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.Search.SuchOrdnung;
import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtErstelltWerden;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtGelöschtWerden;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import Exceptions.Ereignis.ExceptionEreignisNichtGefunden;
import Exceptions.Input.ExceptionInputFalsch;
import UserInterface.UserInterface;
import UserInterface.GUI.models.ArtikelTableModel;
import UserInterface.GUI.models.ArtikelVwTableModel;
import UserInterface.GUI.models.EventTableModel;
import UserInterface.GUI.models.MitarbeiterTableModel;
import common.EshopInterface;

//TODO: ordnungsarbeit --> variablen namen anpassen, unter ornder für GUIs, minifenster, evtl edit frame aufteilen
public class GUI extends UserInterface implements ActionListener {

  // variablen
  JFrame frame;
  LoginGUI login;
  RegisterGUI register;
  KundeGUI kunde;
  MitarbeiterGUI employ;

  ArtikelTableModel artikelTableModel;
  ArtikelVwTableModel artikelVwTableModel;
  MitarbeiterTableModel mitarbeiterTableModel;
  EventTableModel eventTableModel;
  SuchOrdnung ordnung;
  Vector artikelList;
  Vector<Benutzer> nutzerList;
  Vector<Ereignis> eventList;

  InfoBox info;
  String string = "";
  Artikel selectedArticle;
  String searchterm;
  String filter;
  Integer integ;
  EditFrame edit;

  public GUI(EshopInterface eshop) {

    super(eshop);
    artikelList = eshop.AV_getAlleArtikelList();
    nutzerList = eshop.BV_getAllNutzer();
    eventList = eshop.EV_getLog();
    artikelVwTableModel = new ArtikelVwTableModel(artikelList);
    artikelTableModel = new ArtikelTableModel(artikelList);
    mitarbeiterTableModel = new MitarbeiterTableModel(nutzerList);
    eventTableModel = new EventTableModel(eventList);

    frame = new JFrame("mainFrame");
    login = new LoginGUI(this);
    register = new RegisterGUI(this);
    kunde = new KundeGUI(this, artikelTableModel);
    employ = new MitarbeiterGUI(this, artikelVwTableModel, mitarbeiterTableModel, eventTableModel);
    info = new InfoBox();

    buildMainWindow("login");

  }

  // reagiert auf ActionEvents die Kommunikation mit Eshop verlangen
  // TODO: in einelne Funktionen aufteilen
  public void actionPerformed(ActionEvent ae) {
    switch (ae.getActionCommand()) {

      // #region login

      case "login_loginButton":
        // gibt error falls Feld empty
        login.clearColor();
        String username = checkIfEmpty(login.userText.getText(), "Benutzername", login.userText);
        String password = checkIfEmpty(login.passwordText.getText(), "Passwort", login.passwordText);
        if (info.errorAccurred()) {
          info.infoBox("titleBar");
          break;
        }
        // Wenn nicht, leitet daten an Eshop
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
            setVisiblePanel("mitarbeiter");
            login.clearText();
            break;
        }
        break;

      // Button um zum registrieren Fenster zu wechseln
      case "login_toRegister":
        setVisiblePanel("register");
        break;

      // #endregion login

      // #region register

      case "register_registerButton":
        try {
          String name = CheckStringNamen(register.getVorame() + " " + register.getName());
          String email = CheckStringEmail(register.mailText.getText());
          String address = register.getLand() + CheckStringAdresseOrt(register.ortText.getText())
              + CheckStringAdresseStraße(register.getStreet());
          String un = register.getUsername();
          String passwort = CheckStringPasswort(register.getPasswort());
          eshop.BV_kundeHinzufügen(name, un, passwort, email, address);
          info.infoBox("Konto wurde erstellt", "Bestätigung");
          setVisiblePanel("login");
          register.clearText();
        } catch (Exception e) {
          info.infoBox(e.getMessage(), "Registrieren Fehler");
        }
        break;

      case "register_backToLogin":
        setVisiblePanel("login");
        break;

      // #endregion register

      // #region kunde

      case "kunde_logout":
        setVisiblePanel("login");
        eshop.logout(this);
        resetTable();
        break;

      // Suchbutton in der Shopansicht der Kunden
      case "kunde_suchen":
        filter = kunde.getSelectedFilter();
        searchterm = kunde.getSearchterm();
        // falls noch einleitender Text gesetzt ist, wird der Text null gesetzt
        searchterm = (searchterm.equals("wonach suchst du?")) ? "" : searchterm;
        suchen(searchterm, filter);
        break;

      // in den Warenkorb Button
      case "kunde_hinzufügen":
        string = JOptionPane.showInputDialog("Wie viele Items wollen sie in den Warenkorb tun?");
        try {
          integ = Integer.parseInt(CheckStringNum(string));
        } catch (ExceptionInputFalsch e) {
          info.infoBox(e.getMessage(), "Fehler");
        }
        // sucht momentan ausgewählten Artikel heraus
        selectedArticle = (Artikel) artikelList.get(kunde.shopTable.getSelectedRow());
        eshop.WV_setArtikel(selectedArticle, integ);
        updateWarenkorb();
        break;

      case "kunde_entfernen":
        selectedArticle = (Artikel) artikelList.get(kunde.getSelectedRow());
        if (info.confirmBox(selectedArticle.getName() + " aus Warenkorb entfernen?") == 0) {
          eshop.WV_removeArtikel(selectedArticle);
          kunde.updateWK(eshop.WK_getInhalt(), eshop.WV_getSumme());
        }
        break;

      case "kunde_clearAll":
        if (info.confirmBox("Warenkorb leeren?") == 0) {
          eshop.WV_clearAll();
          kunde.updateWK(null, 0);
        }
        break;

      case "kunde_kaufen":
        try {
          if (info.confirmBox("Wollen sie die Artikel kaufen?") == 0) {
            string = eshop.WV_kaufen(this.userHash).toString();
            kunde.updateWK(null, 0);
          } else {
            string = "kaufen abgebrochen";
          }
          info.infoBox(string, "kaufen");
        } catch (ExceptionArtikelCollection e) {
          info.infoBox(e.getMessage(), "Fehler");
        }
        break;

      // #endregion kunde

      // #region mitarbeiter

      case "mitarbeiter_Artikelsuchen":
        filter = "Kein Filter";
        searchterm = employ.searchterm.getText();
        suchen(searchterm, filter);
        break;

      case "mitarbeiter_Artikelbearbeiten":
        // offenes Fenster schließen
        editDispose();
        edit = new EditFrame(this, "Artikel bearbeiten");
        edit.initializeLayoutArtikelBearbeiten((Artikel) artikelList.get(employ.getSelectedRow()));
        break;

      case "mitarbeiter_Artikelerstellen":
        // offenes Fenster schließen
        editDispose();
        edit = new EditFrame(this, "Artikel erstellen");
        edit.initializeLayoutArtikelErstellen();
        break;

      case "mitarbeiter_mitarbeiterHinzufügen":
        // offenes Fenster schließen
        editDispose();
        edit = new EditFrame(this, "Mitarbeiter hinzufügen");
        edit.initializeLayoutMitarbeiter();
        break;

      case "mitarbeiter_logout":
        setVisiblePanel("login");
        resetTable();
        break;

      case "mitarbeiter_EreignisSuchen":
        Vector<Ereignis> ereignisse = new Vector<>();
        searchterm = employ.getSearchtermEvent();
        if (searchterm.equals("")) {
          resetEventLog();
        } else {
          try {
            ereignisse.add(eshop.EV_getEreignis(Integer.parseInt(CheckStringNum(searchterm))));
            updateEvent(ereignisse);
          } catch (NumberFormatException | ExceptionEreignisNichtGefunden | ExceptionInputFalsch e) {
            info.infoBox(e.getMessage(), "Fehler");
          }
        }
        break;

      case "Edit_ArtikelErstellen":
        String name;
        Integer bestand;
        Double einzelpreis;
        Integer packung;
        try {
          name = CheckStringArtikel(edit.getArtikelName());
          bestand = Integer.parseInt(CheckStringNum(edit.getBestand()));
          einzelpreis = Double.parseDouble(CheckStringFloat(edit.getPreis()));
          packung = Integer.parseInt(CheckStringNum(edit.getStückzahl()));
          eshop.AV_addArtikel(this.userHash, name, bestand, einzelpreis, packung);
          edit.frame.dispose();
        } catch (ExceptionArtikelExistiertBereits | ExceptionArtikelKonnteNichtErstelltWerden
            | ExceptionInputFalsch e) {
          info.infoBox(e.getMessage(), "Fehler");
        }
        updateArtikel();
        break;
      case "Edit_ArtikelBearbeiten":
        selectedArticle = (Artikel) artikelList.get(employ.shopTable.getSelectedRow());
        try {
          name = CheckStringArtikel(edit.artikelNameText.getText());
          bestand = Integer.parseInt(CheckStringNum(edit.bestandText.getText()));
          Double preis = Double.parseDouble(edit.preisText.getText());
          eshop.AV_setArtikel(userHash, selectedArticle, name, bestand, preis);
          edit.frame.dispose();
        } catch (NumberFormatException | ExceptionArtikelNichtGefunden | ExceptionArtikelUngültigerBestand
            | ExceptionInputFalsch e) {
          info.infoBox(e.getMessage(), "Fehler");
        }
        updateArtikel();
        break;
      case "Edit_ArtikelLöschen":
        selectedArticle = (Artikel) artikelList.get(employ.shopTable.getSelectedRow());
        if (info.confirmBox("soll der Artikel gelöscht werden?") == 0) {
          try {
            eshop.AV_deleteArtikel(this.userHash, selectedArticle.getName());
            updateArtikel();
            edit.frame.dispose();
          } catch (ExceptionArtikelKonnteNichtGelöschtWerden e) {
            info.infoBox(e.getMessage(), "Fehler");
          }
        }
        break;
      case "Edit_MitarbeiterHinzufügen":
        try {
          eshop.BV_mitarbeiterHinzufügen(CheckStringNamen(edit.nutzerNameText.getText()),
              CheckStringUsername(edit.usernameText.getText()), CheckStringPasswort(edit.passwordText.getText()));
          updateNutzer();
        } catch (ExceptionBenutzerNameUngültig | ExceptionInputFalsch e) {
          info.infoBox(e.getMessage(), "Fehler");
          e.printStackTrace();
        }
        break;
      case "Edit_Bestandshistorie":
        newHistoryFrame(edit.getArtikel());
        break;
    }
  }

  /**
   * Sortiert die Artikelliste bzw erschafft neue Ordnung basierend auf
   * suchbegriff und Filter
   * 
   * @param searchTerm
   * @param filter
   */

  public void suchen(String searchTerm, String filter) {
    this.ordnung = null;

    // switch auf ausgewählten Filter
    switch (filter) {
      case "Kein Filter":
        // kein Filter gewählt, ohne suchbegriff
        if (!(searchTerm.equals(""))) {
          this.ordnung = eshop.AV_sucheArtikel(searchTerm);
        } else {
          // kein Filter gewählt, mit suchbegriff
          artikelList = eshop.AV_getAlleArtikelList();
        }
        break;
      case "Preis aufsteigend":
        // Filter Preis aufsteigend, ohne Suchbegriff
        if (!(searchTerm.equals(""))) {
          this.ordnung = eshop.AV_sucheArtikel(searchTerm);
          eshop.AV_sortListPreis(this.ordnung, false);
        } else {
          // Filter Preis aufsteigend, mit Suchbegriff
          eshop.AV_sortListPreis(artikelList, false);
        }
        break;
      case "Preis absteigend":
        // Filter Preis absteigend, ohne Suchbegriff
        if (!(searchTerm.equals(""))) {
          this.ordnung = eshop.AV_sucheArtikel(searchTerm);
          eshop.AV_sortListPreis(this.ordnung, true);
        } else {
          // Filter Preis absteigend, mit Suchbegriff
          eshop.AV_sortListPreis(artikelList, true);
        }
        break;
    }
    if (ordnung == null) {
      updateArtikel(artikelList);
      // falls Ordnung, gibt Ordnung weiter --> auf Suchbegriff angepasster Vektor
    } else {
      updateArtikel(this.ordnung.getObjektList());
    }
  }

  /**
   * updatet Warenkorb wenn Inhalte entfernt/ hinzugefügt
   */
  public void updateWarenkorb() {
    kunde.updateWK(eshop.WK_getInhalt(), eshop.WV_getSumme());
  }

  /**
   * updatet Shopansicht wenn Artikel entfernt/hinzugefügt oder Filter
   */
  public void updateArtikel() {
    artikelList = eshop.AV_getAlleArtikelList();
    artikelTableModel.setArtikel(artikelList);
    artikelVwTableModel.setArtikel(artikelList);
  }

  // update Shopansicht mit Vorsortierter Artikel Liste --> wenn SUchbegriff
  // eingegeben
  public void updateArtikel(Vector aktuelleArtikel) {
    artikelList = aktuelleArtikel;
    artikelTableModel.setArtikel(aktuelleArtikel);
    artikelVwTableModel.setArtikel(aktuelleArtikel);
  }

  /**
   * updated Mitarbeiter Tabelle falls Änderungen
   */
  public void updateNutzer() {
    nutzerList = eshop.BV_getAllNutzer();
    mitarbeiterTableModel.setMitarbeiter(nutzerList);
  }

  public void updateEvent(Vector<Ereignis> aktuelleEreignisse) {
    eventTableModel.setArtikel(aktuelleEreignisse);
  }

  public void resetEventLog() {
    eventList = eshop.EV_getLog();
    eventTableModel.setArtikel(eventList);
  }

  /**
   * resetet Suchbegriff, Filter, und Shopansicht --> einfache ausgabe aller
   * artikel
   */
  public void resetTable() {
    searchterm = "";
    filter = "Kein Filter";
    updateArtikel();
  }

  /**
   * schließt offenes edit Fenster falls vorhanden
   */
  public void editDispose() {
    if (!(edit == null)) {
      edit.dispose();
    }
  }

  /**
   * öffnet neues Fenster mit Bestandshistorie zu jeweiligem Artikel
   * 
   * @param artikel
   */
  public void newHistoryFrame(Artikel artikel) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.add(new LineChartEx(eshop.EV_getBestandsHistore(artikel)));
    frame.setSize(400, 400);
    frame.setLocation(200, 200);
    frame.setVisible(true);
  }

  /**
   * initialisiere JFrame und add einzelne panels
   * 
   * @param String panel, entscheidet welches panel am anfang sichtbar
   */
  public void buildMainWindow(String panel) {
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());
    frame.add(register);
    frame.add(login);
    frame.add(kunde);
    frame.add(employ);
    setVisiblePanel(panel);
    frame.pack();
    frame.setVisible(true);
    frame.setResizable(false);
  }

  /**
   * bestimm sichtbares Panel
   * 
   * @param sichtbar String, welches Panel ist sichtbar? "login", "kunde",
   *                 "mitarbeiter", "register"
   */
  public void setVisiblePanel(String sichtbar) {
    // Alle panels unsichtbar setzen
    login.setVisible(false);
    register.setVisible(false);
    kunde.setVisible(false);
    employ.setVisible(false);

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
      case "mitarbeiter":
        employ.setVisible(true);
        frame.pack();
        break;
    }
  }

  /**
   * überprüft ob Text Feld leer ist.
   * 
   * @param stringToCheck
   * @param fieldName
   * @param field
   * @return
   */
  public String checkIfEmpty(String stringToCheck, String fieldName, JTextField field) {
    if (stringToCheck.equals("")) {
      info.addString("Feld '" + fieldName + "' ist leer.");
      field.setBackground(Color.red);
    }
    return stringToCheck;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    return;
  }

  // #region input checking
  // premade string pattern checks
  // TODO exception message nicht throwen sondern infobox hinzufügen --> am Ende
  // gesammelte Errors ausgeben

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
    CheckString(PatternEchtNamen, string, null);
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

  protected String CheckStringUsername(String string) throws ExceptionInputFalsch {
    CheckString(PatternUserName, string, null);
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
