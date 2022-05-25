package BenutzerObjekte;

public class Kunde extends Benutzer {
  private String email;
  private String address = "";
  private int kundenNr;
  private static int kundenNrZähler = 1;
  // private String plz = "";
  // private String wohnort = "";

  /**
   * 
   * @param name     name des kunden
   * @param username benutzername für login
   * @param password passwort zum einloggen
   * @param kundenNr
   * @param email
   * @param address
   */
  public Kunde(String name, String username, byte[] password, String email, String address) {
    super(name, username, password);
    this.kundenNr = kundenNrZähler;
    kundenNrZähler++;
    this.email = email;
    this.address = address;
    // this.warenkorb = new Warenkorb; //erstmal rausgenommen Warenkorb wird in der
    // Warenkorbverwaltung gemanaged
  }

  // #region Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
  // z.B. getStrasse() und setStrasse()

  public String getAdress() {
    return address;
  }

  public void setAdress(String adress) {
    this.address = adress;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

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
