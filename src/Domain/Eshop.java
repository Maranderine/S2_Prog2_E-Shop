package Domain;

import java.io.IOException;
import java.util.HashMap;

import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Domain.Artikel.Lager;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.BenutzerObjekte.Kunde;
import Domain.EreignisLog.EreignisLogVerwaltung;
import Domain.Warenkorb.Rechnung;
import Domain.Warenkorb.WarenkorbVerwaltung;
import UserInterface.CUI;
import UserInterface.UserInterface;
public class Eshop {

  private String artikelDoc = "";
  private String mitarbeiterDoc = "";
  private String kundenDoc = "";

  private Benutzerverwaltung BenutzerVw;
  private ArtikelVerwaltung ArtikelVw;
  private WarenkorbVerwaltung WarenkorbVw;
  private EreignisLogVerwaltung EreignisVw;

  public Eshop(String artikelDox, String kundenDox, String mitarbeiterDox){

   this.artikelDoc = artikelDox;
   this.mitarbeiterDoc = mitarbeiterDox;
   this.kundenDoc = kundenDox;

    try{
    BenutzerVw = new Benutzerverwaltung();
    if(!(kundenDoc.equals(""))){BenutzerVw.load(kundenDoc);}
    ArtikelVw = new ArtikelVerwaltung();
    if(!(artikelDoc.equals(""))){ArtikelVw.load(artikelDoc);}
    WarenkorbVw = new WarenkorbVerwaltung();
    EreignisVw = new EreignisLogVerwaltung();
    }catch(IOException e){
      e.printStackTrace();
    }


  }

  // #region NutzerVerwaltung
  public void BV_kundeHinzufügen(String name, String username, String password, String email, String address) {
    BenutzerVw.registrieren(name, username, password, email, address);
    // EreignisVw.ereignisAdd(user, type);
  }

  public void BV_mitarbeiterHinzufügen(String name, String username, String password) {
    BenutzerVw.registrieren(name, username, password);
  }

  public void BV_NutzerEntfernen(String username) {

  }

  public Benutzerverwaltung.AktiverBeutzerType login(String username, String password) {
    return BenutzerVw.login(username, password);
  }

  public void logout() {
    BenutzerVw.logout();
  }

  // aktive nutzer managen
  /**
   * returnt das Benutzer Objekt assoziert mit dem userHash
   * 
   * @param userHash
   * @return
   */
  public Benutzer BV_getAktiverBenutzer(byte[] userHash) {
    return BenutzerVw.getAktiverBenutzer(userHash);
  }

  // #endregion
  // #region Warenkorb
  /**
   * gibt Warenkorb Inhalt zurück
   * 
   * @return HashMap<Artikel, Integer>
   */
  public HashMap<Artikel, Integer> WK_getInhalt() {
    return WarenkorbVw.getInhalt();
  }

  /**
   * gibt warenkorb
   * 
   * @return
   */
  public Object WV_getWarenkorb() {
    return WarenkorbVw.getWarenkorb();
  }

  /**
   * erstellt einen neuen Eintrag oder ändert einen vorhandenen
   * 
   * @param artikel artikel object
   * @param integer artikel Stückzahl
   */
  public void WV_setArtikel(Artikel artikel, int integer) {
    WarenkorbVw.setArtikel(artikel, integer);
  }

  /**
   * entfernt einen artikel aus der map
   * 
   * @param artikel artikel zu entfernen
   */
  public void WV_removeArtikel(Artikel artikel) {
    WarenkorbVw.removeArtikel(artikel);
  }

  /**
   * löscht den gesamten inhalt des Warenkorbes
   */
  public void WV_clearAll() {
    WarenkorbVw.clearAll();
  }

  public Rechnung WV_kaufen() {
    Rechnung rechnung = new Rechnung(WarenkorbVw.ArtikelKaufen());
    WarenkorbVw.clearAll();
    return rechnung;
    
  }

  // #endregion Warenkorb
  // #region Artikelvw
  /**
   * @author Maranderine
   */
  public Lager AV_alleArtikel() {
    return ArtikelVw.alleArtikel();
  }

  /**
   * Add Artikel to artikelListe
   * 
   * @param name
   * @param bestand
   * @param einzelpreis
   */
  public Artikel AV_addArtikel(String name, int bestand, double einzelpreis) {
    // EreignisVw.ereignisAdd();
    return ArtikelVw.addArtikel(name, bestand, einzelpreis);
  }

  public boolean AV_deleteArtikel(String name) {
    return ArtikelVw.deleteArtikel(name);
  }

  public boolean AV_setArtikelBestand(String name, int bestand) {
    return ArtikelVw.setArtikelBestand(name, bestand);
  }

  /**
   * find Artikel by name in artikelListe
   * 
   * @param name of artikel
   * @return Artikel type object or null
   */
  public Artikel AV_findArtikelByName(String name) {
    return ArtikelVw.findArtikelByName(name);
  }

  // #endregion Artikel
  // #region Ereignis Log

  public String EV_logShow() {
    return "";
  }

  /**
   * logt eine ereignis im ereignis log
   * 
   * @param userHash
   * @param type
   * @return
   */
  public boolean EV_logEreignis(byte[] userHash, String type) {
    return EreignisVw.neuesEreignis(userHash, type);
  }
  // TODO eine sichere newlog methode die den benutzer braucht, so dass nicht
  // jedes mal die map benutzt werden muss
  // #endregion

  public void saveData(){
    try{
    ArtikelVw.save(artikelDoc);
    BenutzerVw.save(kundenDoc);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
  /**
   * creates used User Interface object. for example CUI or GUI
   * 
   * @return UserInterface UserInterface Object
   */
  public UserInterface createUserInterface() {
    
    return new CUI(this);
  }

}
