package Domain.BenutzerObjekte;


class Mitarbeiter extends Benutzer {
  private static int mitarbeiterNrzähler = 1;

  private int mitarbeiterNr;

  protected Mitarbeiter(String name, String username, byte[] password) {
    super(Benutzerverwaltung.BeutzerType.MITARBEITER, name, username, password);
    this.mitarbeiterNr = mitarbeiterNrzähler;
    mitarbeiterNrzähler++;
  }

  // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
  // z.B. getStrasse() und setStrasse()
  @Override
  protected int getKundenNr() {
    return mitarbeiterNr;
  }
  

  @Override
  public String toString() {
    // parent name + username
    return "Mitarbeiter:\t" + super.toString() + "\t" + this.mitarbeiterNr;
  }
}
