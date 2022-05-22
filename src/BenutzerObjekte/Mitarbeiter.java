package BenutzerObjekte;


public class Mitarbeiter extends Benutzer{
    private int mitarbeiterNr;

    public Mitarbeiter(String name, String username, String password, int mitarbeiterNr) {
        super(name, username, password);
        this.mitarbeiterNr = mitarbeiterNr;
    }

    // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
    // z.B. getStrasse() und setStrasse()

    public int getKundenNr() {
        return mitarbeiterNr;
    }
}
