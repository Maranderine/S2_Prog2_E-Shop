package common;

import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtErstelltWerden;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtGelöschtWerden;
import Exceptions.Artikel.ExceptionArtikelNameExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameUngültig;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import Exceptions.Ereignis.ExceptionEreignisNichtGefunden;
import UserInterface.UserInterface;

import java.util.HashMap;
import java.util.Vector;
import Domain.Artikel.Artikel;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.Search.SuchOrdnung;
import Domain.Warenkorb.Rechnung;

public interface EshopInterface {

  ///////////////////////////////////// to do////////////////////////////////////

  // #region Blanka

  // #endregion Blanka
  // #region Jonah

  // #endregion Jonah
  // #region Malte

  /**
   * Add Artikel to artikelListe
   * 
   * @param name
   * @param bestand
   * @param einzelpreis
   * @throws ExceptionArtikelExistiertBereits
   * @throws ExceptionArtikelKonnteNichtErstelltWerden
   */
  public Artikel AV_addArtikel(byte[] userHash, String name, int bestand, double einzelpreis, int packungsInhalt)
      throws ExceptionArtikelExistiertBereits, ExceptionArtikelKonnteNichtErstelltWerden;

  /**
   * del artikel
   * 
   * @param userHash
   * @param name
   * @throws ExceptionArtikelKonnteNichtGelöschtWerden
   */
  public void AV_deleteArtikel(byte[] userHash, String name) throws ExceptionArtikelKonnteNichtGelöschtWerden;

  /**
   * set artikel data
   * 
   * @param userHash  userHash
   * @param artikel   artikel obj
   * @param neuerName artikel neuer name
   * @throws ExceptionArtikelNameExistiertBereits
   * @throws ExceptionArtikelNameUngültig
   */
  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName)
      throws ExceptionArtikelNameExistiertBereits, ExceptionArtikelNameUngültig;

  /**
   * set artikel data bestand
   * 
   * @param userHash userHash
   * @param artikel  artikel obj
   * @param bestand  artikel neuer bestand
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void AV_setArtikel(byte[] userHash, Artikel artikel, int bestand) throws ExceptionArtikelUngültigerBestand;

  /**
   * set artikel data bestand
   * 
   * @param userHash userHash
   * @param name     artikel name
   * @param bestand  artikel neuer bestand
   * @throws ExceptionArtikelNichtGefunden
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void AV_setArtikel(byte[] userHash, String name, int bestand)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand;

  /**
   * set artikel data preis
   * 
   * @param userHash userHash
   * @param artikel  artikel obj
   * @param preis    artikel neuer preis
   * @throws ExceptionArtikelNichtGefunden
   */
  public void AV_setArtikel(byte[] userHash, Artikel artikel, double preis);

  /**
   * set artikel data preis
   * 
   * @param userHash userHash
   * @param name     artikel name
   * @param preis    artikel neuer preis
   * @throws ExceptionArtikelNichtGefunden
   */
  public void AV_setArtikel(byte[] userHash, String name, double preis)
      throws ExceptionArtikelNichtGefunden;

  /**
   * set artikel data
   * 
   * @param userHash  userHash
   * @param artikel   artikel obj
   * @param neuerName artikel neuer name
   * @param bestand   artikel neuer bestand
   * @param preis     artikel neuer preis
   * @throws ExceptionArtikelUngültigerBestand
   * @throws ExceptionArtikelNichtGefunden
   */
  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand;

  /**
   * set artikel data
   * 
   * @param userHash  userHash
   * @param name      artikel name
   * @param neuerName artikel neuer name
   * @param bestand   artikel neuer bestand
   * @param preis     artikel neuer preis
   * @return boolean obs geklappt hat
   * @throws ExceptionArtikelNichtGefunden
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void AV_setArtikel(byte[] userHash, String name, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand;

  // ordnungen
  public SuchOrdnung EV_sucheEreignisse(String searchterm);

  /**
   * sortier die liste nach Relevanz
   * 
   * @param ordnung
   * @param reverse
   */
  public void AV_sortListRelevanz(SuchOrdnung ordnung);

  /**
   * sortier die liste nach Preis
   * 
   * @param ordnung
   * @param reverse
   */
  public void AV_sortListPreis(SuchOrdnung ordnung, boolean reverse);

  /**
   * sortier die liste nach name
   * 
   * @param ordnung
   * @param reverse
   */
  public void AV_sortListName(SuchOrdnung ordnung, boolean reverse);

  /**
   * 
   * @param searchTerm
   * @return SuchOrdnung
   */
  public SuchOrdnung AV_sucheArtikel(String searchTerm);

  // #endregion Malte

  /**
   * create new user
   * 
   * @param name
   * @param username
   * @param password
   * @param email
   * @param address
   * @throws ExceptionBenutzerNameUngültig
   */
  public void BV_kundeHinzufügen(String name, String username, String password, String email, String address)
      throws ExceptionBenutzerNameUngültig;

  public void BV_mitarbeiterHinzufügen(String name, String username, String password)
      throws ExceptionBenutzerNameUngültig;

  public Vector<Benutzer> BV_getAllNutzer();

  /**
   * login to user profile
   * 
   * @param callingUI calling user Interface, use "this"
   * @param username
   * @param password
   * @return
   * @throws ExceptionBenutzerNichtGefunden
   */
  public Benutzerverwaltung.BeutzerType login(UserInterface callingUI, String username, String password);

  /**
   * logout the user
   * 
   * @param callingUI calling user Interface, use "this"
   */
  public void logout(UserInterface callingUI);

  /**
   * gibt Warenkorb Inhalt zurück
   * 
   * @return HashMap<Artikel, Integer>
   */
  public HashMap<Artikel, Integer> WK_getInhalt();

  /**
   * gibt warenkorb
   * 
   * @return
   */
  public Object WV_getWarenkorb();

  /**
   * erstellt einen neuen Eintrag oder ändert einen vorhandenen
   * 
   * @param artikel artikel object
   * @param integer artikel Stückzahl
   */
  public void WV_setArtikel(Artikel artikel, int integer);

  /**
   * entfernt einen artikel aus der map
   * 
   * @param artikel artikel zu entfernen
   */
  public void WV_removeArtikel(Artikel artikel);

  /**
   * löscht den gesamten inhalt des Warenkorbes
   */
  public void WV_clearAll();

  /**
   * Kauft alle artikel im Warenkorb.
   * Aktualisiert bestand für alle
   * und erstellt entspechende events
   * 
   * @param userHash Benutzer Identifikator der die funtion ausführt
   * @return rechnung
   * @throws ExceptionArtikelCollection
   */
  public Rechnung WV_kaufen(byte[] userHash) throws ExceptionArtikelCollection;

  public double WV_getSumme();

  /**
   * find Artikel by name in artikelListe
   * 
   * @param name of artikel
   * @return Artikel type object or null
   * @throws ExceptionArtikelNichtGefunden
   */
  public Artikel AV_findArtikelByName(String name) throws ExceptionArtikelNichtGefunden;

  /**
   * get alle artikel in einer liste
   * ist eine kopie
   * 
   * @return
   */
  public Vector<Artikel> AV_getAlleArtikelList();

  /**
   * liste ausgeben
   * 
   * @param list
   * @param detailed
   * @param leereNachicht
   * @return
   */
  public String AV_ArtikelAusgeben(Vector<Artikel> list, boolean detailed, String leereNachicht);

  /**
   * sortier die liste nach name
   * 
   * @param ordnung
   * @param reverse
   */
  public void AV_sortListName(Vector<Artikel> artikelList, boolean reverse);

  /**
   * sortier die liste nach Preis
   * 
   * @param ordnung
   * @param reverse
   */
  public void AV_sortListPreis(Vector<Artikel> artikelList, boolean reverse);

  /**
   * displays Ereignis Log in short
   * 
   * @return ereignis log as a string
   */
  public String EV_logDisplay();

  public Ereignis EV_getEreignis(int ereignisNummer) throws ExceptionEreignisNichtGefunden;

  public Integer[] EV_getBestandsHistore(Artikel artikel);

  public Vector<Ereignis> EV_getLog();

  ///////////////////////////////////// to do////////////////////////////////////

  public void quit();

  /**
   * creates used User Interface object. for example CUI or GUI
   * 
   * @return UserInterface UserInterface Object
   */
  public UserInterface createUserInterface();

  // new
  public static enum REQUESTS {
    QUIT("quit"),
    REPLY("reply"),
    UI("ui");

    private final String key;
    /**
     * character to split a transmitted request string
     */
    public static final String splitter = ";";

    REQUESTS(String str) {
      this.key = str;
    }

    /**
     * gets the string key, used for the switch
     * 
     * @return
     */
    public String get() {
      return key;
    }

    /**
     * check if the request exists
     * 
     * @param str string of the request type
     * @return boolean
     */
    public static REQUESTS get(String str) {

      int i = getIndex(str);

      if (i != -1) {
        return REQUESTS.values()[i];
      } else
        return null;
    }

    /**
     * get the index of an element
     * 
     * @param str
     * @return
     */
    public static int getIndex(String str) {

      REQUESTS[] rList = REQUESTS.values();
      for (int i = 0; i < rList.length; i++) {
        if (rList[i].get().equals(str)) {
          return i;
        }
      }
      return -1;
    }

    @Override
    public String toString() {
      return get();
    }

  }
}