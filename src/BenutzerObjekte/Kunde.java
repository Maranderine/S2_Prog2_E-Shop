package BenutzerObjekte;

public class Kunde extends Benutzer{

    private String email;
    private String adress = "";
    private int kundenNr;
    //private String plz = "";
    //private String wohnort = "";

    public Kunde(String name, String username, String password, int nr, String email, String adress) {
        super(name, username, password);
        kundenNr = nr;
        this.email = email;
        this.adress = adress;
    }

    // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
    // z.B. getStrasse() und setStrasse()

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

}
