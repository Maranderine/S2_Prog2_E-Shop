package Domain.BenutzerObjekte;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import Domain.Verwaltung;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import Exceptions.Benutzer.ExceptionBenutzerNichtGefunden;
import Exceptions.Benutzer.ExceptionKundeNichtGefunden;
import Exceptions.Benutzer.ExceptionMitarbeiterNichtGefunden;
import UserInterface.UserSession;
import persistence.FilePersistenceManager;
import persistence.PersistenceManager;
import common.EshopInterface.BenutzerType;

public class Benutzerverwaltung extends Verwaltung {

  // Verwaltung der Nutzer in einer verketteten Liste

  private Vector<Benutzer> benutzerRegister;
  private PersistenceManager persistenceManager = new FilePersistenceManager();
  private int kundenNrZähler;
  private int mitarbeiterNrzähler;
  private String benutzerDoc;

  public Benutzerverwaltung() {
    this.benutzerDoc = "Nutzer.txt";

    try {
      // loading register
      benutzerRegister = load(this.benutzerDoc);

    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      benutzerRegister = new Vector<Benutzer>();
      System.out.println("Could not load BenutzerVerwaltung");

      // Backup test user
      try {
        registrieren(
            "Admin Adminton",
            "a",
            "a");

        registrieren(
            "Test Benutzer",
            "t",
            "t",
            "t@t.t",
            "Testrasse 22, 22222 Testhausen, Testland");

      } catch (ExceptionBenutzerNameUngültig e1) {
        e1.printStackTrace();
      }
    }
  }

  public void registrieren(String name, String username, String password, String email, String adress)
      throws ExceptionBenutzerNameUngültig {
    checkName(username);
    Benutzer einNutzer = new Kunde(useKundenNrZähler(), name, username, encryptString(password), email, adress);
    // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    // übernimmt Vector:
    this.benutzerRegister.add(einNutzer);
  }

  public void registrieren(String name, String username, String password) throws ExceptionBenutzerNameUngültig {
    checkName(username);
    Benutzer einNutzer = new Mitarbeiter(useMitarbeiterNrzähler(), name, username, encryptString(password));

    // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    // übernimmt Vector:
    this.benutzerRegister.add(einNutzer);
  }

  /**
   * delete be utzer aus register
   * 
   * @param benutzer
   * @throws ExceptionBenutzerNichtGefunden
   */
  public void deleteBenutzer(Benutzer benutzer) throws ExceptionBenutzerNichtGefunden {
    benutzerRegister.remove(benutzer);
  }

  /**
   * delete be utzer aus register
   * 
   * @param username
   * @throws ExceptionBenutzerNichtGefunden
   */
  public void deleteBenutzer(String username) throws ExceptionBenutzerNichtGefunden {
    deleteBenutzer(this.sucheBenutzer(username));
  }

  /**
   * suche nutzer bei namen
   * 
   * @param username
   * @return Benutzer objekt
   * @throws ExceptionBenutzerNichtGefunden
   */
  public Benutzer sucheBenutzer(String username) throws ExceptionBenutzerNichtGefunden {
    for (Benutzer benutzer : benutzerRegister) {
      if (benutzer.getUsername().equals(username)) {
        return benutzer;
      }
    }
    throw new ExceptionBenutzerNichtGefunden();
  }

  /**
   * find by name
   * 
   * @param userNumber
   * @return
   * @throws ExceptionKundeNichtGefunden
   */
  public Kunde sucheKunde(int userNumber) throws ExceptionKundeNichtGefunden {
    for (Benutzer benutzer : benutzerRegister) {
      if (benutzer.getType() == BenutzerType.KUNDE) {
        if (benutzer.getKundenNr() == userNumber) {
          return (Kunde) benutzer;
        }
      }

    }
    throw new ExceptionKundeNichtGefunden();
  }

  /**
   * 
   * ind by name
   * 
   * @param userNumber
   * @return
   * @throws ExceptionMitarbeiterNichtGefunden
   */
  public Mitarbeiter sucheMitarbeiter(int userNumber) throws ExceptionMitarbeiterNichtGefunden {
    for (Benutzer benutzer : benutzerRegister) {
      if (benutzer.getType() == BenutzerType.KUNDE) {
        if (benutzer.getKundenNr() == userNumber) {
          return (Mitarbeiter) benutzer;
        }
      }
    }
    throw new ExceptionMitarbeiterNichtGefunden();
  }

  /**
   * chgeckt ob name gültig ist
   * 
   * @return true wenn alles okay
   */
  public boolean checkName(String string) throws ExceptionBenutzerNameUngültig {
    // TODO do regex pattern checking
    // pattern.matcher(input).matches()
    if (string.equals(null))
      throw new ExceptionBenutzerNameUngültig();

    try {
      sucheBenutzer(string);
      throw new ExceptionBenutzerNameUngültig();
    } catch (ExceptionBenutzerNichtGefunden e) {
      System.out.println("weird");
      return true;
    }

  }

  // #region get user data
  /*
   * get data methoden
   * returnen daten unabhängig vom typ oder benutzung der daten
   * 
   */
  /**
   * get user identifikation number
   * regardless of user type
   * 
   * @param user
   * @return int identifikation number
   */
  public int getDataNummer(Benutzer user) {
    return user.getKundenNr();
  }

  /**
   * get user type
   * 
   * @param user
   * @return BenutzerType user type
   */
  public BenutzerType getDataTyp(Benutzer user) {
    return user.getType();
  }

  /**
   * get user name
   * 
   * @param user
   * @return String user name
   */
  public String getDataName(Benutzer user) {
    return user.getName();
  }

  public Vector<Benutzer> getBenutzerList() {
    return benutzerRegister;
  }

  // #endregion

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // #region zähler
  private int useKundenNrZähler() {
    return kundenNrZähler++;
  }

  private void setKundenNrZähler(int kundenNrZähler) {
    this.kundenNrZähler = kundenNrZähler;
  }

  private int useMitarbeiterNrzähler() {
    return mitarbeiterNrzähler++;
  }

  private void setMitarbeiterNrzähler(int mitarbeiterNrzähler) {
    this.mitarbeiterNrzähler = mitarbeiterNrzähler;
  }

  // #endregion
  // #region security
  /**
   * encrypted einen sting to SHA-1
   * SHA-1 is a hash type
   * A hash is a generated value that is unique to the input
   * 
   * @author github & Malte
   * @return encrypted bytes in an array: byte[]
   */
  private static byte[] encryptString(String string) {
    byte[] sha1 = null;
    try {
      // findet ein MessageDigest obj
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      // Cleared vorhandene daten im obj
      crypt.reset();
      // convertiert tring zu bytes und fügt die daten dem obj hinzu
      crypt.update(string.getBytes("UTF-8"));
      // encoded die daten und gibt sie in variable
      sha1 = crypt.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return sha1;
  }

  /**
   * sets user identifier in UserInterface used
   * 
   * @param byteArr Hash Value generated by generateUserHash, is a byte array
   */
  private void setUserHash(UserSession UIobj, byte[] byteArr) {
    UIobj.userHash = byteArr;
  }

  /**
   * sets user identifier in UserInterface used
   * 
   * @param byteArr Hash Value generated by generateUserHash, is a byte array
   */
  private void resetUserHash(UserSession UIobj) {
    // create empy array
    byte arr[] = {};
    // set is as the user hash
    setUserHash(UIobj, arr);
  }

  /**
   * gets the user identifier from the UserInterface used
   * 
   * @param byteArr
   */
  private byte[] getUserHash(UserSession UIobj) {
    return UIobj.userHash;
  }

  /**
   * Generates a unique hash for the user online instance
   * 
   * @param user user to generate a hash for/from
   * @return encrypted bytes in an array: byte[]
   */
  private byte[] generateUserHash(Benutzer user) {
    // hash
    return encryptString(user.toString() + System.currentTimeMillis());
  }

  // #endregion//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // #region user management

  private Map<byte[], Benutzer> ActiverNutzerListe = new HashMap<byte[], Benutzer>();

  /**
   * logt den user ein: Generiert User Identifier, fügt ihn zur aktiven liste
   * hinzu
   * 
   * @param callingUI calling user Interface
   * @param username
   * @param passw
   * @return
   * @throws ExceptionBenutzerNichtGefunden
   */
  public BenutzerType login(UserSession callingUI, String username, String passw) {
    Benutzer benutzer;
    try {
      benutzer = sucheBenutzer(username);

      if ((benutzer != null) && (Arrays.equals(benutzer.getPassword(), encryptString(passw)))) {
        //////////// if user is found ///////////

        // generate user identifier
        byte[] userHash = generateUserHash(benutzer);
        // give it to the UserInterface used
        setUserHash(callingUI, userHash);
        // add user to list of active users
        addAtiverBenutzer(userHash, benutzer);

        // return type of user
        if (benutzer instanceof Mitarbeiter) {
          return BenutzerType.MITARBEITER;
        }
        if (benutzer instanceof Kunde) {
          return BenutzerType.KUNDE;
        }
      }

    } catch (ExceptionBenutzerNichtGefunden e) {

    }
    return BenutzerType.NONE;
  }

  /**
   * log the user out:
   * overwrite userhash
   * removes from active user list
   * 
   * @param callingUI calling user Interface
   */
  public void logout(UserSession callingUI) {
    // get active user hash and remove him from list of active users
    byte[] userHash = getUserHash(callingUI);
    removeAtiverBenutzer(userHash);

    // resets userHash to default state
    resetUserHash(callingUI);
  }

  // #endregion
  // #region active user management
  /**
   * add user to active user list
   * 
   * @param userHash
   * @param user
   */
  private void addAtiverBenutzer(byte[] userHash, Benutzer user) {
    ActiverNutzerListe.put(userHash, user);
  }

  /**
   * remove user from active user list
   * 
   * @param userHash
   */
  private void removeAtiverBenutzer(byte[] userHash) {
    ActiverNutzerListe.remove(userHash);
  }

  /**
   * checkt ob benutzer online ist
   * 
   * @param userHash
   * @return boolen ob benutzer online ist
   */
  public boolean istBenutzerAktiv(byte[] userHash) {
    return ActiverNutzerListe.containsKey(userHash);
  }

  /**
   * returnt das Benutzer Objekt assoziert mit dem userHash
   * 
   * @param userHash
   * @return associated user obj or null
   */
  public Benutzer getAktiverBenutzer(byte[] userHash) {
    return ActiverNutzerListe.get(userHash);
  }

  // #endregion
  // #region persistenz

  private void save(String benutzerDoc) throws IOException {
    persistenceManager.saveObjekt(benutzerDoc, benutzerRegister);
  }

  public void save() throws IOException {
    save(benutzerDoc);
  }

  private Vector<Benutzer> load(String benutzerDoc) throws IOException, ClassNotFoundException {

    if (!benutzerDoc.equals("")) {

      @SuppressWarnings("unchecked")
      Vector<Benutzer> vec = (Vector<Benutzer>) persistenceManager.loadObjekt(benutzerDoc);

      int k = 0;
      int m = 0;
      // zählt Kunden im vektor und setzt statisches Attribut kundenNrzähler
      for (Benutzer b : vec) {
        if (b instanceof Kunde) {
          k++;
        }
        if (b instanceof Mitarbeiter) {
          m++;
        }
      }
      setKundenNrZähler(k);
      setMitarbeiterNrzähler(m);
      return vec;
    } else
      return null;
  }

  // #endregion
}
