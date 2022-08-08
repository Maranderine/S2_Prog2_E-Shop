package Domain.BenutzerObjekte;

public class Mitarbeiter extends Benutzer {

  private int mitarbeiterNr;

  /**
   * 
   * @param MitarbeiterNummer
   * @param name
   * @param username
   * @param password
   */
  protected Mitarbeiter(int MitarbeiterNummer, String name, String username, byte[] password) {
    super(Benutzerverwaltung.BeutzerType.MITARBEITER, name, username, password);
    this.mitarbeiterNr = MitarbeiterNummer;
  }

  // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
  // z.B. getStrasse() und setStrasse()
  @Override
  public int getKundenNr() {
    return mitarbeiterNr;
  }

  @Override
  public String toString() {
    // parent name + username
    return "Mitarbeiter:\t" + super.toString() + "\t" + this.mitarbeiterNr;
  }
}
