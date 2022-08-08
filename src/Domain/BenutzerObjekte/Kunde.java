package Domain.BenutzerObjekte;

import common.EshopInterface.BenutzerType;

public class Kunde extends Benutzer {
  private String email;
  private String address = "";
  private int kundenNr;
  // private String plz = "";
  // private String wohnort = "";

  /**
   * 
   * @param KundenNummer
   * @param name         name des kunden
   * @param username     benutzername für login
   * @param password     passwort zum einloggen
   * @param email
   * @param address
   */
  protected Kunde(int KundenNummer, String name, String username, byte[] password, String email, String address) {
    super(BenutzerType.KUNDE, name, username, password);
    this.kundenNr = KundenNummer;
    this.email = email;
    this.address = address;
    // this.warenkorb = new Warenkorb; //erstmal rausgenommen Warenkorb wird in der
    // Warenkorbverwaltung gemanaged
  }

  // #region Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
  // z.B. getStrasse() und setStrasse()

  protected String getAdress() {
    return address;
  }

  protected void setAdress(String adress) {
    this.address = adress;
  }

  protected String getEmail() {
    return email;
  }

  protected void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int getKundenNr() {
    return kundenNr;
  }

  // #endregion
  // weitere Dienste der Kunden-Objekte

  @Override
  public String toString() {
    // parent name + username
    return "Kunde:\t" + super.toString() + "\t" + this.kundenNr;
  }

}
