package BenutzerObjekte;

public class Mitarbeiter extends Benutzer {
  public static int zähler = 1;
  public int mitarbeiterNr;

  public Mitarbeiter(String name, String username, String password) {
    super(name, username, password);
    this.mitarbeiterNr = zähler;
    zähler++;
  }

  // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
  // z.B. getStrasse() und setStrasse()

  public int getKundenNr() {
    return mitarbeiterNr;
  }

  @Override
  public String toString() {
    // parent name + username
    return "Mitarbeiter:\t" + super.toString() + "\t" + this.mitarbeiterNr;
  }
}
