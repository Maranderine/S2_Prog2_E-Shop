package BenutzerObjekte;

public class Mitarbeiter {
    private String name;
    private String email;
    private String strasse = "";
    private int mitarbeiterNr;
    private String plz = "";
    private String wohnort = "";

    public Mitarbeiter(int nr, String name) {
        mitarbeiterNr = nr;
        this.name = name;
    }

    // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
    // z.B. getStrasse() und setStrasse()

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getWohnort() {
        return wohnort;
    }

    public void setWohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    public int getKundenNr() {
        return mitarbeiterNr;
    }
}
