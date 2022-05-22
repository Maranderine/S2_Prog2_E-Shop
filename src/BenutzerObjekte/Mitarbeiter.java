public class Mitarbeiter extends Benutzer{
    private int mitarbeiterNr;

    public Mitarbeiter(String name, String username, String password, int nr) {
        super(name, username, password);
        mitarbeiterNr = nr;
    }

    // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
    // z.B. getStrasse() und setStrasse()

    public int getKundenNr() {
        return mitarbeiterNr;
    }
}
