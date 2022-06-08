package Domain.BenutzerObjekte;

public class Mitarbeiter extends Benutzer{
  protected static int zähler = 1;
  private int mitarbeiterNr;

  protected Mitarbeiter(String name, String username, byte[] password) {
    super(name, username, password);
    this.mitarbeiterNr = zähler;
    zähler++;
  }

  // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
  // z.B. getStrasse() und setStrasse()

  protected int getKundenNr() {
    return mitarbeiterNr;
  }

  @Override
  public String toString() {
    // parent name + username
    return "Mitarbeiter:\t" + super.toString() + "\t" + this.mitarbeiterNr;
  }
}
