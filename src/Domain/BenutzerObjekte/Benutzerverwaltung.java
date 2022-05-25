package Domain.BenutzerObjekte;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Benutzerverwaltung {

  // Verwaltung der Nutzer in einer verketteten Liste
  private List<Benutzer> benutzerRegister;
  // private List<Benutzer, > aktiveNutzer;

  public enum AktiverNutzer {
    MITARBEITER,
    KUNDE,
    NONE;
  }

  public enum NutzerDaten {
    TYPE,
    HASH
  }

  public Benutzerverwaltung() {
    benutzerRegister = new Vector<Benutzer>();
  }

  public void registrieren(String name, String username, String password, String email, String adress) {
    Benutzer einNutzer = new Kunde(name, username, encryptString(password), email, adress);
    // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    // übernimmt Vector:
    this.benutzerRegister.add(einNutzer);
  }

  public void registrieren(String name, String username, String password) {
    Benutzer einNutzer = new Mitarbeiter(name, username, encryptString(password));

    // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    // übernimmt Vector:
    this.benutzerRegister.add(einNutzer);
  }

  public void loeschen(String username) {
    Benutzer b = this.sucheNutzer(username);
    // übernimmt Vector:
    benutzerRegister.remove(b);
  }

  public Benutzer sucheNutzer(String username) {
    for (Benutzer benutzer : benutzerRegister) {
      if (benutzer.getUsername().equals(username)) {
        return benutzer;
      }
    }
    return null;
  }

  public AktiverNutzer login(String username, String passw) {
    Benutzer b = this.sucheNutzer(username);
    // no user found or not matching password
    if (b == null || !(Arrays.equals(b.getPassword(), encryptString(passw)))) {
      return AktiverNutzer.NONE;
    } else {
      // user is found
      if (b instanceof Mitarbeiter) {
        return AktiverNutzer.MITARBEITER;
      }
      if (b instanceof Kunde) {
        return AktiverNutzer.KUNDE;
      }
    }
    return null;
  }

  public void logout() {

  }

  /**
   * encrypted einen sting to SHA-1
   * 
   * @author github & Malte
   * @return encrypted bytes byte[]
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

}
