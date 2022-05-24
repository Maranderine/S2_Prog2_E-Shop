package BenutzerObjekte;

public class Kunde extends Benutzer{

    private String email;
    private String address = "";
    private int kundenNr;
    //private String plz = "";
    //private String wohnort = "";

    /**
     * 
     * @param name  name des kunden
     * @param username benutzername für login
     * @param password passwort zum einloggen
     * @param kundenNr 
     * @param email
     * @param address
     */
    public Kunde(String name, String username, String password, int kundenNr, String email, String address) {
        super(name, username, password);
        this.kundenNr = kundenNr;
        this.email = email;
        this.adress = address;
        // this.warenkorb = new Warenkorb; //erstmal rausgenommen Warenkorb wird in der Warenkorbverwaltung gemanaged
    }

    //#region Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
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
    //#endregion
    // weitere Dienste der Kunden-Objekte
}
